package com.pensasha.emoney.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.pensasha.emoney.user.UserDetailsServiceImp;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/", "/error", "/changePassword", "/img/**",
                                "/fontawesome-free/**", "/js/**", "/css/**",
                                "/webjars/**")
                        .permitAll().anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password").permitAll()
                        .successHandler(customSuccessHandler))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/?logout"))
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/403"))
                .csrf(csrf -> csrf.disable());

        return http.build();

    }

    @Bean(name = "passordEncoder")
    public BCryptPasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

}
