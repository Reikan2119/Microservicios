package cita.hospitalCita.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

// 🔥 Importación de Swagger para documentar esquemas de entidades
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atencion")
@Schema(description = "Entidad de persistencia principal que registra el evento de una atención médica en el hospital")
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo de la atención generado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha y hora exacta en la que se efectuó la atención", example = "2026-06-22T15:30:00.000Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private Date fechaAtencion;

    @Column(nullable = false)
    @Schema(description = "Descripción clínica o diagnóstico emitido por el profesional de salud", example = "Rinitis alérgica estacional", requiredMode = Schema.RequiredMode.REQUIRED)
    private String diagnostico;

    // 🔗 Puente a Microservicio → SOLO IDs
    @Column(name = "paciente_id", nullable = false)
    @Schema(description = "ID de referencia del paciente (Ficha lógica que conecta con el microservicio hospitalPaciente)", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer pacienteId;

    @Column(name = "doctor_id", nullable = false)
    @Schema(description = "ID de referencia del médico (Ficha lógica que conecta con el microservicio hospitalDoctor)", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer doctorId;

    // 🏛️ Relación interna del mismo microservicio
    @ManyToOne
    @JoinColumn(name = "tipo_atencion_id", nullable = false)
    @Schema(description = "Estructura interna del tipo o modalidad de atención (Telemedicina, Consulta, Urgencia)", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoAtencion tipoAtencion;
}