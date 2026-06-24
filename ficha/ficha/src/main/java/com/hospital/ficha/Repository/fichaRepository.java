package com.hospital.ficha.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.ficha.Model.fichaModel;

public interface fichaRepository extends JpaRepository<fichaModel, Integer>{

}
