package com.hospital.ficha.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hospital.ficha.Service.fichaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ficha")
@Tag(name = "Ficha Clínica", description = "Operaciones para la gestión e historial de fichas clínicas de los pacientes")
public class fichaController {

    @Autowired
    private fichaService fichaService;

    // GET: Obtener todas las fichas
    @GetMapping
    @Operation(summary = "Listar todas las fichas clínicas", description = "Retorna el listado global de las fichas clínicas de pacientes registradas en el hospital")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de fichas obtenida con éxito"),
        @ApiResponse(responseCode = "204", description = "No existen fichas clínicas en el sistema")
    })
    public ResponseEntity<java.util.List<com.hospital.ficha.Model.fichaModel>> getAllFichas() {
        if (fichaService.getAllFichas().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fichaService.getAllFichas());
    }

    // GET: Buscar ficha por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar ficha clínica por ID", description = "Recupera la información y antecedentes médicos de una ficha específica utilizando su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha clínica localizada con éxito"),
        @ApiResponse(responseCode = "404", description = "La ficha solicitada no existe")
    })
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> getFichaById(
        @Parameter(description = "ID único de la ficha clínica", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            return ResponseEntity.ok(fichaService.getFichaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // POST: Crear una nueva ficha
    @PostMapping
    @Operation(summary = "Crear una nueva ficha clínica", description = "Inicializa el expediente o historial clínico de un paciente mediante un cuerpo JSON")
    @ApiResponse(responseCode = "200", description = "Ficha clínica creada de manera exitosa")
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> createFicha(
        @RequestBody com.hospital.ficha.Model.fichaModel ficha) {
        return ResponseEntity.ok(fichaService.createFicha(ficha));
    }
    
    // PUT: Actualizar una ficha existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ficha clínica", description = "Modifica las anotaciones o antecedentes de una ficha existente localizándola por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ficha clínica actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el ID de la ficha no existe")
    })
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> updateFicha(
        @Parameter(description = "ID de la ficha clínica que se desea modificar", example = "1", required = true)
        @PathVariable Integer id, 
        @RequestBody com.hospital.ficha.Model.fichaModel fichaDetails) {
        try {
            return ResponseEntity.ok(fichaService.updateFicha(id, fichaDetails));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}