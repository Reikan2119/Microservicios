package doctor.hospitalDoctor.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.repository.EspecialidadRepository;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository repository;

    // 🔹 Obtener todas las especialidades
    public List<Especialidad> listar() {
        return repository.findAll();
    }

    // 🔹 Buscar especialidad por ID
    public Especialidad buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con el ID: " + id));
    }

    // 🔹 Guardar / Crear una nueva especialidad
    public Especialidad guardar(Especialidad especialidad) {
        return repository.save(especialidad);
    }

    // 🔹 Actualizar una especialidad existente
    public Especialidad actualizar(Integer id, Especialidad especialidadActualizada) {
        Especialidad especialidadExistente = buscarPorId(id);
        
        // Asumiendo que tu clase Especialidad tiene el atributo 'nombre'
        especialidadExistente.setNombre(especialidadActualizada.getNombre());
        
        return repository.save(especialidadExistente);
    }

    // 🔹 Eliminar una especialidad por ID
    public void eliminar(Integer id) {
        Especialidad especialidad = buscarPorId(id);
        repository.delete(especialidad);
    }
}