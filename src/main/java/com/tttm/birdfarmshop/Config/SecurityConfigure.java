package com.tttm.birdfarmshop.Config;

import com.tttm.birdfarmshop.Enums.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigure {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(cs -> {
                    cs.disable();
                })
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/auth/**")
                                    .permitAll();

                            auth.requestMatchers("/typeOfBird/**, /food/**, /nest/**, /bird/**, /order/**")
                                    .permitAll();

                            auth.requestMatchers("/email/**")
                                    .permitAll();

                            auth.requestMatchers("/admin/**")
                                    .hasRole(ERole.ADMINISTRATOR.name())
                                    .anyRequest()
                                    .permitAll();

                        }
                )
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new SimpleCORSFilter(), WebAsyncManagerIntegrationFilter.class);

        return httpSecurity.build();
    }
}