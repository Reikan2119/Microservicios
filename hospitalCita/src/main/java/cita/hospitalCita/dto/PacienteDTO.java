package cita.hospitalCita.dto;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que representa la información espejo del paciente dentro del módulo de citas")
public class PacienteDTO {

    @Schema(description = "Identificador único correlativo del paciente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre completo del paciente", example = "Carlos Marcelo Fuentes")
    private String nombrePaciente;

    @Schema(description = "Nombre de la persona o familiar directo de contacto de emergencia", example = "María Loreto Fuentes")
    private String nombreContacto;

    @Schema(description = "Dirección de correo electrónico principal del paciente", example = "carlos.fuentes@email.com")
    private String email;

    @Schema(description = "Número telefónico de contacto para avisos o confirmación de la cita", example = "+56912345678")
    private String telefono;
}