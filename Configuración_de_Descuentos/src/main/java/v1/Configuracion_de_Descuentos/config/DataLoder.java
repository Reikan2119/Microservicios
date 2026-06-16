/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.Configuracion_de_Descuentos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import v1.Configuracion_de_Descuentos.repositorio.Repositorio_descuentos;

/**
 *
 * @author diego
 */
@Configuration
public class DataLoder {

    @Bean
    public CommandLineRunner initDescuentosDataBase(Repositorio_descuentos descuentosrepo) {
        return args -> {

            // Verificamos si la tabla de configuraciones de descuento está vacía
            if (descuentosrepo.count() == 0) {
                System.out.println("-> Insertando Campañas y Descuentos de prueba...");

                // Usamos el constructor de Lombok (@AllArgsConstructor):
                // Parámetros: (id, nombrePromocion, porcentajeDescuento, codigoAutorizacion, activo)

                // 1. Descuento enfocado en el adulto mayor (Estacional / Activo)
                Modelo_configuracion_de_descuento promo1 = new Modelo_configuracion_de_descuento(
                    null,
                    "Campaña Mes del Adulto Mayor",
                    15.0, // 15% de descuento
                    "ADULTO-MAYOR-15",
                    true  // Habilitado
                );

                // 2. Descuento por Alianza Estratégica o Empresa (Permanente / Activo)
                Modelo_configuracion_de_descuento promo2 = new Modelo_configuracion_de_descuento(
                    null,
                    "Convenio Empresa",
                    10.0, // 10% de descuento
                    "CONVENIO-EMP-10",
                    true  // Habilitado
                );

                // 3. Promoción que ya caducó o está deshabilitada (Útil para probar validaciones de caja)
                Modelo_configuracion_de_descuento promo3 = new Modelo_configuracion_de_descuento(
                    null,
                    "Promoción Black Friday Pasado",
                    30.0, // 30% de descuento
                    "BLACK-FRIDAY-30",
                    false // Inactivo / Deshabilitado
                );

                // Guardamos las configuraciones en la base de datos
                descuentosrepo.save(promo1);
                descuentosrepo.save(promo2);
                descuentosrepo.save(promo3);

                System.out.println("=== ¡CONFIGURACIONES DE DESCUENTOS CARGADAS CON ÉXITO! ===");
            } else {
                System.out.println("-> Ya existen datos en la BD de Descuentos. No se cargó nada.");
            }
        };
    }
}
