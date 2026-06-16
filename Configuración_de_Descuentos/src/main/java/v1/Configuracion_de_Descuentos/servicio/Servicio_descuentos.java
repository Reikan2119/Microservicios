/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.Configuracion_de_Descuentos.servicio;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import v1.Configuracion_de_Descuentos.repositorio.Repositorio_descuentos;

/**
 *
 * @author diego
 */

@Service
public class Servicio_descuentos {
    @Autowired
    private Repositorio_descuentos repositorio;

    public List<Modelo_configuracion_de_descuento> listar() {
        return repositorio.findAll();
    }

    public Modelo_configuracion_de_descuento buscarPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración de descuento no encontrada"));
    }

    public Modelo_configuracion_de_descuento buscarPorCodigoAutorizacion(String codigo) {
        return repositorio.findByCodigoAutorizacion(codigo)
                .orElseThrow(() -> new RuntimeException("Código de descuento inválido o no existente"));
    }

    public List<Modelo_configuracion_de_descuento> listarActivos() {
        return repositorio.findByActivo(true);
    }

    public Modelo_configuracion_de_descuento guardar(Modelo_configuracion_de_descuento des) {
        // Validación: Evitar duplicar códigos de autorización estacionales
        if (des.getId() == null && repositorio.existsByCodigoAutorizacion(des.getCodigoAutorizacion())) {
            throw new RuntimeException("El código de autorización ya está registrado para otra campaña");
        }
        return repositorio.save(des);
    }

    /**
     * Lógica de Negocio: Cambia el estado de una promoción (Activar/Desactivar)
     */
    public Modelo_configuracion_de_descuento cambiarEstadoPromocion(Long id, Boolean estado) {
        Modelo_configuracion_de_descuento des = buscarPorId(id);
        des.setActivo(estado);
        return repositorio.save(des);
    }

    public void eliminar(Long id) {
        if (!repositorio.existsById(id)) {
            throw new RuntimeException("La configuración de descuento no existe");
        }
        repositorio.deleteById(id);
    }
}
