/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package java.inventario.repositorio;
import java.inventario.modelo.inventario_item;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author diego
 */

public interface inventario_repo extends JpaRepository<inventario_item, Long>{

    /**
     * Busca un ítem de inventario por su código SKU único.
     */
    Optional<inventario_item> findBySku(String sku);

    /**
     * Busca todos los registros cuyo stock actual sea menor o igual al límite indicado.
     */
    List<inventario_item> findByStockActualLessThanEqual(Integer limiteStock);

    /**
     * Busca insumos con fecha de vencimiento anterior a la fecha dada.
     */
    List<inventario_item> findByFechaVencimientoLoteBefore(LocalDate fecha);
    
    /**
     * Valida la existencia de un SKU antes de permitir la inserción de un nuevo registro.
     */
    boolean existsBySku(String sku);
}

