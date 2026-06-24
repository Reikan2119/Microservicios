package com.hospital.recetas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.recetas.Model.recetaModel;
import java.util.Optional;


public interface recetaRepository extends JpaRepository<recetaModel, Integer> {
    
    Optional<recetaModel> findById(Integer id);
}
