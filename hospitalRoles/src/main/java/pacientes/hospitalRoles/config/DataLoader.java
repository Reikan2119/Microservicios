package pacientes.hospitalRoles.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pacientes.hospitalRoles.Model.Roles;
import pacientes.hospitalRoles.Repository.RolesRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(RolesRepository rolesRepo) { // 👈 Eliminamos el repositorio de contactos
        return args -> {

            if (rolesRepo.count() == 0) {
                System.out.println("-> Insertando Roles iniciales del sistema...");

                Roles rol1 = new Roles(null, "Doctor");
                Roles rol2 = new Roles(null, "Paciente");
                Roles rol3 = new Roles(null, "Secretaria");

                rolesRepo.save(rol1);
                rolesRepo.save(rol2);
                rolesRepo.save(rol3);

                System.out.println("=== ¡ROLES REGISTRADOS CON ÉXITO! ===");
            } else {
                System.out.println("-> Ya existen roles en la base de datos. No se cargó nada.");
            }
        };
    }
}