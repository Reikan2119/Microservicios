package pacientes.hospitalPaciente.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Repository.PacienteRepository;
import pacientes.hospitalPaciente.Repository.ContactoRepository;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ContactoRepository contactoRepository; // Necesario si creamos contactos dinámicamente

    // Listar todos los pacientes
    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    // Buscar paciente por ID
    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // Buscar paciente por RUT
    // Recuerda que debes tener este método definido en tu PacienteRepository
    public Paciente buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    // Guardar paciente asignándole su contacto
    public Paciente guardar(Paciente paciente) {
        // 1. Verificamos si el paciente viene con un objeto contacto
        if (paciente.getContacto() != null) {
            // Si el contacto es nuevo (no tiene ID), debemos guardarlo primero en su tabla
            if (paciente.getContacto().getId() == null) {
                contactoRepository.save(paciente.getContacto());
            }
        } else {
            throw new RuntimeException("No se puede registrar un paciente sin un contacto asociado");
        }
        
        // 2. Guardamos el paciente. Al tener el contacto_id correcto, se enlazará sin problemas
        return pacienteRepository.save(paciente);
    }

    // Actualizar paciente
    public Paciente actualizar(Integer id, Paciente pacienteActualizado) {

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        // Actualizamos los datos básicos del paciente
        pacienteExistente.setRut(pacienteActualizado.getRut());
        pacienteExistente.setNombre(pacienteActualizado.getNombre());
        pacienteExistente.setApellido(pacienteActualizado.getApellido());
        pacienteExistente.setEdad(pacienteActualizado.getEdad());

        // Manejo del Contacto
        if (pacienteActualizado.getContacto() != null) {
            // Si el contacto que envían ya existe en la BD, lo asociamos directamente
            if (pacienteActualizado.getContacto().getId() == null) {
                contactoRepository.save(pacienteActualizado.getContacto());
            }
            pacienteExistente.setContacto(pacienteActualizado.getContacto());
        }

        return pacienteRepository.save(pacienteExistente);
    }

    // Eliminar paciente
    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no existe");
        }
        pacienteRepository.deleteById(id);
    }
}