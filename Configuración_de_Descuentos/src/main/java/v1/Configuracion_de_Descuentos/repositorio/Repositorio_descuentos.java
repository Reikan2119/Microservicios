/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package v1.Configuracion_de_Descuentos.repositorio;
import  java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
/**
 *
 * @author diego
 */
public interface Repositorio_descuentos extends JpaRepository<Modelo_configuracion_de_descuento, Long> {
    
    //Busca la configuración mediante su código de autorización en caja.
    Optional<Modelo_configuracion_de_descuento> findByCodigoAutorizacion(String codigoAutorizacion);

    //Devuelve todas las promociones que estén vigentes o deshabilitadas (según el parámetro)
    List<Modelo_configuracion_de_descuento> findByActivo(Boolean activo);

    //Valida si un código ya existe para prevenir duplicados.
    boolean existsByCodigoAutorizacion(String codigoAutorizacion);
}
