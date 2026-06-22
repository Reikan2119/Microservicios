package doctor.hospitalDoctor.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.service.EspecialidadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/especialidades")
// Agrupa los endpoints bajo una pestaña específica en Swagger UI
@Tag(name = "Especialidad Médica", description = "Operaciones para administrar las distintas especialidades del personal médico (ej: Cardiología, Pediatría)")
public class EspecialidadController {

    @Autowired
    private EspecialidadService service;

    // GET: Listar todas las especialidades
    @GetMapping
    @Operation(summary = "Listar todas las especialidades", description = "Retorna una lista con todas las ramas de especialización médica configuradas en el hospital")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de especialidades obtenida con éxito"),
        @ApiResponse(responseCode = "204", description = "No existen especialidades registradas en el sistema")
    })
    public ResponseEntity<List<Especialidad>> listar() {
        List<Especialidad> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    // GET: Buscar especialidad por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar especialidad por ID", description = "Busca y retorna los datos de una especialidad médica mediante su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Especialidad localizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "El ID ingresado no corresponde a ninguna especialidad registrada")
    })
    public ResponseEntity<Especialidad> buscar(
        @Parameter(description = "ID de la especialidad a consultar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(service.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Registrar una nueva especialidad
    @PostMapping
    @Operation(summary = "Registrar nueva especialidad", description = "Crea una nueva especialidad médica (ej: Neurología) mediante un cuerpo JSON")
    @ApiResponse(responseCode = "200", description = "Especialidad guardada correctamente")
    public ResponseEntity<Especialidad> guardar(@RequestBody Especialidad especialidad) {
        return ResponseEntity.ok(service.guardar(especialidad));
    }

    // PUT: Actualizar una especialidad existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una especialidad", description = "Modifica el nombre u otros datos de una especialidad localizándola por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Especialidad actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque la especialidad no existe")
    })
    public ResponseEntity<Especialidad> actualizar(
        @Parameter(description = "ID de la especialidad que se desea modificar", example = "1", required = true)
        @PathVariable Integer id, 
        @RequestBody Especialidad especialidad) {
        try {
            Especialidad actualizada = service.actualizar(id, especialidad);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar una especialidad
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una especialidad", description = "Remueve físicamente el registro de una especialidad clínica utilizando su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Especialidad eliminada de manera exitosa"),
        @ApiResponse(responseCode = "404", description = "La especialidad no se pudo eliminar porque el ID no fue encontrado")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID de la especialidad que se eliminará", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}