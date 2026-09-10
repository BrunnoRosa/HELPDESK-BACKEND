package com.example.help_desk.config;

import com.example.help_desk.security.JwtAuthenticationFilter;
import com.example.help_desk.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // IMPORTANTE: Adicionado para liberar o OPTIONS
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource; // IMPORTANTE: Injeção do CORS

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final CorsConfigurationSource corsConfigurationSource; // Adicionado

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            UsuarioService usuarioService,
            PasswordEncoder passwordEncoder,
            CorsConfigurationSource corsConfigurationSource // Injetado via construtor
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. Libera requisições de CORS Preflight e Login
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/auth/login").permitAll()

                        // 2. Libera alteração de senha para qualquer usuário autenticado
                        .requestMatchers(HttpMethod.PUT, "/usuarios/alterar-senha").authenticated()

                        // 3. Restringe o painel administrativo apenas para ADMINISTRADOR
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")

                        // 4. Exige autenticação para qualquer outra requisição
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}