package  cita.hospitalCita.dto;
import java.util.Date;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AtencionDetalleDTO {

    private Integer id;

    private Date fechaAtencion;

    private String diagnostico;

    private PacienteDTO paciente;

    private DoctorDTO doctor;

    private TipoAtencionDTO tipoAtencion;
}