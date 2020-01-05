package rsoi.lab2.rservice.conf.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import rsoi.lab2.rservice.conf.WebConfig;
import rsoi.lab2.rservice.exception.jwt.JwtAuthenticationException;
import rsoi.lab2.rservice.model.TokenObject;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    private SecretKey secretKey;

    @Value("${jwt.token.expires}")
    private long validityTokenMilliseconds;

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

    public TokenObject getToken(HttpServletRequest req) {
        String auth = req.getHeader("Authorization");
        if (auth != null && auth.startsWith("Basic ")) {
            String basic = auth.substring(6);
            String base64 = String.format("base64(%s:%s)", WebConfig.getGatewayKey(), WebConfig.getGatewaySecret());
            if (basic.replaceAll(" ", "").contains(base64)) {
                Claims claims = Jwts.claims().setSubject(WebConfig.getGatewayKey());
                claims.put("date", new Date());
                claims.put("app_id", WebConfig.getGatewayKey());
                claims.put("name_service", "Result Service");

                String accessToken = generationToken(claims);

                TokenObject token = new TokenObject();
                token.setAccessToken(accessToken);
                token.setExpiresIn(validityTokenMilliseconds);
                token.setTokenType("basic");
                return token;
            }
        }
        throw new JwtAuthenticationException("Header is invalid");
    }

    private String generationToken(Claims claims) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, secretKey.getEncoded());
        return builder.compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);
            Date date = new Date(claims.getBody().get("date", Date.class).getTime() + validityTokenMilliseconds);
            String appId = getAppId(token);
            String nameService = claims.getBody().get("name_service", String.class);
            if (!appId.equals(WebConfig.getGatewayKey()) || !nameService.equals("Result Service")) {
                logger.warn("JWT Token is invalid.");
                return false;
            }
            if (date.before(new Date())) {
                logger.warn("JWT Token is expired.");
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

    private Jws<Claims> getJwsClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey.getEncoded()).parseClaimsJws(token);
    }

    private String getAppId(String token) {
        Jws<Claims> claims = getJwsClaimsFromToken(token);
        return claims.getBody().get("app_id", String.class);
    }

    Authentication getAuthentication(String token) {
        Jws<Claims> claims = getJwsClaimsFromToken(token);
        String appId = claims.getBody().get("app_id", String.class);
        if (appId.equals(WebConfig.getGatewayKey())) {
            UserDetails userDetails = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add((GrantedAuthority) () -> "ADMIN");
                    return authorities;
                }

                @Override
                public String getPassword() {
                    return WebConfig.getGatewaySecret();
                }

                @Override
                public String getUsername() {
                    return WebConfig.getGatewayKey();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }
        throw new JwtAuthenticationException("Invalid authentication");
    }
}
