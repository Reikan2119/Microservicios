package com.hospital.ficha.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ficha")    
public class fichaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String examinacion;

    @Column(nullable = false)
    private String notas;

    @Column(nullable = false)
    private Boolean riesgo;
    
    @Column(name = "diagnostico", nullable = false)
    private String diagnostico;

}
