package pacientes.hospitalPaciente.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Repository.PacienteRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    //Listar todos los pacientes
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    //Buscar paciente por RUT
    public Paciente buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // guardar paciente completo (Paciente + Dirección + Contacto)
    public Paciente guardar(Paciente paciente) {
        // 1. Verificamos si el paciente trae un objeto contacto
        if (paciente.getContacto() != null) {
            // 2. IMPORTANTE: Le decimos al contacto quién es su "dueño" (el paciente)
            // Esto asegura que la columna paciente_id en la tabla 'contacto' no sea NULL
            paciente.getContacto().setPaciente(paciente);
        }
        
        // 3. Al guardar el paciente, el CascadeType.ALL se encarga del resto
        return pacienteRepository.save(paciente);
    }

    //Actualizar paciente
    public Paciente actualizar(Integer id, Paciente pacienteActualizado) {

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        paciente.setRut(pacienteActualizado.getRut());
        paciente.setNombre(pacienteActualizado.getNombre());
        paciente.setApellido(pacienteActualizado.getApellido());
        paciente.setEdad(pacienteActualizado.getEdad());

        // Contacto
        if (pacienteActualizado.getContacto() != null) {
            pacienteActualizado.getContacto().setPaciente(paciente);
            paciente.setContacto(pacienteActualizado.getContacto());
        }

        return pacienteRepository.save(paciente);
    }

    // Eliminar paciente
    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no existe");
        }
        pacienteRepository.deleteById(id);
    }
}