package doctor.hospitalDoctor.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private String rut;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;
}