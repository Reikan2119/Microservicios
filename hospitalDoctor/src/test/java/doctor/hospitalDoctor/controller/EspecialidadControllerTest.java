package doctor.hospitalDoctor.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.service.EspecialidadService;

@WebMvcTest(EspecialidadController.class)
public class EspecialidadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EspecialidadService service;

    private Especialidad especialidadEjemplo;

    @BeforeEach
    void setUp() {
        especialidadEjemplo = new Especialidad();
        especialidadEjemplo.setId(1);
        especialidadEjemplo.setNombre("Cardiologia");
    }

    @Test
    void listar_retorna200() throws Exception {
        // ARRANGE
        List<Especialidad> lista = new ArrayList<>();
        lista.add(especialidadEjemplo);
        when(service.listar()).thenReturn(lista);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/especialidades"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Cardiologia"));
    }

    @Test
    void listar_vacio_retorna204() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(Collections.emptyList());

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/especialidades"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(especialidadEjemplo);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/especialidades/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cardiologia"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Especialidad no encontrada"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/especialidades/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Especialidad.class))).thenReturn(especialidadEjemplo);
        String jsonBody = new ObjectMapper().writeValueAsString(especialidadEjemplo);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/especialidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cardiologia"));
    }

    @Test
    void actualizar_retorna200() throws Exception {
        // ARRANGE
        when(service.actualizar(eq(1), any(Especialidad.class))).thenReturn(especialidadEjemplo);
        String jsonBody = new ObjectMapper().writeValueAsString(especialidadEjemplo);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/especialidades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/especialidades/1"))
                .andExpect(status().isNoContent());
    }
}