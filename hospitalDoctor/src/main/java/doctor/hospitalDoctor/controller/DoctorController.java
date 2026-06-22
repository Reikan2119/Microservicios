package doctor.hospitalDoctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import doctor.hospitalDoctor.dto.DoctorDTO;
import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.service.DoctorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/doctores")
// Agrupa los endpoints en Swagger UI bajo la sección de Personal Médico
@Tag(name = "Doctor", description = "Operaciones para la gestión del personal médico del hospital y sus especialidades")
public class DoctorController {

    @Autowired
    private DoctorService service;

    // GET: Listar todos los doctores
    @GetMapping
    @Operation(summary = "Listar todos los doctores", description = "Retorna una lista completa con todos los médicos registrados y sus especialidades asociadas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de doctores obtenido con éxito"),
        @ApiResponse(responseCode = "204", description = "No existen doctores registrados en el sistema")
    })
    public ResponseEntity<List<Doctor>> listar() {
        List<Doctor> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    // GET: Buscar doctor por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar doctor por ID", description = "Busca y retorna la información de un médico específico mediante su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Doctor localizado de forma exitosa"),
        @ApiResponse(responseCode = "404", description = "El médico solicitado no existe con el ID provisto")
    })
    public ResponseEntity<Doctor> buscar(
        @Parameter(description = "ID único del doctor a consultar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Registrar un nuevo médico
    @PostMapping
    @Operation(summary = "Registrar nuevo doctor", description = "Crea un nuevo registro de médico en el sistema adjuntando su especialidad")
    @ApiResponse(responseCode = "200", description = "Médico registrado correctamente en el sistema")
    public ResponseEntity<Doctor> guardar(@RequestBody Doctor doctor) {
        return ResponseEntity.ok(service.guardar(doctor));
    }

    // PUT: Actualizar información de un doctor
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos del doctor", description = "Modifica los campos del registro de un médico localizándolo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Información del doctor actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar debido a que el ID no existe")
    })
    public ResponseEntity<Doctor> actualizar(
        @Parameter(description = "ID del doctor que se desea modificar", example = "1", required = true)
        @PathVariable Integer id, 
        @RequestBody Doctor doctor) {
        try {
            Doctor actualizado = service.actualizar(id, doctor); 
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar un médico del sistema
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un doctor", description = "Remueve físicamente el registro del médico utilizando su ID correlativo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Médico removido exitosamente del sistema"),
        @ApiResponse(responseCode = "404", description = "El ID provisto no coincide con ningún médico en los registros")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del doctor que se dará de baja", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Obtener vista resumida (DTO)
    @GetMapping("/dto/{id}")
    @Operation(
        summary = "Obtener vista resumida del doctor (DTO)", 
        description = "Retorna un objeto aplanado ideal para integraciones externas, uniendo nombres y mostrando únicamente el texto de la especialidad"
    )
    @ApiResponse(responseCode = "200", description = "Estructura resumida del DTO procesada exitosamente")
    public ResponseEntity<DoctorDTO> obtenerDoctorDTO(
        @Parameter(description = "ID del doctor para mapear a la vista DTO", example = "1", required = true)
        @PathVariable Integer id) {
        Doctor doctor = service.buscarPorId(id);
        DoctorDTO dto = new DoctorDTO(
                doctor.getId(),
                doctor.getNombre()+ " " + doctor.getApellido(),
                doctor.getEspecialidad().getNombre()
        );
        return ResponseEntity.ok(dto);
    }
}