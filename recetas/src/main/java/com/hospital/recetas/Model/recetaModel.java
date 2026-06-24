package com.hospital.recetas.Model;

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
@Table(name = "recetas")
// Describe de forma global la entidad en la sección de esquemas de Swagger
@Schema(description = "Entidad que representa una receta médica dentro de la base de datos del hospital")
public class recetaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Documenta el ID primario. Al ser autoincremental, es bueno ilustrarlo con un ejemplo numérico
    @Schema(description = "Identificador único autoincrementable de la receta", example = "1", readOnly = true)
    private Integer id;
    
    @Column(nullable = false, unique = true)
    // Documenta el código único indicando un formato de ejemplo realista
    @Schema(description = "Código único alfa-numérico de autorización para el retiro de la receta", example = "RX-1001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigo;

    @Column(nullable = false)
    // Documenta el detalle de los fármacos
    @Schema(description = "Listado detallado de medicamentos recetados junto con sus respectivas dosis e indicaciones", example = "Amoxicilina 500mg cada 8 horas por 7 días / Paracetamol 1g en caso de dolor", requiredMode = Schema.RequiredMode.REQUIRED)
    private String medicamentos;

    @Column(nullable = false)
    // Documenta la fecha de vencimiento que Diego definió como String
    @Schema(description = "Fecha límite de vigencia de la receta para poder retirar los medicamentos", example = "2026-12-31", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fechaVencimiento;    

}