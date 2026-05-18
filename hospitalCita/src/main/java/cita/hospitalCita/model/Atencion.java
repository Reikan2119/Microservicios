package  cita.hospitalCita.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "atencion")
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fechaAtencion;

    @Column(nullable = false)
    private String diagnostico;

    //  Microservicios → SOLO IDs
    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteId;

    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    //  Relación interna (SÍ permitido)
    @ManyToOne
    @JoinColumn(name = "tipo_atencion_id", nullable = false)
    private TipoAtencion tipoAtencion;
}