package cita.hospitalCita.dto;

import java.util.Date;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO compuesto que consolida la información completa de una atención clínica, integrando datos de pacientes, doctores y tipos de atención")
public class AtencionDetalleDTO {

    @Schema(description = "Identificador único correlativo de la atención", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Fecha y hora exacta en la que se llevó a cabo la consulta médica", example = "2026-06-22T15:30:00.000Z")
    private Date fechaAtencion;

    @Schema(description = "Juicio clínico o diagnóstico emitido por el médico durante la sesión", example = "Hipertensión arterial primaria bajo control")
    private String diagnostico;

    // 💡 Swagger detectará automáticamente las propiedades internas de PacienteDTO
    @Schema(description = "Información resumida de contacto del paciente atendido")
    private PacienteDTO paciente;

    // 💡 Swagger anidará la estructura interna de DoctorDTO
    @Schema(description = "Datos de identificación y especialidad del médico tratante")
    private DoctorDTO doctor;

    // 💡 Swagger mapeará el DTO correspondiente a la modalidad de atención
    @Schema(description = "Detalle del tipo o modalidad de atención médica recibida")
    private TipoAtencionDTO tipoAtencion;
}