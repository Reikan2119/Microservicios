package pacientes.hospitalPaciente.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Service.ContactoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/contactos")
@Tag(name = "Contacto Paciente", description = "Operaciones para gestionar la libreta de contactos de emergencia")
public class ContactoController {

    @Autowired
    private ContactoService service;

    @GetMapping
    @Operation(summary = "Listar todos los contactos", description = "Retorna una lista completa con la información de contacto registrada en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito"),
        @ApiResponse(responseCode = "244", description = "No hay registros disponibles")
    })
    public ResponseEntity<List<Contacto>> listar() {
        List<Contacto> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar contacto por ID", description = "Retorna un único contacto basándose en su ID numérico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contacto localizado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID de contacto no existe en el sistema")
    })
    public ResponseEntity<Contacto> buscarPorId(
        @Parameter(description = "ID del contacto a buscar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            Contacto contacto = service.buscarPorId(id);
            return ResponseEntity.ok(contacto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo contacto", description = "Crea un registro de contacto mediante un cuerpo JSON")
    @ApiResponse(responseCode = "200", description = "Contacto creado de manera exitosa")
    public ResponseEntity<Contacto> guardar(@RequestBody Contacto contacto) {
        Contacto nuevo = service.guardar(contacto);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar datos de un contacto", description = "Modifica los campos de un contacto existente localizándolo por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contacto actualizado con éxito"),
        @ApiResponse(responseCode = "404", description = "No se pudo actualizar porque el ID no existe")
    })
    public ResponseEntity<Contacto> actualizar(
        @Parameter(description = "ID del contacto a modificar", example = "1", required = true)
        @PathVariable Integer id, 
        @RequestBody Contacto contacto) {
        try {
            Contacto actualizado = service.actualizar(id, contacto);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un contacto", description = "Remueve permanentemente un contacto del sistema por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "244", description = "Contacto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "El contacto no pudo eliminarse debido a que el ID no existe")
    })
    public ResponseEntity<Void> eliminar(
        @Parameter(description = "ID del contacto que se desea eliminar", example = "1", required = true)
        @PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}