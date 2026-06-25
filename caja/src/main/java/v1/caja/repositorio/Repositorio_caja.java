/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package v1.caja.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import v1.caja.Modelo.ModeloCaja;
/**
 *
 * @author diego
 */

@Repository
public interface Repositorio_caja extends JpaRepository< ModeloCaja, Long > {

    // Método de búsqueda basado en el atributo en español
    List< ModeloCaja > findByIdCajero(Long idCajero);
}
