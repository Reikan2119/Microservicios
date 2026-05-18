package pacientes.hospitalPaciente.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Service.ContactoService;

@RestController
@RequestMapping("/api/v1/contactos")
public class ContactoController {

    @Autowired
    private ContactoService service;

    @GetMapping
    public ResponseEntity<List<Contacto>> listar() {
        List<Contacto> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contacto> buscarPorId(@PathVariable Integer id) {
        try {
            Contacto contacto = service.buscarPorId(id);
            return ResponseEntity.ok(contacto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Contacto> guardar(@RequestBody Contacto contacto) {
        Contacto nuevo = service.guardar(contacto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contacto> actualizar(@PathVariable Integer id, @RequestBody Contacto contacto) {
        try {
            Contacto actualizado = service.actualizar(id, contacto);
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
}