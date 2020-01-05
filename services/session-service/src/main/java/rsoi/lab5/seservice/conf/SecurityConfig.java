package rsoi.lab5.seservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import rsoi.lab5.seservice.conf.jwt.JwtAuthenticationEntryPoint;
import rsoi.lab5.seservice.conf.jwt.JwtConfigurer;
import rsoi.lab5.seservice.service.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String OAUTH_ENDPOINT = "/api/v1/oauth/**";
    private static final String REGISTRATION_ENDPOINT = "/api/v1/registration";
    private static final String USERS_BY_ID_ENDPOINT = "/api/v1/users/{id}";
    private static final String CLIENTS_BY_ID_ENDPOINT = "/api/v1/clients/{id}";
    private static final String REG_USER_ENDPOINT = "/api/v1/users/reg";
    private static final String REG_CLIENT_ENDPOINT = "/api/v1/clients/reg";
    private static final String SIGN_IN_ENDPOINT = "/api/v1/sign_in";
    private static final String CSS_ENDPOINT = "/css/**";
    private static final String JS_ENDPOINT = "/js/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(OAUTH_ENDPOINT, REGISTRATION_ENDPOINT, CSS_ENDPOINT, JS_ENDPOINT, SIGN_IN_ENDPOINT,
                        REG_CLIENT_ENDPOINT, REG_USER_ENDPOINT, USERS_BY_ID_ENDPOINT, CLIENTS_BY_ID_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }
}
