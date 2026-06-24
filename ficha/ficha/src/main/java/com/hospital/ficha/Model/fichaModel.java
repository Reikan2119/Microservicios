package com.hospital.ficha.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ficha")    
@Schema(description = "Entidad de persistencia que representa el expediente o ficha médica individual con las evaluaciones del paciente")
public class fichaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo de la ficha clínica", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Detalles de la examinación física o evaluación general realizada al paciente", example = "Paciente presenta presión arterial elevada y fatiga muscular.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String examinacion;

    @Column(nullable = false)
    @Schema(description = "Notas adicionales del profesional médico, observaciones o cuidados paliativos", example = "Se sugiere reposo absoluto por 48 horas y monitoreo de temperatura.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String notas;

    @Column(nullable = false)
    @Schema(description = "Indicador crítico que determina si el paciente se encuentra en una situación de alto riesgo clínico", example = "false", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean riesgo;
    
    @Column(name = "diagnostico", nullable = false)
    @Schema(description = "Juicio clínico final o enfermedad identificada anotada en la ficha", example = "Hipertensión Etapa 1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String diagnostico;
}