package com.hospital.notificaciones.Controller;

// Importaciones de MockMvc para simular llamadas de red HTTP y evaluar respuestas
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hospital.notificaciones.Model.notificacionModel;
import com.hospital.notificaciones.Service.notificacionesService;

// Restringe el contexto de Spring Boot cargando únicamente la capa web para este controlador
@WebMvcTest(notificacionesController.class)
public class notificacionesControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulador del servidor web Tomcat integrado

    @MockBean
    private notificacionesService servicio; // Reemplaza el servicio real en el contenedor de Spring por un Mock

    private notificacionModel notificacionEjemplo;

    // Prepara el entorno para cada test
    @BeforeEach
    void setUp() {
        notificacionEjemplo = new notificacionModel(1, "Whatsapp", "Su receta está lista.", "Enviado");
    }

    @Test
    void obtenerTodas_retornaListaDeNotificacionesConStatus200Ok() throws Exception {
        // PREPARACIÓN: Entrenamos al mock del servicio para que retorne una lista poblada
        List<notificacionModel> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(servicio.obtenerTodas()).thenReturn(lista);

        // EJECUCIÓN Y VERIFICACIÓN: Ejecutamos un GET síncrono al endpoint base
        mockMvc.perform(get("/api/v1/notificaciones"))
                .andExpect(status().isOk()) // Valida que el servidor responda con un HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Evalúa el ID en la primera posición del JSON
                .andExpect(jsonPath("$[0].medioContacto").value("Whatsapp")) // Valida el medio de contacto
                .andExpect(jsonPath("$[0].mensaje").value("Su receta está lista.")); // Valida el contenido del mensaje
    }

    @Test
    void enviarNotificacion_enviaParametrosCorrectamenteYRetornaStatus200Ok() throws Exception {
        // PREPARACIÓN: Como el método del servicio es un 'void', configuramos que no haga nada al ser invocado
        doNothing().when(servicio).enviarNotificacion("SMS", "Hola mundo");

        // EJECUCIÓN Y VERIFICACIÓN: Usamos .param() para emular variables en la URL o campos de formulario
        mockMvc.perform(post("/api/v1/notificaciones")
                .param("medioContacto", "SMS")
                .param("mensaje", "Hola mundo"))
                .andExpect(status().isOk()); // Diego no asignó código 201 Created aquí, por ende valida un 200 OK clásico

        // Verificación de seguridad: Comprueba que el controlador verdaderamente le pasó los parámetros limpios al servicio
        verify(servicio, times(1)).enviarNotificacion("SMS", "Hola mundo");
    }
}