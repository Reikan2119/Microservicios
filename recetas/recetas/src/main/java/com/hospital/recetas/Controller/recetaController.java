package com.hospital.recetas.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Service.recetaService;
import com.hospital.recetas.dto.recetaDto;



@RestController
@RequestMapping("api/v1/recetas")
public class recetaController {

    @Autowired
    private recetaService recetaService;

    @PostMapping
    public ResponseEntity<recetaModel> guardar(@RequestBody recetaModel receta) {
    recetaModel nueva = recetaService.guardar(receta);
    return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
}

    @GetMapping
    public ResponseEntity<List<recetaModel>> listar() {
        List<recetaModel> lista = recetaService.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lista);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<recetaModel> getRecetaById(@PathVariable Integer id) {
        try{
            recetaModel receta = recetaService.getRecetaById(id);
            return ResponseEntity.ok(receta);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReceta(@PathVariable Integer id) {
        try{
            recetaService.deleteReceta(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<recetaModel> actualizar(@PathVariable Integer id, @RequestBody recetaModel recetaModel) {
        try {
            recetaModel actualizado = recetaService.actualizar(id, recetaModel);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<recetaDto> recetaDTO(@PathVariable Integer id) {

    recetaModel receta = recetaService.getRecetaById(id);

    recetaDto dto = new recetaDto(
            receta.getCodigo(),
            receta.getMedicamentos(),
            receta.getFechaVencimiento()
    );

    return ResponseEntity.ok(dto);
}

}
