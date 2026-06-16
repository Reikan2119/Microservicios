package pacientes.hospitalPaciente.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import pacientes.hospitalPaciente.Model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByRut(String rut);

}
