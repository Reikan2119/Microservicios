package doctor.hospitalDoctor.dto;

import lombok.*;

@data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String especialidad; 
}