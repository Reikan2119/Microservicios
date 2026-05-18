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

            if (pacienteRepo.count() == 0) {
                System.out.println("-> Insertando contactos y pacientes (Relación limpia)...");

                Contacto conCompartido = new Contacto(null,"juanito" ,"+56912345678", "familia.perez@email.com");
                Contacto conIndependiente = new Contacto(null,"Carlos" ,"+56955554444", "carlos.munoz@email.com");

                contactoRepo.save(conCompartido);
                contactoRepo.save(conIndependiente);

                Paciente pac1 = new Paciente(null, "11111111-1", "Juan", "Pérez", 30, conCompartido);
                Paciente pac2 = new Paciente(null, "22222222-2", "María", "Pérez", 12, conCompartido); 
                Paciente pac3 = new Paciente(null, "33333333-3", "Carlos", "Munoz", 45, conIndependiente);

                pacienteRepo.save(pac1);
                pacienteRepo.save(pac2);
                pacienteRepo.save(pac3);

                System.out.println("=== ¡PACIENTES REGISTRADOS CON ÉXITO");
            } else {
                System.out.println("-> Ya existen pacientes en la base de datos. No se cargó nada.");
            }
        };
    }
}