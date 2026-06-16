package  cita.hospitalCita.dto;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {

    private Integer id;
    private String nombrePaciente;
    private String nombreContacto;
    private String email;
    private String telefono;
}