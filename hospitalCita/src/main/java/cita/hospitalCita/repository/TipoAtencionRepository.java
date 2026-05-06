package cita.hospitalCita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cita.hospitalCita.model.TipoAtencion;



@Repository
public interface TipoAtencionRepository extends JpaRepository<TipoAtencion, Integer> {

}