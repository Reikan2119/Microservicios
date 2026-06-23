package v1.inventario.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import v1.inventario.modelo.inventario_item;
import v1.inventario.service.inventario_service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/inventario")
// Agrupa todos los endpoints bajo la pestaña "Inventario" en la página de Swagger UI
@Tag(name = "Inventario", description = "Operaciones para la gestión de insumos médicos, stock y alertas en el hospital")
public class inventario_controlador {

    @Autowired
    private inventario_service inventario_service;

    // GET: Listar todo el inventario
    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna una lista completa con todos los artículos e insumos registrados en el almacén médico")
    public List<inventario_item> listar() {
        return inventario_service.listar();
    }

    // GET: Buscar por ID numérico
    @GetMapping("/{id}")
    @Operation(summary = "Buscar ítem por ID", description = "Busca y retorna un único insumo médico basándose en su ID numérico correlativo")
    public inventario_item buscarPorId(
        @Parameter(description = "ID del ítem en el inventario", example = "1", required = true)
        @PathVariable Long id) {
        return inventario_service.buscarPorId(id);
    }

    // GET: Buscar por código SKU
    @GetMapping("/sku/{sku}")
    @Operation(summary = "Buscar ítem por SKU", description = "Busca y retorna un único insumo médico a través de su código único SKU")
    public inventario_item buscarPorSku(
        @Parameter(description = "Código SKU del producto médico", example = "BISTURI-01", required = true)
        @PathVariable String sku) {
        return inventario_service.buscarPorSku(sku);
    }

    // POST: Guardar nuevo ítem
    @PostMapping
    @Operation(summary = "Registrar nuevo ítem", description = "Crea un nuevo artículo en el inventario a través de un cuerpo JSON y retorna el objeto guardado")
    public inventario_item guardar(@RequestBody inventario_item item) {
        return inventario_service.guardar(item);
    }

    // PUT: Modificar stock (Entradas/Salidas)
    @PutMapping("/sku/{sku}/stock")
    @Operation(
        summary = "Modificar stock (Entradas/Salidas)", 
        description = "Actualiza las existencias sumando o restando unidades al stock actual mediante el uso de la cantidad suministrada por parámetro"
    )
    public inventario_item actualizarStock(
            @Parameter(description = "Código SKU del artículo a modificar", example = "BISTURI-01", required = true)
            @PathVariable String sku, 
            
            @Parameter(description = "Cantidad a modificar (Valores positivos suman stock, negativos simulan salidas o mermas)", example = "-5", required = true)
            @RequestParam Integer cantidad) {
        return java.util.Objects.nonNull(sku) ? inventario_service.actualizarStock(sku, cantidad) : null;
    }

    // GET: Verificar alertas de stock mínimo
    @GetMapping("/alertas")
    @Operation(summary = "Verificar alertas de stock bajo", description = "Retorna una lista con los insumos cuya cantidad actual sea menor o igual al límite numérico especificado")
    public List<inventario_item> verificarAlertasStock(
        @Parameter(description = "Límite numérico o umbral de stock crítico para activar la alerta", example = "10", required = true)
        @RequestParam Integer limite) {
        return inventario_service.verificarAlertasStock(limite);
    }

    // GET: Verificar insumos vencidos
    @GetMapping("/vencidos")
    @Operation(summary = "Listar insumos vencidos", description = "Escanea la base de datos y retorna todos aquellos insumos cuya fecha de vencimiento sea anterior al día de hoy")
    public List<inventario_item> verificarInsumosVencidos() {
        return inventario_service.verificarInsumosVencidos();
    }

    // DELETE: Eliminar un ítem
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ítem por ID", description = "Borra físicamente un artículo del inventario utilizando su ID numérico")
    public void eliminar(
        @Parameter(description = "ID del ítem que se desea dar de baja", example = "5", required = true)
        @PathVariable Long id) {
        inventario_service.eliminar(id);
    }
}