package doctor.hospitalDoctor.model;

import jakarta.persistence.*;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
@Schema(description = "Entidad de persistencia que representa a un médico especialista dentro del personal del hospital")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo del médico asignado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Primer y segundo nombre del médico", example = "Alejandro Andrés", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellidos paterno y materno del médico", example = "Silva Castro", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "RUT (Rol Único Tributario) identificador del médico", example = "11222333-K", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rut;

    @ManyToOne // (fetch = FetchType.EAGER)
    @JoinColumn(name = "especialidad_id", nullable = false)
    // 💡 Swagger interpretará de forma automática el objeto Especialidad completo dentro del JSON
    @Schema(description = "Especialidad médica asignada de forma obligatoria al doctor", requiredMode = Schema.RequiredMode.REQUIRED)
    private Especialidad especialidad;
}