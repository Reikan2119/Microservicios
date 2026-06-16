package com.hospital.notificaciones.Repossitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.notificaciones.Model.notificacionModel;


public interface notificacionRepository extends JpaRepository<notificacionModel, Integer> {

}
