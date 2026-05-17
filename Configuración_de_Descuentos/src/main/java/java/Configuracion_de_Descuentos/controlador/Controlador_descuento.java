/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package java.Configuracion_de_Descuentos.controlador;

import java.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import java.Configuracion_de_Descuentos.servicio.Servicio_descuentos;
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
/**
 *
 * @author diego
 */
@RestController
@RequestMapping("/api/v1/descuentos")
public class Controlador_descuento {

    @Autowired
    private Servicio_descuentos servicio_descuentos;

    @GetMapping
    public List<Modelo_configuracion_de_descuento> listar() {
        return servicio_descuentos.listar();
    }

    @GetMapping("/{id}")
    public Modelo_configuracion_de_descuento buscarPorId(@PathVariable Long id) {
        return servicio_descuentos.buscarPorId(id);
    }

    @GetMapping("/validar/{codigo}")
    public Modelo_configuracion_de_descuento buscarPorCodigoAutorizacion(@PathVariable String codigo) {
        Modelo_configuracion_de_descuento descuento = servicio_descuentos.buscarPorCodigoAutorizacion(codigo);
        
        if (!descuento.getActivo()) {
            throw new RuntimeException("La campaña promocional asociada a este código se encuentra inactiva");
        }
        return descuento;
    }

    @GetMapping("/activos")
    public List<Modelo_configuracion_de_descuento> listarActivos() {
        return servicio_descuentos.listarActivos();
    }

    @PostMapping
    public Modelo_configuracion_de_descuento guardar(@RequestBody Modelo_configuracion_de_descuento des) {
        return servicio_descuentos.guardar(des);
    }

    @PutMapping("/{id}/estado")
    public Modelo_configuracion_de_descuento cambiarEstadoPromocion(
            @PathVariable Long id, 
            @RequestParam Boolean activo) {
        return servicio_descuentos.cambiarEstadoPromocion(id, activo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        servicio_descuentos.eliminar(id);
    }
}
