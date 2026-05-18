package com.hospital.recetas.Controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class recetaDto {

    private String codigo;
    private String medicamentos;    
    private String fechaVencimiento;
}
