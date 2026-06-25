package v1.caja.Controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import v1.caja.Modelo.ModeloCaja;
import v1.caja.Servicio.Servicio_caja;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/finanzas")
@Tag(name = "Finanzas y Caja", description = "Operaciones para la gestión de transacciones financieras, pagos y auditoría de cajeros del hospital")
public class Controlador_caja {

    @Autowired
    private Servicio_caja cajaService;

    // GET: Listar movimientos
    @GetMapping
    @Operation(summary = "Listar movimientos de caja", description = "Retorna el flujo completo de transacciones financieras registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Historial financiero obtenido con éxito")
    public List<ModeloCaja> listar() {
        return cajaService.listar();
    }

    // GET: Buscar transacción por ID
    @GetMapping("/{id}")
    @Operation(summary = "Buscar transacción por ID", description = "Obtiene los detalles específicos de un movimiento de caja mediante su ID único")
    @ApiResponse(responseCode = "200", description = "Transacción localizada con éxito")
    public ModeloCaja buscarPorId(
        @Parameter(description = "ID único del registro de caja", example = "105", required = true)
        @PathVariable Long id) {
        return cajaService.buscarPorId(id);
    }

    // GET: Listar por cajero
    @GetMapping("/cajero/{idCajero}")
    @Operation(summary = "Buscar transacciones por ID de Cajero", description = "Permite auditar el flujo financiero filtrando todos los movimientos efectuados por un cajero específico")
    @ApiResponse(responseCode = "200", description = "Listado de movimientos por cajero generado con éxito")
    public List<ModeloCaja> buscarPorCajero(
        @Parameter(description = "ID del funcionario de caja a auditar", example = "12", required = true)
        @PathVariable Long idCajero) {
        return cajaService.buscarPorCajero(idCajero);
    }

    // POST: Registrar pago/ingreso
    @PostMapping
    @Operation(summary = "Registrar movimiento financiero", description = "Crea un nuevo asiento de pago o transacción en el módulo de finanzas")
    @ApiResponse(responseCode = "200", description = "Transacción guardada y procesada correctamente")
    public ModeloCaja guardar(
        @RequestBody ModeloCaja entrada) {
        return cajaService.guardar(entrada);
    }

    // DELETE: Eliminar movimiento
    @DeleteMapping("/{id}")
    @Operation(summary = "Anular/Eliminar transacción", description = "Remueve físicamente un registro financiero del sistema por su ID")
    @ApiResponse(responseCode = "200", description = "Registro financiero eliminado exitosamente")
    public void eliminar(
        @Parameter(description = "ID de la transacción que se desea eliminar", example = "105", required = true)
        @PathVariable Long id) {
        cajaService.eliminar(id);
    }
}