package cita.hospitalCita.model;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_atencion")
@Schema(description = "Entidad de persistencia que almacena los tipos o modalidades de atención interna del hospital")
public class TipoAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del tipo de atención", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre oficial de la modalidad (ej: Telemedicina, Control, Urgencia)", example = "Urgencia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;
}