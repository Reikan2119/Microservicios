package v1.Configuracion_de_Descuentos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import v1.Configuracion_de_Descuentos.servicio.Servicio_descuentos;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/descuentos")
@Tag(name = "Configuración de Descuentos", description = "Operaciones para gestionar, validar y activar los beneficios y rebajas arancelarias de la clínica")
public class Controlador_descuento {

    @Autowired
    private Servicio_descuentos servicio_descuentos;

    // GET: Listar todos
    @GetMapping
    @Operation(summary = "Listar todas las promociones", description = "Retorna el historial completo de descuentos configurados en el sistema, activos o inactivos")
    @ApiResponse(responseCode = "200", description = "Catálogo de descuentos obtenido con éxito")
    public List<Modelo_configuracion_de_descuento> listar() {
        return servicio_descuentos.listar();
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar descuento por ID", description = "Obtiene los detalles de un beneficio arancelario específico según su ID")
    @ApiResponse(responseCode = "200", description = "Descuento localizado con éxito")
    public Modelo_configuracion_de_descuento buscarPorId(
        @Parameter(description = "ID único del registro de descuento", example = "1", required = true)
        @PathVariable Long id) {
        return servicio_descuentos.buscarPorId(id);
    }

    // GET: Validar código
    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar código de autorización", description = "Busca una promoción por su código alfanumérico y valida que se encuentre activa en las cajas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "El código es válido y la campaña está activa"),
        @ApiResponse(responseCode = "500", description = "La campaña promocional asociada a este código se encuentra inactiva")
    })
    public Modelo_configuracion_de_descuento buscarPorCodigoAutorizacion(
        @Parameter(description = "Código interno de autorización del beneficio", example = "CONV-2026-AM", required = true)
        @PathVariable String codigo) {
        Modelo_configuracion_de_descuento descuento = servicio_descuentos.buscarPorCodigoAutorizacion(codigo);
        
        if (!descuento.getActivo()) {
            throw new RuntimeException("La campaña promocional asociada a este código se encuentra inactiva");
        }
        return descuento;
    }

    // GET: Listar solo activos
    @GetMapping("/activos")
    @Operation(summary = "Listar descuentos vigentes", description = "Retorna únicamente los beneficios arancelarios que están marcados como activos para su uso inmediato")
    @ApiResponse(responseCode = "200", description = "Listado de descuentos activos obtenido correctamente")
    public List<Modelo_configuracion_de_descuento> listarActivos() {
        return servicio_descuentos.listarActivos();
    }

    // POST: Guardar una nueva promoción
    @PostMapping
    @Operation(summary = "Crear nuevo beneficio arancelario", description = "Registra una nueva campaña de descuento o convenio en el sistema")
    @ApiResponse(responseCode = "200", description = "Promoción registrada exitosamente")
    public Modelo_configuracion_de_descuento guardar(
        @RequestBody Modelo_configuracion_de_descuento des) {
        return servicio_descuentos.guardar(des);
    }

    // PUT: Cambiar estado (Activo/Inactivo) mediante RequestParam
    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de la promoción", description = "Activa o desactiva rápidamente un beneficio en el sistema de cajas usando un parámetro de consulta")
    @ApiResponse(responseCode = "200", description = "Estado de la promoción actualizado con éxito")
    public Modelo_configuracion_de_descuento cambiarEstadoPromocion(
        @Parameter(description = "ID del descuento que se desea modificar", example = "1", required = true)
        @PathVariable Long id, 
        @Parameter(description = "Nuevo estado de la promoción (true = Activo, false = Inactivo)", example = "false", required = true)
        @RequestParam Boolean activo) {
        return servicio_descuentos.cambiarEstadoPromocion(id, activo);
    }

    // DELETE: Eliminar físicamente
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un descuento", description = "Remueve físicamente la configuración de un descuento de la base de datos por su ID")
    @ApiResponse(responseCode = "200", description = "Descuento eliminado con éxito")
    public void eliminar(
        @Parameter(description = "ID del descuento que se va a eliminar definitivamente", example = "3", required = true)
        @PathVariable Long id) {
        servicio_descuentos.eliminar(id);
    }
}