package cita.hospitalCita.dto;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que define la modalidad o categoría bajo la cual se realiza la atención médica")
public class TipoAtencionDTO {

    @Schema(description = "Identificador único de la categoría de atención", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "Nombre descriptivo del tipo o modalidad de atención", example = "Consulta Médica General")
    private String nombre;
}