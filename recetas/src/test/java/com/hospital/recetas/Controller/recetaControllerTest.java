package com.hospital.recetas.Controller;

// Importaciones estáticas para simular verbos HTTP (GET, POST, etc.) y validar respuestas JSON
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
import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Service.recetaService;

// Configura la prueba aislando únicamente el controlador web especificado
@WebMvcTest(recetaController.class)
public class recetaControllerTest {

    @Autowired
    private MockMvc mockMvc; // Motor para realizar peticiones HTTP simuladas

    @MockBean
    private recetaService servicio; // Inyecta un mock del servicio en el controlador

    private recetaModel recetaEjemplo;
    private ObjectMapper objectMapper; // Convierte objetos Java en cadenas JSON de texto

    @BeforeEach
    void setUp() {
        recetaEjemplo = new recetaModel();
        recetaEjemplo.setId(1);
        recetaEjemplo.setCodigo("REC-2026-001");
        recetaEjemplo.setMedicamentos("Paracetamol 500mg");
        recetaEjemplo.setFechaVencimiento("2026-12-31");

        objectMapper = new ObjectMapper();
    }

    @Test
    void guardar_retornaStatus201Created() throws Exception {
        // PREPARACIÓN: Indicamos que al guardar devuelva la receta con ID generado
        when(servicio.guardar(any(recetaModel.class))).thenReturn(recetaEjemplo);
        String jsonBody = objectMapper.writeValueAsString(recetaEjemplo);

        // EJECUCIÓN Y VERIFICACIÓN
        mockMvc.perform(post("/api/v1/recetas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated()) // Espera HTTP Status 201 Created
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.codigo").value("REC-2026-001"));
    }

    @Test
    void listar_conElementos_retornaStatus200Ok() throws Exception {
        List<recetaModel> lista = new ArrayList<>();
        lista.add(recetaEjemplo);
        when(servicio.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/recetas"))
                .andExpect(status().isOk()) // Espera HTTP Status 200 OK
                .andExpect(jsonPath("$[0].codigo").value("REC-2026-001"));
    }

    @Test
    void listar_vacio_retornaStatus204NoContent() throws Exception {
        // PREPARACIÓN: Retornamos una lista vacía para activar la validación condicional de Diego
        when(servicio.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/recetas"))
                .andExpect(status().isNoContent()); // Espera HTTP Status 204 No Content
    }

    @Test
    void getRecetaById_existente_retornaStatus200Ok() throws Exception {
        when(servicio.getRecetaById(1)).thenReturn(recetaEjemplo);

        mockMvc.perform(get("/api/v1/recetas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizar_exitoso_retornaStatus200Ok() throws Exception {
        when(servicio.actualizar(eq(1), any(recetaModel.class))).thenReturn(recetaEjemplo);
        String jsonBody = objectMapper.writeValueAsString(recetaEjemplo);

        mockMvc.perform(put("/api/v1/recetas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("REC-2026-001"));
    }

    @Test
    void actualizar_inexistente_retornaStatus404NotFound() throws Exception {
        // PREPARACIÓN: Forzamos a que el servicio dispare la excepción al no encontrar la receta
        when(servicio.actualizar(eq(99), any(recetaModel.class))).thenThrow(new RuntimeException("Receta no encontrada"));
        String jsonBody = objectMapper.writeValueAsString(recetaEjemplo);

        mockMvc.perform(put("/api/v1/recetas/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isNotFound()); // Espera HTTP Status 404 Not Found por el bloque try-catch del controlador
    }

    @Test
    void deleteReceta_exitoso_retornaStatus204NoContent() throws Exception {
        doNothing().when(servicio).deleteReceta(1);

        mockMvc.perform(delete("/api/v1/recetas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void recetaDTO_mapeaCorrectamente200Ok() throws Exception {
        // PREPARACIÓN: El controlador pide la receta al servicio y mapea manualmente sus propiedades al DTO
        when(servicio.getRecetaById(1)).thenReturn(recetaEjemplo);

        mockMvc.perform(get("/api/v1/recetas/dto/1"))
                .andExpect(status().isOk())
                // Verificamos que el JSON de salida tenga los campos del DTO planos (no tiene atributo id)
                .andExpect(jsonPath("$.codigo").value("REC-2026-001"))
                .andExpect(jsonPath("$.medicamentos").value("Paracetamol 500mg"))
                .andExpect(jsonPath("$.fechaVencimiento").value("2026-12-31"))
                .andExpect(jsonPath("$.id").doesNotExist()); // El DTO no incluye ID por diseño
    }
}