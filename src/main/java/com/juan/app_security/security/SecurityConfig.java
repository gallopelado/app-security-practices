package com.juan.app_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        //http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
        http.authorizeHttpRequests(auth ->
                        //auth.requestMatchers("/loans", "/balance", "/accounts", "/cards")
                        auth
                                .requestMatchers("/loans","/balance").hasRole("USER")
                                .requestMatchers("/accounts", "/cards").hasRole("ADMIN")
                                .anyRequest().permitAll()
                ).formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        // Si NO configuraste el método CorsConfigurationSource corsConfigurationSource()
        // deshabilita los cors por momentos
        //http.cors(AbstractHttpConfigurer::disable);
        //http.csrf(AbstractHttpConfigurer::disable);

        // Si ya configuraste el método CorsConfigurationSource corsConfigurationSource()
        http.cors(cors -> corsConfigurationSource());

        http.csrf(csrf -> csrf
                .csrfTokenRequestHandler(requestHandler)
                // ignorar en
                .ignoringRequestMatchers("/welcome", "/about-us")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())

        ).addFilterAfter( new CsrfCookieFilter(), BasicAuthenticationFilter.class );

        return http.build();
    }

    /*@Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var admin = User.withUsername("admin").password("1").authorities("ADMIN").build();
        var user = User.withUsername("user").password("1").authorities("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }*/

    // para usar el datasource se debe importar
    // en el pom el jpa starter
//    @Bean
//    UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();

        // Esta es un manera de agregar dominios permitidos
        // config.setAllowedOrigins(List.of("http://localhost:4200", "http://otrodominio.com"));

        // Cualquier página está permitida
        config.setAllowedOrigins(List.of("*"));

        // Por verbo http
        // config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // Todos los headers
        config.setAllowedHeaders(List.of("*"));

        // Todos los verbos http
        config.setAllowedMethods(List.of("*"));

        var source = new UrlBasedCorsConfigurationSource();
        // registra todas las configuraciones
        source.registerCorsConfiguration("/**", config);

        return source;
    }

}
