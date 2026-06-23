package com.example.v1.login.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "usuarios")
@Schema(description = "Entidad de persistencia que representa las credenciales y el perfil de acceso de un usuario en el sistema de seguridad")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único auto-incremental asignado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Nombre de usuario único para el inicio de sesión (No se permiten duplicados)", example = "carlos_medico", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Column(unique = true, nullable = false)
    @Schema(description = "Dirección de correo electrónico única asociada a la cuenta", example = "contacto@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña de acceso almacenada en texto plano", example = "ClaveSegura2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Rol o nivel de autorización asignado al usuario", example = "DOCTOR", requiredMode = Schema.RequiredMode.REQUIRED)
    private Rol rol;  
}