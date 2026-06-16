/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
/**
 *
 * @author diego
 */
@RestController
@RequestMapping("/api/v1/inventario")
public class inventario_controlador {
    @Autowired
    private inventario_service inventario_service;

    @GetMapping
    public List<inventario_item> listar() {
        return inventario_service.listar();
    }

    @GetMapping("/{id}")
    public inventario_item buscarPorId(@PathVariable Long id) {
        return inventario_service.buscarPorId(id);
    }

    @GetMapping("/sku/{sku}")
    public inventario_item buscarPorSku(@PathVariable String sku) {
        return inventario_service.buscarPorSku(sku);
    }

    @PostMapping
    public inventario_item guardar(@RequestBody inventario_item item) {
        return inventario_service.guardar(item);
    }

    /**
     * Endpoint para modificar stock (Entradas/Salidas).
     * Ejemplo: PUT /api/inventario/sku/BISTURI-01/stock?cantidad=-5
     */
    @PutMapping("/sku/{sku}/stock")
    public inventario_item actualizarStock(
            @PathVariable String sku, 
            @RequestParam Integer cantidad) {
        return inventario_service.actualizarStock(sku, cantidad);
    }

    @GetMapping("/alertas")
    public List<inventario_item> verificarAlertasStock(@RequestParam Integer limite) {
        return inventario_service.verificarAlertasStock(limite);
    }

    @GetMapping("/vencidos")
    public List<inventario_item> verificarInsumosVencidos() {
        return inventario_service.verificarInsumosVencidos();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        inventario_service.eliminar(id);
    }

}
