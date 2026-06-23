package com.example.v1.login.DTO;

import com.example.v1.login.Model.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) utilizado para recopilar la información requerida en la creación de una nueva cuenta de usuario con su rol correspondiente")
public class RegistroDTO {

    @Schema(description = "Nombre de usuario único o alias que se desea registrar", example = "carlos_medico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Dirección de correo electrónico institucional (Debe cumplir con el dominio requerido por el sistema)", example = "contacto@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Contraseña segura que se asignará a la nueva cuenta", example = "ClaveSegura2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(description = "Rol o perfil de acceso asignado al usuario dentro del entorno hospitalario (ej. MEDICO, CAJERO, ADMIN)", requiredMode = Schema.RequiredMode.REQUIRED)
    private Rol rol;
}