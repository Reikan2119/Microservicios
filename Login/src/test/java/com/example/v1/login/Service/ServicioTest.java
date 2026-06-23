package com.example.v1.login.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.v1.login.DTO.EliminarCuentaDTO;
import com.example.v1.login.DTO.LoginDTO;
import com.example.v1.login.DTO.RegistroDTO;
import com.example.v1.login.Model.Rol;
import com.example.v1.login.Model.Usuario;
import com.example.v1.login.repositorio.Repositorio;
import com.example.v1.login.servicio.Servicio;

@ExtendWith(MockitoExtension.class)
public class ServicioTest {

    @Mock
    private Repositorio repositorio; //  Simulamos el comportamiento del repositorio

    @InjectMocks
    private Servicio servicio; // 🔌 Inyectamos el mock simulado dentro de tu servicio real

    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        // Inicializamos un objeto de usuario común para reutilizar en los tests
        usuarioPrueba = new Usuario(1L, "diego_admin", "diego@gmail.com", "clave123", Rol.ADMINISTRADOR);
    }

    // ==========================================
    // 🟢 PRUEBAS DE REGISTRO
    // ==========================================

    @Test
    void registrarUsuario_Exitoso() {
        // Arrange (Preparar datos)
        RegistroDTO dto = new RegistroDTO("diego_admin", "diego@gmail.com", "clave123", Rol.ADMINISTRADOR);
        when(repositorio.existsByUsername(dto.getUsername())).thenReturn(false);
        when(repositorio.existsByEmail(dto.getEmail())).thenReturn(false);

        // Act (Ejecutar)
        String resultado = servicio.registrarUsuario(dto);

        // Assert (Verificar resultados)
        assertNotNull(resultado);
        assertTrue(resultado.contains("Usuario creado con éxito"));
        verify(repositorio, times(1)).save(any(Usuario.class)); // Verifica que se llamó a guardar una vez
    }

    @Test
    void registrarUsuario_ErrorCorreoInvalido() {
        // Arrange
        RegistroDTO dto = new RegistroDTO("diego_admin", "diego@outlook.com", "clave123", Rol.ADMINISTRADOR);

        // Act & Assert (Debe lanzar la excepción esperada)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarUsuario(dto);
        });

        assertEquals("Error: Solo se permiten correos con dominio @gmail.com", exception.getMessage());
        verify(repositorio, never()).save(any(Usuario.class)); // Jamás debe guardar si el correo falla
    }

    @Test
    void registrarUsuario_ErrorUsernameDuplicado() {
        // Arrange
        RegistroDTO dto = new RegistroDTO("diego_admin", "diego@gmail.com", "clave123", Rol.ADMINISTRADOR);
        when(repositorio.existsByUsername(dto.getUsername())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.registrarUsuario(dto);
        });

        assertEquals("Error: El nombre de usuario ya está registrado.", exception.getMessage());
    }

    // ==========================================
    // 🔵 PRUEBAS DE LOGIN
    // ==========================================

    @Test
    void loginUsuario_Exitoso() {
        // Arrange
        LoginDTO dto = new LoginDTO("diego_admin", "clave123");
        when(repositorio.findByUsername(dto.getUsername())).thenReturn(Optional.of(usuarioPrueba));

        // Act
        String resultado = servicio.loginUsuario(dto);

        // Assert
        assertTrue(resultado.contains("Login exitoso"));
    }

    @Test
    void loginUsuario_ErrorContrasenaIncorrecta() {
        // Arrange
        LoginDTO dto = new LoginDTO("diego_admin", "clave_incorrecta");
        when(repositorio.findByUsername(dto.getUsername())).thenReturn(Optional.of(usuarioPrueba));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            servicio.loginUsuario(dto);
        });

        assertEquals("Error: Contraseña incorrecta.", exception.getMessage());
    }

    // ==========================================
    // 🔴 PRUEBAS DE ELIMINACIÓN DE CUENTA
    // ==========================================

    @Test
    void eliminarCuentaUsuario_Exitoso() {
        // Arrange
        EliminarCuentaDTO dto = new EliminarCuentaDTO("diego_admin", "clave123");
        when(repositorio.findByUsername(dto.getUsername())).thenReturn(Optional.of(usuarioPrueba));

        // Act
        String resultado = servicio.eliminarCuentaUsuario(dto);

        // Assert
        assertEquals("Cuenta eliminada correctamente del sistema.", resultado);
        verify(repositorio, times(1)).delete(usuarioPrueba); // Confirma el borrado físico
    }
}