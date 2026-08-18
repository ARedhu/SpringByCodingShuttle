package com.codingShuttle.Ashish.SpringSecurity.config;

import com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role;
import com.codingShuttle.Ashish.SpringSecurity.filters.JwtAuthFilter;
import com.codingShuttle.Ashish.SpringSecurity.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.codingShuttle.Ashish.SpringSecurity.entities.enums.Permission.POST_CREATE;
import static com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role.ADMIN;
import static com.codingShuttle.Ashish.SpringSecurity.entities.enums.Role.CREATOR;

@Configuration
@EnableWebSecurity // This means we want to create/modify security filter chain.
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes = {
            "/error", "/auth/**", "/home.html"
    };

    // Role: We can have multiple kind of roles: "USER", "ADMIN", "MANAGER" etc.
    // Authorities: Each role can be associated with some authorities/permissions. We assign that particular role to the user and it gets the mentioned permissions automatically.

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicRoutes).permitAll()
                        .requestMatchers(HttpMethod.GET, "/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                        .requestMatchers(HttpMethod.POST, "/posts/**").hasAuthority(POST_CREATE.name()) // Remeber if the upper condition satisfies it will never check this condition and will let the client to continue with the path/api.
                        .anyRequest().authenticated())
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth2Config -> oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );
               // .formLogin(Customizer.withDefaults()); If we do the upper two things than there is no need of this form. And, we in production applications don't use this form.

        return httpSecurity.build();
    }

    // Remember the below way of storing the user is not recommended. As we are storing it in program's memory. But in production applications we store it in some database.
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//        UserDetails normalUser = User
//                .withUsername("Ashu")
//                .password(passwordEncoder().encode("Ashu123"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("Admin")
//                .password(passwordEncoder().encode("Admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser, adminUser);
//    }



    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
