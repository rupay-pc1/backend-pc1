package com.pc1.backendrupay.configs.security;
import com.pc1.backendrupay.configs.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOriginPattern("*"); // Permitir todas as origens
        corsConfiguration.addAllowedMethod("*"); // Permitir todos os métodos (GET, POST, PUT, DELETE, etc.)
        corsConfiguration.addAllowedHeader("*"); // Permitir todos os cabeçalhos
        corsConfiguration.setAllowCredentials(true); // Permitir credenciais

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> corsConfiguration)) // Aplicar a configuração CORS
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/error/**").permitAll();
                    auth.requestMatchers("/api/**").permitAll();
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/payment/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .build();
    }
}

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(
//                        auth -> auth.requestMatchers("/api/auth/**").permitAll()
////                                .requestMatchers("/users/**", "/apps/**").hasAuthority("ADMIN")
////                                .requestMatchers("/myapps/**").hasAuthority("STUDENT")
//                                //TODO Nesse local, esta permitindo que qualquer um acesse a API, para qualquer endpoint
//                                // É necessário corrigir isso, quando os endpoints estiverem prontos, através do uso de hasAuthority
//                                // Atribuindo a permissão de acesso a cada endpoint
//                                .requestMatchers("/api/user/**").permitAll()
//                                .requestMatchers("/api/**").permitAll()
//                               .anyRequest().authenticated())
////                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout(logout -> logout.logoutUrl("/api/auth/logout")
//                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                );
//
//
//
//        return http.build();
//    }
//}



