package  cita.hospitalCita.dto;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String nombreDoctor;
    private String especialidad;
}