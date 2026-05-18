package pacientes.hospitalRoles.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pacientes.hospitalRoles.Model.Roles;
import pacientes.hospitalRoles.Service.RolesService;
import pacientes.hospitalRoles.dto.RolesDTO;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController { 

    @Autowired
    private RolesService service;

    @GetMapping
    public ResponseEntity<List<Roles>> listar() {
        List<Roles> roles = service.listarRoles(); 
        if (roles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> buscarPorId(@PathVariable Integer id) {
        try {
            Roles rol = service.buscarPorId(id);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Roles> buscarPorNombre(@PathVariable String nombre) {
        try {
            Roles rol = service.buscarPorNombre(nombre);
            return ResponseEntity.ok(rol);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Roles> guardar(@RequestBody Roles rol) {
        try {
            Roles nuevo = service.guardar(rol);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> actualizar(@PathVariable Integer id, @RequestBody Roles rol) {
        try {
            Roles actualizado = service.actualizar(id, rol);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<RolesDTO> obtenerRolDTO(@PathVariable Integer id) {
        try {
            Roles rol = service.buscarPorId(id);
            RolesDTO dto = new RolesDTO(
                    rol.getId(),
                    rol.getNombreRoles()
            );
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}