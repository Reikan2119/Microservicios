package pacientes.hospitalPaciente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PacienteDTO {

    private Integer id;
    private String nombrePaciente;
    private String nombreContacto;
    private String email;
    private String telefono;
}