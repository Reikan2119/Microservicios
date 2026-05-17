package pacientes.hospitalPaciente.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Repository.PacienteRepository;
import pacientes.hospitalPaciente.Repository.ContactoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDataBase(PacienteRepository pacienteRepo, ContactoRepository contactoRepo) {
        return args -> {

            System.out.println("=== EJECUTANDO DATALOADER DE PACIENTES ===");

            // Validamos si ya existen pacientes para no duplicar datos
            if (pacienteRepo.count() == 0) {
                System.out.println("-> Insertando contactos y pacientes (Relación limpia)...");

                // 1. Creamos los contactos puros (id, telefono, email) -> ¡SIN LISTAS!
                Contacto conCompartido = new Contacto(null, "+56912345678", "familia.perez@email.com");
                Contacto conIndependiente = new Contacto(null, "+56955554444", "carlos.munoz@email.com");

                // 2. Guardamos los contactos primero para generar sus IDs en la BD
                contactoRepo.save(conCompartido);
                contactoRepo.save(conIndependiente);

                // 3. Creamos los pacientes asignándoles su respectivo contacto
                // (id, rut, nombre, apellido, edad, contacto)
                Paciente pac1 = new Paciente(null, "11111111-1", "Juan", "Pérez", 30, conCompartido);
                Paciente pac2 = new Paciente(null, "22222222-2", "María", "Pérez", 12, conCompartido); // Reutiliza el contacto de Juan
                Paciente pac3 = new Paciente(null, "33333333-3", "Carlos", "Munoz", 45, conIndependiente);

                // 4. Guardamos los pacientes en la base de datos
                pacienteRepo.save(pac1);
                pacienteRepo.save(pac2);
                pacienteRepo.save(pac3);

                System.out.println("=== ¡PACIENTES REGISTRADOS CON ÉXITO SIN USAR LISTAS! ===");
            } else {
                System.out.println("-> Ya existen pacientes en la base de datos. No se cargó nada.");
            }
        };
    }
}