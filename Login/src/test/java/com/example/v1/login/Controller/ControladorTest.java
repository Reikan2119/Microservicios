package com.example.v1.login.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.v1.login.DTO.EliminarCuentaDTO;
import com.example.v1.login.DTO.LoginDTO;
import com.example.v1.login.DTO.RegistroDTO;
import com.example.v1.login.Model.Rol;
import com.example.v1.login.controller.Controlador;
import com.example.v1.login.servicio.Servicio;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(Controlador.class)
public class ControladorTest {

    @Autowired
    private MockMvc mockMvc; //  Simula las peticiones HTTP (GET, POST, etc.)

    @MockitoBean
    private Servicio servicio; //  Mockea la capa de negocio asociada al controlador

    @Autowired
    private ObjectMapper objectMapper; //  Convierte objetos Java a cadenas JSON

    

    @Test
    void registrar_DeberiaRetornarOk_CuandoDatosSonValidos() throws Exception {
        // Arrange
        RegistroDTO dto = new RegistroDTO("diego_doc", "medico@gmail.com", "clave123", Rol.DOCTOR);
        when(servicio.registrarUsuario(any(RegistroDTO.class)))
            .thenReturn("Usuario creado con éxito con el rol: DOCTOR");

        // Act & Assert
        mockMvc.perform(post("/api/v1/log/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(content().string("Usuario creado con éxito con el rol: DOCTOR"));
    }

    @Test
    void registrar_DeberiaRetornarBadRequest_CuandoCorreoNoEsGmail() throws Exception {
        // Arrange
        RegistroDTO dto = new RegistroDTO("diego_doc", "medico@outlook.com", "clave123", Rol.DOCTOR);
        when(servicio.registrarUsuario(any(RegistroDTO.class)))
            .thenThrow(new IllegalArgumentException("Error: Solo se permiten correos con dominio @gmail.com"));

        // Act & Assert
        mockMvc.perform(post("/api/v1/log/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest()) // Espera HTTP 400
                .andExpect(content().string("Error: Solo se permiten correos con dominio @gmail.com"));
    }

    

    @Test
    void login_DeberiaRetornarOk_CuandoCredencialesSonCorrectas() throws Exception {
        // Arrange
        LoginDTO dto = new LoginDTO("diego_doc", "clave123");
        when(servicio.loginUsuario(any(LoginDTO.class)))
            .thenReturn("Login exitoso. Bienvenido, tu rol es: DOCTOR");

        // Act & Assert (Cambiado a post)
        mockMvc.perform(post("/api/v1/log/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().string("Login exitoso. Bienvenido, tu rol es: DOCTOR"));
    }

    @Test
    void login_DeberiaRetornarUnauthorized_CuandoCredencialesSonIncorrectas() throws Exception {
        // Arrange
        LoginDTO dto = new LoginDTO("diego_doc", "clave_erronea");
        when(servicio.loginUsuario(any(LoginDTO.class)))
            .thenThrow(new IllegalArgumentException("Error: Contraseña incorrecta."));

        // Act & Assert
        mockMvc.perform(post("/api/v1/log/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnauthorized()) // Espera HTTP 401 Unauthorized
                .andExpect(content().string("Error: Contraseña incorrecta."));
    }


    @Test
    void eliminarCuenta_DeberiaRetornarOk_CuandoPasswordCoincide() throws Exception {
        // Arrange
        EliminarCuentaDTO dto = new EliminarCuentaDTO("diego_doc", "clave123");
        when(servicio.eliminarCuentaUsuario(any(EliminarCuentaDTO.class)))
            .thenReturn("Cuenta eliminada correctamente del sistema.");

        // Act & Assert
        mockMvc.perform(delete("/api/v1/log/eliminar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()) // Espera HTTP 200
                .andExpect(content().string("Cuenta eliminada correctamente del sistema."));
    }
}