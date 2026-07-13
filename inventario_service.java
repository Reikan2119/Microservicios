/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.inventario.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import v1.inventario.modelo.inventario_item;
import v1.inventario.repositorio.inventario_repo;

/**
 *
 * @author diego
 */

@Configuration
public class DataLoder {
    @Bean
    public CommandLineRunner initDataBase(inventario_repo itemRepository) {
        return args -> {

            if (itemRepository.count() == 0) {
                System.out.println("-> Insertando Insumos de Inventario Podológico de prueba...");

                // Usamos el constructor generado por @AllArgsConstructor de Lombok:
                // Parámetros: (id, sku, nombre, stockActual, fechaVencimientoLote)
                inventario_item insumo1 = new inventario_item(
                    null,
                    "BISTURI-N15-MED", 
                    "Bisturí Quirúrgico N°15", 
                    120, 
                    LocalDate.of(2028, 5, 20)
                );

                inventario_item insumo2 = new inventario_item(
                    null,
                    "ALC-ISO-1L", 
                    "Alcohol Isopropílico Desinfectante 1 Litro", 
                    15, 
                    LocalDate.of(2026, 12, 15)
                );

                inventario_item insumo3 = new inventario_item(
                    null,
                    "GUANTE-L-LATEX", 
                    "Guantes de Examinación Látex Talla L (Caja x100)", 
                    8, 
                    LocalDate.of(2029, 3, 10)
                );

                itemRepository.save(insumo1);
                itemRepository.save(insumo2);
                itemRepository.save(insumo3);

                System.out.println("=== ¡INSUMOS DE INVENTARIO PODOLÓGICO CARGADOS CON ÉXITO! ===");
            } else {
                System.out.println("-> Ya existen datos en la BD de Inventario. No se cargó nada.");
            }
        };
    }
}
