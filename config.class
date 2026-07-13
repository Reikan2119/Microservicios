package com.example.v1.login.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) requerido para validar la identidad de un usuario antes de proceder con la eliminación definitiva de su cuenta")
public class EliminarCuentaDTO {

    @Schema(description = "Nombre de usuario o alias registrado en el sistema", example = "carlos_medico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "Contraseña actual asociada al usuario para confirmación de seguridad", example = "ClaveSegura2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}