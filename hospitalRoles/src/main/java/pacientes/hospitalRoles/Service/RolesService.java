package pacientes.hospitalRoles.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pacientes.hospitalRoles.Model.Roles;
import pacientes.hospitalRoles.Repository.RolesRepository;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository; // 👈 Inyección única y correcta

    public List<Roles> listarRoles() {
        return rolesRepository.findAll();
    }

    public Roles buscarPorId(Integer id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

   
    public Roles buscarPorNombre(String nombre) {
        return rolesRepository.findByNombreRoles(nombre) 
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + nombre));
    }

    public Roles guardar(Roles rol) {
        if (rol.getNombreRoles() == null || rol.getNombreRoles().trim().isEmpty()) {
            throw new RuntimeException("El nombre del rol no puede estar vacío");
        }
        return rolesRepository.save(rol);
    }

    public Roles actualizar(Integer id, Roles rolActualizado) {
        Roles rolExistente = rolesRepository.findById(id).orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        rolExistente.setNombreRoles(rolActualizado.getNombreRoles());
        return rolesRepository.save(rolExistente);
    }

    public void eliminar(Integer id) {
        if (!rolesRepository.existsById(id)) {
            throw new RuntimeException("El rol no existe");
        }
        rolesRepository.deleteById(id);
    }
}