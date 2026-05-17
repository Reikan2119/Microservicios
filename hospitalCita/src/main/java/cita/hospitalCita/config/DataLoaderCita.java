package cita.hospitalCita.config;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cita.hospitalCita.model.Atencion;
import cita.hospitalCita.model.TipoAtencion;
import cita.hospitalCita.repository.AtencionRepository;
import cita.hospitalCita.repository.TipoAtencionRepository;

@Configuration
public class DataLoaderCita{

    @Bean
    CommandLineRunner initCitaDataBase(AtencionRepository atencionRepo, TipoAtencionRepository tipoAtencionRepo) {
        return args -> {


            if (tipoAtencionRepo.count() == 0) {
                System.out.println("-> Insertando Tipos de Atenciones y Citas de prueba...");

                // 1. Guardamos los tipos de atención internos
                TipoAtencion t1 = tipoAtencionRepo.save(new TipoAtencion(null, "Consulta General"));
                TipoAtencion t2 = tipoAtencionRepo.save(new TipoAtencion(null, "Urgencia Médica"));
                TipoAtencion t3 = tipoAtencionRepo.save(new TipoAtencion(null, "Control Rutina"));

                // 2. Recuperamos instancias limpias desde la BD
                TipoAtencion general = tipoAtencionRepo.findById(t1.getId()).orElseThrow();
                TipoAtencion urgencia = tipoAtencionRepo.findById(t2.getId()).orElseThrow();
                TipoAtencion control = tipoAtencionRepo.findById(t3.getId()).orElseThrow();

                // 3. Creamos las Atenciones vinculando los IDs planos:
                // Parámetros: (id, fechaAtencion, diagnostico, pacienteId, doctorId, tipoAtencion)
                // Asumimos que en el otro microservicio creaste pacientes que generaron IDs 1, 2 y 3.
                // Asumimos IDs de doctores ficticios (ej. 10, 20, 30) o los que maneje tu microservicio de doctores.
                Atencion ate1 = new Atencion(null, new Date(), "Resfrío común, se receta paracetamol.", 1, 1, general);
                Atencion ate2 = new Atencion(null, new Date(), "Esguince de tobillo izquierdo.", 2, 2, urgencia);
                Atencion ate3 = new Atencion(null, new Date(), "Chequeo de presión arterial óptimo.", 3, 3, control);

                // 4. Guardamos en la base de datos de citas
                atencionRepo.save(ate1);
                atencionRepo.save(ate2);
                atencionRepo.save(ate3);

                System.out.println("=== ¡CITAS Y TIPOS DE ATENCION CARGADOS CON ÉXITO! ===");
            } else {
                System.out.println("-> Ya existen datos en la BD de Citas. No se cargó nada.");
            }
        };
    }
}