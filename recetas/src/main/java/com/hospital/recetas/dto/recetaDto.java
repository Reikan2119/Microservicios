package com.hospital.recetas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 🔥 Importación necesaria para documentar la estructura del JSON en Swagger
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Describe el propósito del DTO en la sección de esquemas inferiores de Swagger
@Schema(description = "Objeto de transferencia de datos (DTO) utilizado para exponer la información esencial de una receta médica a otros microservicios")
public class recetaDto {

    // Documenta el código con su ejemplo correspondiente
    @Schema(description = "Código único de autorización de la receta", example = "REC-2026-99X")
    private String codigo;

    // Documenta el listado de medicamentos
    @Schema(description = "Detalle de los medicamentos recetados y sus dosis", example = "Paracetamol 1g cada 8 horas")
    private String medicamentos;    

    // Documenta la fecha límite de retiro
    @Schema(description = "Fecha de vencimiento o límite para el retiro de los fármacos", example = "2026-12-31")
    private String fechaVencimiento;
}