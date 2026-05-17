package cita.hospitalCita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cita.hospitalCita.model.Atencion;



@Repository
public interface AtencionRepository extends JpaRepository<Atencion, Integer> {

    //  Buscar atenciones por paciente (microservicios → usamos ID)
    List<Atencion> findByPacienteId(Integer pacienteId);

    //  Buscar atenciones por doctor
    List<Atencion> findByDoctorId(Integer doctorId);

    //  Buscar por tipo de atención (relación interna JPA)
    List<Atencion> findByTipoAtencionId(Integer tipoAtencionId);

}