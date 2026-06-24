package com.hospital.ficha.Controller;

// Importaciones estáticas para disparar llamadas simuladas del servidor y verificar las respuestas HTTP
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.ficha.Model.fichaModel;
import com.hospital.ficha.Service.fichaService;

// Configura la capa web de Spring cargando de forma aislada únicamente el controlador de fichas
@WebMvcTest(fichaController.class)
public class fichaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Cliente simulado para interactuar con la API REST

    @MockBean
    private fichaService servicio; // Inyecta un mock del servicio en el contexto web controlado

    private fichaModel fichaEjemplo;
    private ObjectMapper objectMapper; // Herramienta para serializar objetos Java a formato JSON

    @BeforeEach
    void setUp() {
        fichaEjemplo = new fichaModel(1, "Examen general", "Paciente diabético", false, "Diabetes");
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllFichas_conElementos_retornaStatus200Ok() throws Exception {
        // PREPARACIÓN: Construimos una lista y configuramos el comportamiento del mock
        List<fichaModel> lista = new ArrayList<>();
        lista.add(fichaEjemplo);
        when(servicio.getAllFichas()).thenReturn(lista);

        // EJECUCIÓN Y VERIFICACIÓN: Ejecutamos una petición HTTP GET de forma síncrona
        mockMvc.perform(get("/api/v1/ficha"))
                .andExpect(status().isOk()) // Valida HTTP 200 OK
                .andExpect(jsonPath("$[0].id").value(1)) // Evalúa el ID en el primer nodo del arreglo JSON
                .andExpect(jsonPath("$[0].examinacion").value("Examen general"))
                .andExpect(jsonPath("$[0].diagnostico").value("Diabetes"));
    }

    @Test
    void getAllFichas_vacio_retornaStatus204NoContent() throws Exception {
        // PREPARACIÓN: El servicio devuelve una lista vacía
        when(servicio.getAllFichas()).thenReturn(new ArrayList<>());

        // EJECUCIÓN Y VERIFICACIÓN
        mockMvc.perform(get("/api/v1/ficha"))
                .andExpect(status().isNoContent()); // Valida HTTP 204 No Content según la regla condicional del controlador
    }

    @Test
    void getFichaById_existente_retornaStatus200Ok() throws Exception {
        when(servicio.getFichaById(1)).thenReturn(fichaEjemplo);

        mockMvc.perform(get("/api/v1/ficha/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.notas").value("Paciente diabético"));
    }

    @Test
    void getFichaById_error_retornaStatus404NotFound() throws Exception {
        // PREPARACIÓN: Simulamos que ocurre un fallo o excepción imprevista en el servicio para forzar el catch
        when(servicio.getFichaById(99)).thenThrow(new RuntimeException("Error forzado"));

        // EJECUCIÓN Y VERIFICACIÓN
        mockMvc.perform(get("/api/v1/ficha/99"))
                .andExpect(status().isNotFound()); // Valida que el bloque try-catch del controlador retorne HTTP 404
    }

    @Test
    void createFicha_retornaStatus200OkConFichaCreada() throws Exception {
        when(servicio.createFicha(any(fichaModel.class))).thenReturn(fichaEjemplo);
        String jsonBody = objectMapper.writeValueAsString(fichaEjemplo);

        mockMvc.perform(post("/api/v1/ficha")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk()) // Diego definió .ok() en vez de .created() en su código original
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.examinacion").value("Examen general"));
    }

    @Test
    void updateFicha_existente_retornaStatus200OkConFichaModificada() throws Exception {
        when(servicio.updateFicha(eq(1), any(fichaModel.class))).thenReturn(fichaEjemplo);
        String jsonBody = objectMapper.writeValueAsString(fichaEjemplo);

        mockMvc.perform(put("/api/v1/ficha/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateFicha_errorOInexistente_retornaStatus404NotFound() throws Exception {
        // PREPARACIÓN: Forzamos a que ocurra una excepción durante el proceso para disparar el catch
        when(servicio.updateFicha(eq(99), any(fichaModel.class))).thenThrow(new RuntimeException("No encontrado"));
        String jsonBody = objectMapper.writeValueAsString(fichaEjemplo);

        mockMvc.perform(put("/api/v1/ficha/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isNotFound()); // El catch del controlador captura el error y devuelve un HTTP 404 pure
    }
}