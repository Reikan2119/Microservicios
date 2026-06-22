package doctor.hospitalDoctor.model;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "especialidad")
@Schema(description = "Entidad de persistencia que representa las distintas ramas o especialidades médicas disponibles en el hospital")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la especialidad médica", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre oficial de la especialidad clínica", example = "Cardiología", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;
}