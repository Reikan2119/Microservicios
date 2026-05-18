package com.hospital.ficha.Model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
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
    
    @Column(nullable = false)
    private List<String> diagnosticos = new ArrayList<>();
}
