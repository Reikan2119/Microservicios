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
    private ContactoRepository contactoRepository;

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPorId(Integer id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    public Paciente buscarPorRut(String rut) {
        return pacienteRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
    }

    public Paciente guardar(Paciente paciente) {
        if (paciente.getContacto() != null) {
            if (paciente.getContacto().getId() == null) {
                contactoRepository.save(paciente.getContacto());
            }
        } else {
            throw new RuntimeException("No se puede registrar un paciente sin un contacto asociado");
        }
        
        return pacienteRepository.save(paciente);
    }

    public Paciente actualizar(Integer id, Paciente pacienteActualizado) {

        Paciente pacienteExistente = pacienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        pacienteExistente.setRut(pacienteActualizado.getRut());
        pacienteExistente.setNombre(pacienteActualizado.getNombre());
        pacienteExistente.setApellido(pacienteActualizado.getApellido());
        pacienteExistente.setEdad(pacienteActualizado.getEdad());

        if (pacienteActualizado.getContacto() != null) {
            if (pacienteActualizado.getContacto().getId() == null) {
                contactoRepository.save(pacienteActualizado.getContacto());
            }
            pacienteExistente.setContacto(pacienteActualizado.getContacto());
        }
        return pacienteRepository.save(pacienteExistente);
    }

    public void eliminar(Integer id) {
        if (!pacienteRepository.existsById(id)) {
            throw new RuntimeException("Paciente no existe");
        }
        pacienteRepository.deleteById(id);
    }
}