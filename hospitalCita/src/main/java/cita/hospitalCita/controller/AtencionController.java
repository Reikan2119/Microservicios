package cita.hospitalCita.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cita.hospitalCita.dto.AtencionDetalleDTO;
import cita.hospitalCita.model.Atencion;
import cita.hospitalCita.service.AtencionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/atenciones")
// Agrupa los endpoints en la interfaz de Swagger UI
@Tag(name = "Atenciones Clínicas", description = "Operaciones para el registro, evolución médica y consulta de atenciones del hospital")
public class AtencionController {

    @Autowired
    private AtencionService service;

    // GET: Listar todas las atenciones
    @GetMapping
    @Operation(summary = "Listar todas las atenciones", description = "Retorna el historial completo de atenciones médicas registradas en el hospital")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Listado de atenciones obtenido con éxito"),
        @ApiResponse(responseCode = "204", description = "No existen registros de atenciones clínicas")
    })
    public ResponseEntity<List<Atencion>> listar() {
        List<Atencion> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    // GET: Buscar atención por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar atención por ID", description = "Busca y retorna los datos generales de una atención específica mediante su ID único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Atención localizada con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID de la atención solicitado no existe")
    })
    public ResponseEntity<Atencion> buscar(
        @Parameter(description = "ID único de la atención clínica", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            Atencion atencion = service.buscarPorId(id);
            return ResponseEntity.ok(atencion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Obtener el DTO de detalle completo de la atención
    @GetMapping("/{id}/detalle")
    @Operation(
        summary = "Obtener detalle completo de la atención (DTO)", 
        description = "Consulta y unifica la información de la atención médica integrando dinámicamente los datos externos de otros microservicios"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Detalle DTO de la atención estructurado y devuelto correctamente"),
        @ApiResponse(responseCode = "404", description = "No se pudo generar el detalle porque el ID de atención no existe")
    })
    public ResponseEntity<AtencionDetalleDTO> detalle(
        @Parameter(description = "ID de la atención para compilar la ficha de detalle", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            AtencionDetalleDTO dto = service.obtenerDetalle(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) { 
            return ResponseEntity.notFound().build(); 
        }
    }

    // POST: Guardar una nueva atención
    @PostMapping
    @Operation(summary = "Registrar nueva atención médica", description = "Crea una nueva hoja de atención en el box médico a través de un cuerpo JSON")
    @ApiResponse(responseCode = "200", description = "Atención registrada de manera exitosa")
    public ResponseEntity<Atencion> guardar(@RequestBody Atencion atencion) {
        Atencion nueva = service.guardar(atencion);
        return ResponseEntity.ok(nueva);
    }
}