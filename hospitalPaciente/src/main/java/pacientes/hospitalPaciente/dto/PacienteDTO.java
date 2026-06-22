package pacientes.hospitalPaciente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que expone el resumen de contacto esencial de un paciente para su uso entre microservicios")
public class PacienteDTO {

    @Schema(description = "Identificador único correlativo del paciente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre completo del paciente registrado", example = "Carlos Marcelo Fuentes")
    private String nombrePaciente;

    @Schema(description = "Nombre de la persona o familiar directo de contacto de emergencia", example = "María Loreto Fuentes")
    private String nombreContacto;

    @Schema(description = "Dirección de correo electrónico principal del paciente", example = "carlos.fuentes@email.com")
    private String email;

    @Schema(description = "Número telefónico de red móvil o fija para llamadas directas", example = "+56912345678")
    private String telefono;
}