package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
 
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/pizzas/create", "/pizzas/edit/**").hasAuthority("ADMIN") //solo se sei admin puoi andare su questa rotta
                .requestMatchers(HttpMethod.POST, "/pizzas/**").hasAuthority("ADMIN")//(**)stanno per indicare gli id dinamici 
                .requestMatchers("/offerte", "/offerte/**").hasAuthority("ADMIN")
                .requestMatchers("/ingredienti", "/ingredienti/**").hasAuthority("ADMIN")
                .requestMatchers("/pizzas", "/pizzas/**").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/**").permitAll())
                .formLogin(Customizer.withDefaults())
                .cors(cors-> cors.disable())//da approffondire
                .csrf(csrf -> csrf.disable());//da approffondire

        return http.build();
    }

    @Bean

    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
