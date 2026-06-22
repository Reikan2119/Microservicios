package cita.hospitalCita.dto;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que representa la información espejo del médico dentro del módulo de citas")
public class DoctorDTO {

    @Schema(description = "Identificador único correlativo del médico", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre y apellido completo del doctor asignado", example = "Alejandro Silva")
    private String nombreDoctor;

    @Schema(description = "Nombre de la especialidad clínica que atiende la cita", example = "Pediatría")
    private String especialidad;
}