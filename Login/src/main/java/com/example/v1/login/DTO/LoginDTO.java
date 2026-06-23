package com.example.v1.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) utilizado para capturar las credenciales de acceso durante el inicio de sesión")
public class LoginDTO {

    @Schema(description = "Nombre de usuario o alias para el ingreso al sistema", example = "carlos_medico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Contraseña en texto plano asociada a la cuenta del usuario", example = "ClaveSegura2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}