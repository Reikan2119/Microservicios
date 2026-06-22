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

import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.service.DoctorService;

@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DoctorService service;

    private Doctor doctorEjemplo;
    private Especialidad especialidadEjemplo;

    @BeforeEach
    void setUp() {
        especialidadEjemplo = new Especialidad();
        especialidadEjemplo.setId(1);
        especialidadEjemplo.setNombre("Cardiologia");

        doctorEjemplo = new Doctor();
        doctorEjemplo.setId(1);
        doctorEjemplo.setNombre("Mapache");
        doctorEjemplo.setApellido("loco");
        doctorEjemplo.setEspecialidad(especialidadEjemplo);
    }

    @Test
    void listar_retorna200() throws Exception {
        // ARRANGE
        List<Doctor> lista = new ArrayList<>();
        lista.add(doctorEjemplo);
        when(service.listar()).thenReturn(lista);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/doctores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Mapache"));
    }

    @Test
    void listar_vacio_retorna204() throws Exception {
        // ARRANGE
        when(service.listar()).thenReturn(Collections.emptyList());

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/doctores"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna200() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(doctorEjemplo);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/doctores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Mapache"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Doctor no encontrado"));

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/doctores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void guardar_retorna200() throws Exception {
        // ARRANGE
        when(service.guardar(any(Doctor.class))).thenReturn(doctorEjemplo);

        // Convertimos el objeto Java a JSON String para el cuerpo del Post
        String jsonBody = new ObjectMapper().writeValueAsString(doctorEjemplo);

        // ACT & ASSERT
        mockMvc.perform(post("/api/v1/doctores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Mapache"));
    }

    @Test
    void actualizar_retorna200() throws Exception {
        // ARRANGE
        when(service.actualizar(eq(1), any(Doctor.class))).thenReturn(doctorEjemplo);
        String jsonBody = new ObjectMapper().writeValueAsString(doctorEjemplo);

        // ACT & ASSERT
        mockMvc.perform(put("/api/v1/doctores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_retorna204() throws Exception {
        // ARRANGE - no lanzará excepciones
        doNothing().when(service).eliminar(1);

        // ACT & ASSERT
        mockMvc.perform(delete("/api/v1/doctores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void obtenerDoctorDTO_retorna200_conNombreConcatenado() throws Exception {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(doctorEjemplo);

        // ACT & ASSERT
        mockMvc.perform(get("/api/v1/doctores/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombreDoctor").value("Mapache loco")) 
                .andExpect(jsonPath("$.especialidad").value("Cardiologia"));
    }
}