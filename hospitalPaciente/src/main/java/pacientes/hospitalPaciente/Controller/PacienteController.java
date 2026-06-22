package pacientes.hospitalPaciente.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Service.PacienteService;
import pacientes.hospitalPaciente.dto.PacienteDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pacientes")
@Tag(name = "Paciente", description = "Operaciones principales para la administración de fichas clínicas de pacientes")
public class PacienteController {

    @Autowired
    private PacienteService service;

    @GetMapping
    @Operation(summary = "Listar todos los pacientes", description = "Retorna el listado completo de pacientes asociados a sus respectivos datos de contacto")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de pacientes obtenido con éxito"),
        @ApiResponse(responseCode = "244", description = "No se encontraron pacientes en la base de datos")
    })
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = service.listarPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar paciente por ID", description = "Obtiene la ficha del paciente y su contacto mediante su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "404", description = "El ID de paciente especificado no existe")
    })
    public ResponseEntity<Paciente> buscarPorId(
        @Parameter(description = "ID del paciente a consultar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            Paciente paciente = service.buscarPorId(id);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar paciente por RUT", description = "Permite filtrar de forma única y directa la ficha de un paciente por su RUT")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El RUT ingresado no pertenece a ningún paciente registrado")
    })
    public ResponseEntity<Paciente> buscarPorRut(
        @Parameter(description = "RUT del paciente", example = "12345678-9", required = true)
        @PathVariable String rut) {
        try {
            Paciente paciente = service.buscarPorRut(rut);
            return ResponseEntity.ok(paciente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo paciente", description = "Registra un paciente en el sistema. Requiere adjuntar la estructura del contacto asociada")
    @ApiResponse(responseCode = "200", description = "Paciente guardado exitosamente")
    public ResponseEntity<Paciente> guardar(@RequestBody Paciente paciente) {
        Paciente nuevo = service.guardar(paciente);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un paciente", description = "Permite modificar los datos del paciente o su objeto de contacto anidado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Paciente actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "No se encontró el paciente con el ID suministrado")
    })
    public ResponseEntity<Paciente> actualizar(
            @Parameter(description = "ID del paciente a modificar", example = "1", required = true)
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
    @Operation(summary = "Eliminar ficha de paciente", description = "Remueve físicamente el registro del paciente del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "244", description = "Paciente eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "El paciente no existe")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del paciente a eliminar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener vista resumida del paciente (DTO)", description = "Retorna un objeto simplificado optimizado para el consumo entre microservicios, concatenando nombres y aplanando el objeto de contacto")
    @ApiResponse(responseCode = "200", description = "Estructura DTO generada exitosamente")
    public ResponseEntity<PacienteDTO> obtenerPacienteDTO(
        @Parameter(description = "ID del paciente para generar la vista DTO", example = "1", required = true)
        @PathVariable Integer id) {

        Paciente paciente = service.buscarPorId(id);

        PacienteDTO dto = new PacienteDTO(
                paciente.getId(),
                paciente.getNombre() + " " + paciente.getApellido(),
                paciente.getContacto().getNombre(),
                paciente.getContacto().getEmail(),
                paciente.getContacto().getTelefono()
        );

        return ResponseEntity.ok(dto);
    }
}