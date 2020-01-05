package rsoi.lab2.gservice.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import rsoi.lab2.gservice.conf.jwt.JwtAuthenticationEntryPoint;
import rsoi.lab2.gservice.conf.jwt.JwtConfigurer;
import rsoi.lab2.gservice.service.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    private static final String ALL_ENDPOINT = "/api/v1/**";
    private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
    private static final String OAUTH_ENDPOINT = "/api/v1/oauth/**";

    @Autowired
    public SecurityConfig(JwtTokenProvider provider) {
        this.jwtTokenProvider = provider;
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
                .antMatchers(AUTH_ENDPOINT).authenticated()
                .antMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                .antMatchers(OAUTH_ENDPOINT).permitAll()
                .anyRequest().permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }

}
