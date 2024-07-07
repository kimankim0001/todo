package com.sparta.todo.config;

import com.sparta.todo.jwt.JwtProvider;
import com.sparta.todo.security.AccessDeniedHandlerImpl;
import com.sparta.todo.security.AuthenticationEntryPointImpl;
import com.sparta.todo.security.JwtAuthenticationFilter;
import com.sparta.todo.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;
    private final AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        return new JwtAuthenticationFilter(jwtProvider, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);

        http.sessionManagement(
                (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(
                (authorizationHttpRequests) -> authorizationHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/todos/{todoId}").permitAll()
                        .anyRequest().authenticated()
        );

        http.exceptionHandling(e -> e
                .authenticationEntryPoint(authenticationEntryPointImpl)
                .accessDeniedHandler(accessDeniedHandlerImpl));

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
