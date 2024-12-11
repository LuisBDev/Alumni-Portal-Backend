package com.alumniportal.unmsm.config.security;

import com.alumniportal.unmsm.repository.ICompanyRepository;
import com.alumniportal.unmsm.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICompanyRepository companyRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            // Intenta buscar primero en los usuarios
            var user = userRepository.findByEmail(email);
            if (user != null) {
                return user;
            }

            // Si no encuentra el usuario, busca en las compañías
            var company = companyRepository.findByEmail(email);
            if (company != null) {
                return company;
            }

            // Si no encuentra ni usuario ni compañía, lanza una excepción
            throw new UsernameNotFoundException("Entity not found");
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
}
