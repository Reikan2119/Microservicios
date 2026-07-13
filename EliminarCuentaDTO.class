package com.example.v1.login.Model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Un 'enum' representa una lista cerrada de constantes fijas que nunca cambian.
 * En lugar de usar Strings en una clase normal (que permitirían errores de dedo como "doctro"),
 * el enum restringe el sistema a usar únicamente las 3 opciones de roles oficiales del hospital.
 */
@Schema(description = "Perfil de acceso o rol asignado a las cuentas de usuario para controlar los permisos dentro del sistema hospitalario")
public enum Rol {
    
    @Schema(description = "Permisos asignados al personal médico para interactuar con fichas clínicas y agendas")
    DOCTOR, 
    
    @Schema(description = "Perfil asignado a los pacientes para revisar su historial o atenciones")
    PACIENTE, 
    
    @Schema(description = "Acceso total para configurar parámetros, gestionar descuentos e inventario")
    ADMINISTRADOR
}