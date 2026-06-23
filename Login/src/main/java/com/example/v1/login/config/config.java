package com.example.v1.login.config;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.v1.login.Model.Rol;
import com.example.v1.login.Model.Usuario;
import com.example.v1.login.repositorio.Repositorio;


@Configuration
public class config {
    @Bean
    public CommandLineRunner initcajaDataBase(Repositorio repository) {
        return args -> {

            if (repository.count() == 0) {
                System.out.println("-> Insertando cuentas de prueba...");

                Usuario juanUsuario = new Usuario(
                    null,
                    "Juan",
                    "JuanPérez@gmail.com",
                    "password123",
                    Rol.DOCTOR
                );

                // Registro 2: Cierre de caja del turno tarde
                Usuario MariaUsuario = new Usuario(
                    null,
                    "María",
                    "MaríaGonzález@gmail.com",
                    "password456",
                    Rol.PACIENTE
                );

                // Registro 3: Cierre parcial de hoy
                Usuario CarlosUsuario = new Usuario(
                    null,
                    "Carlos",
                    "CarlosRodríguez@gmail.com",
                    "password789",
                    Rol.ADMINISTRADOR
                );
                repository.save(juanUsuario);
                repository.save(MariaUsuario);
                repository.save(CarlosUsuario);

                System.out.println("=== ¡Cuentas de prueba cargadas con éxito! ===");
            } else {
                System.out.println("-> Ya existen cuentas de usuario en la BD. No se cargó nada.");
            }
        };
    }
    
}

