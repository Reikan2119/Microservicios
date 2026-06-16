package pacientes.hospitalPaciente.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Repository.ContactoRepository;

@Service
public class ContactoService {

    @Autowired
    private ContactoRepository repository;

    public List<Contacto> listar() {
        return repository.findAll();
    }

    public Contacto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contacto no encontrado con el ID: " + id));
    }

    public Contacto guardar(Contacto contacto) {
        return repository.save(contacto);
    }

    public Contacto actualizar(Integer id, Contacto contactoActualizado) {
        Contacto contactoExistente = buscarPorId(id);
        contactoExistente.setTelefono(contactoActualizado.getTelefono());
        contactoExistente.setEmail(contactoActualizado.getEmail());
        
        return repository.save(contactoExistente);
    }

    public void eliminar(Integer id) {
        Contacto contacto = buscarPorId(id);
        repository.delete(contacto);
    }
}