package com.hospital.ficha.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital.ficha.Service.fichaService;

@RestController
@RequestMapping("/api/v1/fichaController")
public class fichaController {

    @Autowired
    private fichaService fichaService;

    @GetMapping
    public ResponseEntity<java.util.List<com.hospital.ficha.Model.fichaModel>> getAllFichas() {
        if (fichaService.getAllFichas().isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(fichaService.getAllFichas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> getFichaById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(fichaService.getFichaById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> createFicha(@RequestBody com.hospital.ficha.Model.fichaModel ficha) {
        return ResponseEntity.ok(fichaService.createFicha(ficha));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<com.hospital.ficha.Model.fichaModel> updateFicha(@PathVariable Integer id, @RequestBody com.hospital.ficha.Model.fichaModel fichaDetails) {
        try {
            return ResponseEntity.ok(fichaService.updateFicha(id, fichaDetails));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    // delete
}
