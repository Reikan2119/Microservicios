package pacientes.hospitalPaciente.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Service.PacienteService;
import pacientes.hospitalPaciente.dto.PacienteDTO;


@RestController
@RequestMapping("/api/v1/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = service.listarPacientes();

        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id) {
        try {
            Paciente paciente = service.buscarPorId(id);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Paciente> buscarPorRut(@PathVariable String rut) {
        try {
            Paciente paciente = service.buscarPorRut(rut);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        Paciente nuevo = service.guardar(paciente);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> actualizar(
            @PathVariable Integer id,
            @RequestBody Paciente paciente) {

        try {
            Paciente actualizado = service.actualizar(id, paciente);
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
    public ResponseEntity<PacienteDTO> obtenerPacienteDTO(@PathVariable Integer id) {

    Paciente paciente = service.buscarPorId(id);

    PacienteDTO dto = new PacienteDTO(
            paciente.getId(),
            paciente.getNombre(),
            paciente.getApellido(),
            paciente.getContacto().getNombre(),
            paciente.getContacto().getEmail(),
            paciente.getContacto().getTelefono()
    );

    return ResponseEntity.ok(dto);
}

}