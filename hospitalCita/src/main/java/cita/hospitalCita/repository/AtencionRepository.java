package cita.hospitalCita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cita.hospitalCita.model.Atencion;



@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    List<Atencion> findByPacienteId(Integer pacienteId);

    List<Atencion> findByDoctorId(Integer doctorId);

    List<Atencion> findByTipoAtencionId(Integer tipoAtencionId);

}