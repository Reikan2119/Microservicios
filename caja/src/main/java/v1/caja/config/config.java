/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.caja.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import v1.caja.Modelo.ModeloCaja;
import v1.caja.repositorio.Repositorio_caja;
/**
 *
 * @author diego
 */
@Configuration
public class config {
    @Bean
    public CommandLineRunner initcajaDataBase(Repositorio_caja repository) {
        return args -> {

            if (repository.count() == 0) {
                System.out.println("-> Insertando cierres de caja y registros financieros de prueba...");

                // Registro 1: Cierre de caja del turno mañana
                ModeloCaja cierreManana = new ModeloCaja(
                    null, 
                    0.0,
                     50000.0, 
                     120000.0, 
                     85000.0, 
                     30000.0,
                     "CONVENIO-EMP-10", 
                     LocalDateTime.now().minusDays(1).withHour(14), 
                     10L
                );

                // Registro 2: Cierre de caja del turno tarde
                ModeloCaja cierreTarde = new ModeloCaja(
                    null, 
                    0.0, 
                    20000.0, 
                    180000.0, 
                    200000.0, 
                    95000.0,
                    null, 
                    LocalDateTime.now().minusDays(1).withHour(20), 
                    11L
                );

                // Registro 3: Cierre parcial de hoy
                ModeloCaja cierreHoy = new ModeloCaja(
                    null, 
                    0.0, 
                    15000.0, 
                    60000.0, 
                    45000.0, 
                    0.0,
                    "BLACK-FRIDAY-30", 
                    LocalDateTime.now(), 
                    10L
                );

                repository.save(cierreManana);
                repository.save(cierreTarde);
                repository.save(cierreHoy);

                System.out.println("=== ¡INGRESOS DE CAJA FINANCIEROS CARGADOS CON ÉXITO! ===");
            } else {
                System.out.println("-> Ya existen movimientos financieros en la BD. No se cargó nada.");
            }
        };
    }
    
}
