package  cita.hospitalCita.dto;
import lombok.*;

// 🔥 DTO PARA DOCTOR
// Representa los datos que recibimos desde el microservicio de doctores
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    private Integer id;
    private String nombre;
    private String apellido;
    private String especialidad;
}