package doctor.hospitalDoctor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que expone una vista simplificada y resumida de un médico para integraciones rápidas")
public class DoctorDTO {

    @Schema(description = "Identificador único correlativo del médico", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre y apellido concatenado del doctor", example = "Alejandro Silva")
    private String nombreDoctor;

    @Schema(description = "Nombre de la especialidad clínica a la que pertenece", example = "Pediatría")
    private String especialidad;
}