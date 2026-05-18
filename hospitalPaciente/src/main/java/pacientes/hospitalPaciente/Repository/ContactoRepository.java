package pacientes.hospitalPaciente.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pacientes.hospitalPaciente.Model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Integer>{


    
}
