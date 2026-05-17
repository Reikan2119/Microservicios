/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package java.inventario.service;
import java.inventario.modelo.inventario_item;
import java.inventario.repositorio.inventario_repo;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author diego
 */
public class inventario_service {
@Autowired
    private inventario_repo inventario_repo;

    public List<inventario_item> listar() {
        return inventario_repo.findAll();
    }

    public inventario_item buscarPorId(Long id) {
        return inventario_repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
    }

    public inventario_item buscarPorSku(String sku) {
        return inventario_repo.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado con el SKU especificado"));
    }

    public inventario_item guardar(inventario_item item) {
        // Validación de negocio: Evita que se dupliquen SKUs idénticos en la base de datos
        if (item.getId() == null && inventario_repo.existsBySku(item.getSku())) {
            throw new RuntimeException("El SKU ya se encuentra registrado");
        }
        return inventario_repo.save(item);
    }

    public inventario_item actualizarStock(String sku, Integer cantidadAModificar) {
        inventario_item item = buscarPorSku(sku);
        
        int nuevoStock = item.getStockActual() + cantidadAModificar;
        
        // Regla esencial: Impide que el stock quede en negativo en la clínica
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para realizar la operación");
        }
        
        item.setStockActual(nuevoStock);
        return inventario_repo.save(item);
    }

    public List<inventario_item> verificarAlertasStock(Integer limite) {
        return inventario_repo.findByStockActualLessThanEqual(limite);
    }

    public List<inventario_item> verificarInsumosVencidos() {
        return inventario_repo.findByFechaVencimientoLoteBefore(LocalDate.now());
    }

    public void eliminar(Long id) {
        if (!inventario_repo.existsById(id)) {
            throw new RuntimeException("Insumo no existe");
        }
        inventario_repo.deleteById(id);
    }
}

