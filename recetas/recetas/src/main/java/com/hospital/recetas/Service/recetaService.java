package com.hospital.recetas.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Repository.recetaRepository;

@Service
public class recetaService {
    
    @Autowired
    private recetaRepository recetaRepository;

    public List<recetaModel> listar(){
        return recetaRepository.findAll();
    }

    public void createReceta(recetaModel receta) {
        recetaRepository.save(receta);
    }

    public recetaModel guardar(recetaModel receta) {
        return recetaRepository.save(receta);
    }

    public recetaModel getRecetaById(Integer id) {
        return recetaRepository.findById(id).orElse(null);
    }

    public void deleteReceta(Integer id) {
        recetaRepository.deleteById(id);
    }

    public recetaModel actualizar(Integer id, recetaModel recetaActualizada) {
    recetaModel recetaExistente = recetaRepository.findById(id).orElseThrow(() -> new RuntimeException("Receta no encontrada"));

    recetaExistente.setCodigo(recetaActualizada.getCodigo());
    recetaExistente.setMedicamentos(recetaActualizada.getMedicamentos());
    recetaExistente.setFechaVencimiento(recetaActualizada.getFechaVencimiento());

    return recetaRepository.save(recetaExistente);
}


}

