package doctor.hospitalDoctor.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.repository.DoctorRepository;
import doctor.hospitalDoctor.repository.EspecialidadRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(DoctorRepository doctorRepo, EspecialidadRepository especialidadRepo) {
        return args -> {

            // 1. Declaramos las variables que usaremos para los doctores
            Especialidad cardiologia = null;
            Especialidad pediatria = null;
            Especialidad traumatologia = null;

            // 2. Manejo de Especialidades
            if (especialidadRepo.count() == 0) {
                // Creamos los objetos temporales
                Especialidad esp1 = new Especialidad(null, "Cardiologia");
                Especialidad esp2 = new Especialidad(null, "Pediatria");
                Especialidad esp3 = new Especialidad(null, "Traumatologia");

                // ¡CRUCIAL! Guardamos y asignamos el resultado retornado por la BD.
                // Esto hace que ahora sí lleven el ID autoincremental (1, 2, 3...)
                cardiologia = especialidadRepo.save(esp1);
                pediatria = especialidadRepo.save(esp2);
                traumatologia = especialidadRepo.save(esp3);

                System.out.println("Especialidades creadas y vinculadas con éxito.");
            } else {
                // Si ya existían, las recuperamos dinámicamente de la lista
                List<Especialidad> lista = especialidadRepo.findAll();
                if (lista.size() >= 3) {
                    cardiologia = lista.get(0);
                    pediatria = lista.get(1);
                    traumatologia = lista.get(2);
                }
            }

            // 3. Manejo de Doctores
            if (doctorRepo.count() == 0) {
                // Verificamos que las especialidades estén listas en memoria
                if (cardiologia != null && pediatria != null && traumatologia != null) {
                    
                    // Pasamos las variables que ya contienen sus IDs gestionados por JPA
                    Doctor doc1 = new Doctor(null, "Cristhyan", "Silva", "23207146-9", cardiologia);
                    Doctor doc2 = new Doctor(null, "Thomas", "Peralta", "22264672-1", pediatria);
                    Doctor doc3 = new Doctor(null, "Diego", "Gaete", "21352167-3", traumatologia);

                    doctorRepo.save(doc1);
                    doctorRepo.save(doc2);
                    doctorRepo.save(doc3);

                    System.out.println("Doctores cargados con éxito en la Base de Datos.");
                } else {
                    System.out.println("No se pudieron cargar los doctores: faltan especialidades.");
                }
            } else {
                System.out.println("Los doctores ya existen en la base de datos.");
            }

        };
    }
}