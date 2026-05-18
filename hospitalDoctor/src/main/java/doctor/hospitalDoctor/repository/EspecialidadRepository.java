package doctor.hospitalDoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import doctor.hospitalDoctor.model.Especialidad;


@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Integer> {

}