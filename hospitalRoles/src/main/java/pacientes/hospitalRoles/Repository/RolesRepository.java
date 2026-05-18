package pacientes.hospitalRoles.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pacientes.hospitalRoles.Model.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {

    Optional<Roles> findByNombreRoles(String nombreRoles);
}
