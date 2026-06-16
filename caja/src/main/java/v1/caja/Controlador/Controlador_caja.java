/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.caja.Controlador;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import v1.caja.Modelo.ModeloCaja;
import v1.caja.Servicio.Servicio_caja;
/**
 *
 * @author diego
 */

@RestController
@RequestMapping("/api/v1/finanzas")
public class Controlador_caja {
@Autowired
    private Servicio_caja cajaService;

    @GetMapping
    public List<ModeloCaja> listar() {
        return cajaService.listar();
    }

    @GetMapping("/{id}")
    public ModeloCaja buscarPorId(@PathVariable Long id) {
        return cajaService.buscarPorId(id);
    }

    @GetMapping("/cajero/{idCajero}")
    public List<ModeloCaja> buscarPorCajero(@PathVariable Long idCajero) {
        return cajaService.buscarPorCajero(idCajero);
    }

    @PostMapping
    public ModeloCaja guardar(@RequestBody ModeloCaja entrada) {
        return cajaService.guardar(entrada);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        cajaService.eliminar(id);
    }
}
