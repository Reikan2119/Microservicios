package com.hospital.notificaciones.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hospital.notificaciones.Model.notificacionModel;
import com.hospital.notificaciones.Repossitory.notificacionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(notificacionRepository notificacionRepository) {
        return args -> {
            if (notificacionRepository.count() == 0) {
                notificacionRepository.save(new notificacionModel(null, "Email", "Su cita ha sido confirmada para el próximo lunes.", "Enviado"));
                notificacionRepository.save(new notificacionModel(null, "SMS", "Recordatorio: examen de laboratorio mañana a las 8:00 AM.", "Pendiente"));
                notificacionRepository.save(new notificacionModel(null, "Whatsapp", "Su receta ya está disponible para retiro en la farmacia.", "Enviado"));
            }
            else {System.out.println("Ya existen notificaciones en la base de datos, no se cargaran datos");
            }
        };
    }
}
