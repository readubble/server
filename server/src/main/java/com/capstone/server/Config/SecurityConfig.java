package com.capstone.server.Config;

import com.capstone.server.Auth.CustomAccessDeniedHandler;
import com.capstone.server.Auth.CustomAuthenticationEntryPoint;
import com.capstone.server.Auth.CustomFilter;
import com.capstone.server.JWT.JwtAuthenticationFilter;
import com.capstone.server.JWT.JwtAuthorizationFilter;
import com.capstone.server.Repository.TokenRepository;
import com.capstone.server.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private final CorsConfig corsConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class)
                .apply(new CustomDsl())
                .and()
                .authorizeRequests(authorize -> authorize.antMatchers("/", "/users", "/users/authorize", "/swagger-ui/**", "/v3/api-docs/**", "/users/authorize/token").permitAll()
                        .anyRequest().hasRole("USER"))
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity> {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            final JwtAuthenticationFilter filter = new JwtAuthenticationFilter(authenticationManager);
            filter.setFilterProcessesUrl("/users/authorize");
            http.addFilter(corsConfig.corsFilter())
                    .addFilter(filter)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, tokenRepository));
        }
    }


}
