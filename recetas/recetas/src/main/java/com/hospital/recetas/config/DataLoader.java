package com.hospital.recetas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Repository.recetaRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(recetaRepository recetaRepository) {
        return args -> {
            if (recetaRepository.count() == 0) {
                recetaRepository.save(new recetaModel(null, "RX-1001", "Ibuprofeno 400mg, Omeprazol 20mg", "2026-12-31"));
                recetaRepository.save(new recetaModel(null, "RX-1002", "Amoxicilina 500mg, Paracetamol 500mg", "2026-11-15"));
                recetaRepository.save(new recetaModel(null, "RX-1003", "Metformina 850mg, Atorvastatina 20mg", "2027-01-10"));
            }
            else {
                System.out.println("Ya existen recetas en la base de datos, no se cargaran datos");
            }
        };
    }
}
