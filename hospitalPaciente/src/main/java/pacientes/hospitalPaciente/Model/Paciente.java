package pacientes.hospitalPaciente.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paciente")
@Schema(description = "Entidad principal que representa a un paciente dentro del sistema clínico del hospital")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo del paciente asignado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "RUT (Rol Único Tributario) identificador único del paciente", example = "12345678-9", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Primer y segundo nombre del paciente", example = "Carlos Marcelo", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Apellidos paterno y materno del paciente", example = "Fuentes Poblete", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @Column(nullable = false)
    @Schema(description = "Edad cronológica actual del paciente", example = "34", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer edad;

    @ManyToOne
    @JoinColumn(name = "contacto_id", nullable = false)
    // 💡 Swagger automáticamente interpretará que aquí va todo el objeto Contacto estructurado en el JSON
    @Schema(description = "Información detallada de contacto asignada de manera obligatoria al paciente", requiredMode = Schema.RequiredMode.REQUIRED)
    private Contacto contacto;
}