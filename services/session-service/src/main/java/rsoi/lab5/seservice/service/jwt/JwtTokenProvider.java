package rsoi.lab5.seservice.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import rsoi.lab5.seservice.entity.user.Role;
import rsoi.lab5.seservice.entity.Status;
import rsoi.lab5.seservice.entity.user.User;
import rsoi.lab5.seservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab5.seservice.model.TokenObject;
import rsoi.lab5.seservice.model.user.UserModel;
import rsoi.lab5.seservice.service.oauth.OAuthTokenService;
import rsoi.lab5.seservice.entity.oauth.OAuthClient;
import rsoi.lab5.seservice.entity.oauth.OAuthToken;
import rsoi.lab5.seservice.service.UserService;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.*;
import java.util.*;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private SecretKey secretKey;

    @Value("${jwt.access_token.expired}")
    private long validityAccessTokenInMilliseconds;

    @Value("${jwt.refresh_token.expired}")
    private long validityRefreshTokenInMilliseconds;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private OAuthTokenService oAuthTokenService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public TokenObject createToken(User user, OAuthClient oAuthClient) {
        Claims claimsAccess = Jwts.claims().setSubject(user.getUsername());
        claimsAccess.put("roles", getRoleNames(user.getRoles()));
        claimsAccess.put("client_id", oAuthClient.getClientId());
        claimsAccess.put("user_id", user.getId());
        claimsAccess.put("username", user.getUsername());
        OAuthToken token = oAuthTokenService.findByUserId(user.getId());
        try {
            String accessToken = generationToken(claimsAccess, validityAccessTokenInMilliseconds);

            Claims claimsRefresh = Jwts.claims().setSubject(user.getId().toString());
            claimsRefresh.put("user_id", user.getId());

            TokenObject tokenObject = new TokenObject();
            tokenObject.setAccessToken(accessToken);
            tokenObject.setExpiresIn(validityAccessTokenInMilliseconds);
            tokenObject.setTokenType("bearer");

            String refreshToken = generationToken(claimsRefresh, validityRefreshTokenInMilliseconds);
            if (token == null) {
                OAuthToken oAuthToken = new OAuthToken();
                oAuthToken.setAccessToken(accessToken);
                oAuthToken.setAccessTokenValidity(true);
                oAuthToken.setClientId(oAuthClient.getClientId());
                oAuthToken.setRefreshToken(refreshToken);
                oAuthToken.setRefreshTokenValidity(true);
                oAuthToken.setUserId(user.getId());
                oAuthToken.setStatus(Status.ACTIVE);

                tokenObject.setRefreshToken(refreshToken);
                oAuthTokenService.create(oAuthToken);
            } else {
                token.setAccessToken(accessToken);
                token.setAccessTokenValidity(true);
                token.setClientId(oAuthClient.getClientId());
                token.setUserId(user.getId());
                token.setStatus(Status.ACTIVE);
                if (token.isRefreshTokenValidity()) {
                    tokenObject.setRefreshToken(token.getRefreshToken());
                }
                else {
                    token.setRefreshToken(refreshToken);
                    tokenObject.setRefreshToken(refreshToken);
                }
                token.setRefreshTokenValidity(true);

                oAuthTokenService.update(token);
            }
            return tokenObject;

        } catch (Exception exc) {
            throw new JwtAuthenticationException(exc.getMessage());
        }
    }

    public TokenObject updateToken(String refreshToken, UUID oAuthClientId) {
        Jws<Claims> refreshClaims = getJwsClaimsFromToken(refreshToken);
        UUID userId = UUID.fromString(refreshClaims.getBody().get("user_id", String.class));
        OAuthToken oAuthToken = oAuthTokenService.findByUserId(userId);
        if (oAuthToken == null)
            throw new BadCredentialsException("Invalid token");
        oAuthTokenService.delete(oAuthToken.getId());
        if (!oAuthToken.getClientId().equals(oAuthClientId))
            throw new BadCredentialsException("Invalid clientId");
        Jws<Claims> accessClaims = getJwsClaimsFromToken(oAuthToken.getAccessToken());

        try {
            String accessToken = generationToken(accessClaims.getBody(), validityAccessTokenInMilliseconds);
            Claims claimsRefresh = refreshClaims.getBody();
            TokenObject tokenObject = new TokenObject();
            tokenObject.setAccessToken(accessToken);
            tokenObject.setExpiresIn(validityAccessTokenInMilliseconds);
            tokenObject.setTokenType("bearer");
            String newRefreshToken = generationToken(claimsRefresh, validityRefreshTokenInMilliseconds);
            tokenObject.setRefreshToken(newRefreshToken);

            oAuthToken.setId(null);
            oAuthToken.setAccessToken(accessToken);
            oAuthToken.setAccessTokenValidity(true);
            oAuthToken.setRefreshToken(newRefreshToken);
            oAuthToken.setRefreshTokenValidity(true);
            oAuthToken.setStatus(Status.ACTIVE);
            oAuthTokenService.create(oAuthToken);

            return tokenObject;
        } catch (Exception exc) {
            throw new JwtAuthenticationException(exc.getMessage());
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey.getEncoded()).parseClaimsJws(token).getBody().getSubject();
    }

    public TokenObject getTokenObjectByToken(String token) {
        Claims claims = getJwsClaimsFromToken(token).getBody();
        UUID userId = UUID.fromString(claims.get("user_id", String.class));
        OAuthToken oAuthToken = oAuthTokenService.findByUserId(userId);

        TokenObject tokenObject = new TokenObject();
        tokenObject.setRefreshToken(oAuthToken.getRefreshToken());
        tokenObject.setExpiresIn(validityAccessTokenInMilliseconds);
        tokenObject.setTokenType("bearer");
        tokenObject.setAccessToken(oAuthToken.getAccessToken());

        return tokenObject;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateAccessToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);
            UUID userId = UUID.fromString(claims.getBody().get("user_id", String.class));
            Date date = claims.getBody().getExpiration();
            OAuthToken oAuthToken = oAuthTokenService.findByUserId(userId);
            if (oAuthToken == null || !oAuthToken.getAccessToken().equals(token))
                return false;
            if (date.before(new Date())) {
                logger.warn("JWT Token is expired.");
                oAuthToken.setAccessTokenValidity(false);
                oAuthTokenService.update(oAuthToken);
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException exc) {
            if (exc instanceof ExpiredJwtException) {
                logger.warn("JWT Token is expired.");
            } else {
                logger.warn("JWT Token is invalid.");
            }
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);
            UUID userId = UUID.fromString(claims.getBody().get("user_id", String.class));
            Date date = claims.getBody().getExpiration();
            OAuthToken oAuthToken = oAuthTokenService.findByUserId(userId);
            if (oAuthToken == null || !oAuthToken.getRefreshToken().equals(token))
                return false;
            if (date.before(new Date())) {
                logger.warn("JWT Token is expired.");
                oAuthToken.setRefreshTokenValidity(false);
                oAuthTokenService.update(oAuthToken);
            }
            return oAuthToken.isRefreshTokenValidity();
        } catch (JwtException | IllegalArgumentException exc) {
            if (exc instanceof ExpiredJwtException) {
                logger.warn("JWT Token is expired.");
            } else {
                logger.warn("JWT Token is invalid.");
            }
            return false;
        }
    }

    public UserModel getUserModelByToken(String token) {
        Jws<Claims> claims = getJwsClaimsFromToken(token);
        UUID clientId = UUID.fromString(claims.getBody().get("user_id", String.class));
        return userService.findById(clientId);
    }

    public UUID getUserIdByToken(String token) {
        Jws<Claims> claims = getJwsClaimsFromToken(token);
        return UUID.fromString(claims.getBody().get("user_id", String.class));
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });

        return result;
    }

    private String generationToken(Claims claims, long validityMilliseconds) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validityMilliseconds))
                .signWith(SignatureAlgorithm.HS512, secretKey.getEncoded());
        return builder.compact();
    }

    private Jws<Claims> getJwsClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getEncoded()).parseClaimsJws(token);
    }

    @PostConstruct
    public void init() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            char[] keyStorePassword = "314adfas6732fdagd9113A4".toCharArray();
            char[] keyPassword = "463129fda4843H21fdaf".toCharArray();
            KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(keyPassword);
            try (InputStream keyStoreData = new FileInputStream("data" + File.separator + "keystore.ks")) {
                keyStore.load(keyStoreData, keyStorePassword);
                KeyStore.SecretKeyEntry secretKeyEntry =
                        (KeyStore.SecretKeyEntry) keyStore.getEntry("secretAlias", entryPassword);
                secretKey = secretKeyEntry.getSecretKey();
            } catch (Exception exc) {
                secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
                keyStore.setEntry("secretAlias", secretKeyEntry, entryPassword);
                try (FileOutputStream keyStoreOutputStream = new FileOutputStream("data" + File.separator + "keystore.ks")) {
                    keyStore.store(keyStoreOutputStream, keyStorePassword);
                }
            }
        } catch (Exception exc) {
            throw new RuntimeException(exc.getMessage());
        }
    }
}
