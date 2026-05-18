package com.hospital.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(com.hospital.ficha.Repository.fichaRepository fichaRepository) {
        return args -> {
            
                
            
        };
    }
}
