package com.hospital.recetas.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital.recetas.Service.recetaService;


@RestController
@RequestMapping("api/v1/recetas")
public class recetaController {

    @Autowired
    private recetaService recetaService;

    @PostMapping
    public ResponseEntity<String> createReceta() {
        return ResponseEntity.ok("Receta creada exitosamente");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getRecetaById() {
        return ResponseEntity.ok("Receta consultada:");
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> deleteReceta() {
        return ResponseEntity.ok("Receta eliminada exitosamente");
    }
}
