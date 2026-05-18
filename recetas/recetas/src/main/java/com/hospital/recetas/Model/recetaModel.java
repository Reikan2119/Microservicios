package com.hospital.recetas.Model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recetas")
public class recetaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String codigo;

    @Column(nullable = false)
    private List<String> medicamentos;

    @Column(nullable = false)
    private String fechaVencimiento;

//String prescriptionCode: Folio único para validación legal.
//List<> medications: Fármacos tópicos (antimicóticos), cremas hidratantes o apósitos recomendados.
//LocalDate expiryDate: Vencimiento de la receta para control de seguimiento.//


}
