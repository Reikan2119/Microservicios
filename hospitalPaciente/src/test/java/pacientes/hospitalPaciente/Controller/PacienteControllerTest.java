package pacientes.hospitalPaciente.Controller;

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

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Service.PacienteService;

@WebMvcTest(PacienteController.class)
public class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService service;

    private Paciente pacienteEjemplo;
    private Contacto contactoEjemplo;

    @BeforeEach
    void setUp() {
        contactoEjemplo = new Contacto(1, "Juanito", "+56912345678", "contacto@email.com");
        pacienteEjemplo = new Paciente(1, "12345678-9", "Juan", "Perez", 30, contactoEjemplo);
    }

    @Test
    void listar_retorna200() throws Exception {
        List<Paciente> lista = new ArrayList<>();
        lista.add(pacienteEjemplo);
        when(service.listarPacientes()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void buscarPorRut_retorna200() throws Exception {
        when(service.buscarPorRut("12345678-9")).thenReturn(pacienteEjemplo);

        mockMvc.perform(get("/api/v1/pacientes/rut/12345678-9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido").value("Perez"));
    }

    @Test
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Paciente.class))).thenReturn(pacienteEjemplo);
        String jsonBody = new ObjectMapper().writeValueAsString(pacienteEjemplo);

        mockMvc.perform(post("/api/v1/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void obtenerPacienteDTO_retorna200_conDatosMapeados() throws Exception {
        when(service.buscarPorId(1)).thenReturn(pacienteEjemplo);

        mockMvc.perform(get("/api/v1/pacientes/dto/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                // 🎯 Verifica que concatena nombre + apellido en tu controlador:
                .andExpect(jsonPath("$.nombrePaciente").value("Juan Perez"))
                .andExpect(jsonPath("$.nombreContacto").value("Juanito"))
                .andExpect(jsonPath("$.email").value("contacto@email.com"));
    }
}