package com.hospital.recetas.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Repository.recetaRepository;

@Service
public class recetaService {
    
    @Autowired
    private recetaRepository recetaRepository;

    public void createReceta(recetaModel receta) {
        recetaRepository.save(receta);
    }

    public recetaModel getRecetaById(String id) {
        return recetaRepository.findById(id).orElse(null);
  }
    public void deleteReceta(String id) {
        recetaRepository.deleteById(id);
        
    }


}

