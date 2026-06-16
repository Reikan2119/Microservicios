package com.hospital.ficha.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hospital.ficha.Model.fichaModel;  


@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(com.hospital.ficha.Repository.fichaRepository fichaRepository) {
        return args -> {

            if (fichaRepository.count() == 0) {
                fichaRepository.save(new fichaModel(null,
                        "Examen general",
                        "Paciente con antecedentes de diabetes tipo 2.",
                        false,
                        "Diabetes"));
                fichaRepository.save(new fichaModel(null,
                        "Evaluación cardiovascular",
                        "Hipertensión arterial y colesterol alto controlados parcialmente.",
                        true,
                        "Hipertensión, Colesterol alto"));
                fichaRepository.save(new fichaModel(null,
                        "Examen respiratorio",
                        "Asma leve con episodios ocasionales de disnea.",
                        true,
                        "Asma"));
            }else {System.out.println("Ya existen fichas en la base de datos, no se cargaran datos");
            }

        };
    }
}
