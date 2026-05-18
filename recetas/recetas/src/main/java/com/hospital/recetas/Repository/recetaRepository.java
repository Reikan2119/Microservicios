package com.hospital.recetas.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.recetas.Model.recetaModel;

@Repository
public interface recetaRepository extends JpaRepository<recetaModel, String> {

}
