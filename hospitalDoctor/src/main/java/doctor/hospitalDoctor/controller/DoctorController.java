package doctor.hospitalDoctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import doctor.hospitalDoctor.dto.DoctorDTO;
import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctores")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @GetMapping
    public ResponseEntity<List<Doctor>> listar() {
        List<Doctor> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> buscar(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Doctor> guardar(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(service.guardar(doctor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

 @GetMapping("/dto/{id}")
public ResponseEntity<DoctorDTO> obtenerDoctorDTO(@PathVariable Integer id) {
    Doctor doctor = service.buscarPorId(id);
    DoctorDTO dto = new DoctorDTO(
            doctor.getId(),
            doctor.getNombre(),
            doctor.getApellido(),
            doctor.getEspecialidad().getNombre() // extraemos solo el nombre

    );
    return ResponseEntity.ok(dto);
}



}