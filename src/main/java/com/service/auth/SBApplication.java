package com.service.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = "com.service.auth")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.service.auth")
public class SBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SBApplication.class, args);
	}

	// Comma separated list of allowed front end origins, set it in production (e.g. https://app.example.com), default keeps the old "*" behaviour
	@Value("${spring.cors.allowed.origins:*}")
	private String[] corsAllowedOrigins;

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(corsAllowedOrigins).maxAge(3600);
			}
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
	    return new BCryptPasswordEncoder();
	}

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
         
    	http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }  

}
