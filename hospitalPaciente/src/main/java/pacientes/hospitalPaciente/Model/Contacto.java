package pacientes.hospitalPaciente.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacto")
@Schema(description = "Entidad que representa los datos de contacto directo o de emergencia de un paciente")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo del registro de contacto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = true)
    // Al ser nullable = true, indicas en Swagger que es opcional
    @Schema(description = "Nombre completo de la persona de contacto (Opcional)", example = "María Loreto Fuentes")
    private String nombre;
    
    @Column(nullable = false)
    @Schema(description = "Número telefónico de contacto", example = "+56912345678", requiredMode = Schema.RequiredMode.REQUIRED)
    private String telefono;

    @Column(nullable = false)
    @Schema(description = "Dirección de correo electrónico", example = "maria.fuentes@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}