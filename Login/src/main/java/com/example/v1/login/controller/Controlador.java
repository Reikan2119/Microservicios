package com.example.v1.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.v1.login.DTO.EliminarCuentaDTO;
import com.example.v1.login.DTO.LoginDTO;
import com.example.v1.login.DTO.RegistroDTO;
import com.example.v1.login.servicio.Servicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/log")
@Tag(name = "Autenticacion", description = "Controlador central para el registro de usuarios, inicio de sesión y eliminación de cuentas del sistema hospitalario")
public class Controlador {

    @Autowired
    private Servicio service; 

    // POST: Registro de Usuarios
    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo usuario", description = "Procesa el formulario de registro, valida que el dominio del correo institucional sea correcto e inicializa las credenciales")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente en el sistema"),
        @ApiResponse(responseCode = "400", description = "Error en las validaciones de datos (ej. formato de correo inválido)")
    })
    public ResponseEntity<String> registrar(@RequestBody RegistroDTO dto) {
        try {
            String resultado = service.registrarUsuario(dto);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST: Login de Usuarios
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión en el sistema", description = "Valida las credenciales proporcionadas contra los registros de la base de datos para otorgar acceso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Autenticación exitosa"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas o el usuario no existe")
    })
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        try {
            String resultado = service.loginUsuario(dto);
            return ResponseEntity.ok(resultado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // DELETE: Eliminar cuenta
    @DeleteMapping("/eliminar")
    @Operation(summary = "Eliminar cuenta de usuario", description = "Verifica la identidad y elimina de forma definitiva el acceso del usuario del sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta eliminada de forma definitiva"),
        @ApiResponse(responseCode = "401", description = "No autorizado. La contraseña proporcionada no coincide con los registros")
    })
    public ResponseEntity<String> eliminarCuenta(@RequestBody EliminarCuentaDTO dto) {
        try {   
            String resultado = service.eliminarCuentaUsuario(dto);
            return ResponseEntity.ok(resultado); 
        } catch (IllegalArgumentException errorr) {
            return ResponseEntity.status(401).body(errorr.getMessage());
        }
    }
}