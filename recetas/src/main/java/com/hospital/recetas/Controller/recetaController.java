package com.hospital.recetas.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Service.recetaService;
import com.hospital.recetas.dto.recetaDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/recetas")
// Agrupa todos los endpoints de este controlador bajo la sección "Recetas" en el panel visual
@Tag(name = "Recetas", description = "Operaciones sobre las recetas médicas de los pacientes")
public class recetaController {

    @Autowired
    private recetaService recetaService;

    // POST: Crear receta médica
    @PostMapping
    @Operation(summary = "Crear una nueva receta", description = "Registra una receta médica en el sistema y retorna el objeto creado con su ID correspondiente")
    public ResponseEntity<recetaModel> guardar(@RequestBody recetaModel receta) {
        recetaModel nueva = recetaService.guardar(receta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // GET: Listar todas las recetas
    @GetMapping
    @Operation(summary = "Listar todas las recetas", description = "Retorna un listado completo con todas las recetas registradas en la base de datos")
    public ResponseEntity<List<recetaModel>> listar() {
        List<recetaModel> lista = recetaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    // GET: Buscar receta por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar receta por ID", description = "Retorna una única receta médica según el ID entero proporcionado")
    public ResponseEntity<recetaModel> getRecetaById(@PathVariable Integer id) {
        try {
            recetaModel receta = recetaService.getRecetaById(id);
            return ResponseEntity.ok(receta);
        } catch(RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Eliminar receta médica
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar receta por ID", description = "Elimina físicamente el registro de la receta de la base de datos usando su ID")
    public ResponseEntity<String> deleteReceta(@PathVariable Integer id) {
        try {
            recetaService.deleteReceta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT: Actualizar receta existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar receta por ID", description = "Modifica los campos internos de una receta existente localizándola mediante su ID de registro")
    public ResponseEntity<recetaModel> actualizar(@PathVariable Integer id, @RequestBody recetaModel recetaModel) {
        try {
            recetaModel actualizado = recetaService.actualizar(id, recetaModel);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

      //  Aquí realizas la transformación Entidad → DTO
    @GetMapping("/dto/{id}")
    @Operation(summary = "Retorna una Receta DTO", description = "Retorna un objeto recetaDto plano según el ID. Se utiliza para ser llamado de forma externa por otros microservicios, evitando exponer claves o IDs innecesarios")
    public ResponseEntity<recetaDto> recetaDTO(@PathVariable Integer id) {

        recetaModel receta = recetaService.getRecetaById(id);

        // Mapeo plano manual (Solo pasamos código, medicamentos y vencimiento)
        recetaDto dto = new recetaDto(
                receta.getCodigo(),
                receta.getMedicamentos(),
                receta.getFechaVencimiento()
        );

        return ResponseEntity.ok(dto);
    }
}