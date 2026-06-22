package pacientes.hospitalPaciente.Controller;

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
import org.springframework.test.web.servlet.MockMvc;


import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Service.ContactoService;

@WebMvcTest(ContactoController.class)
public class ContactoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactoService service;

    private Contacto contactoEjemplo;

    @BeforeEach
    void setUp() {
        contactoEjemplo = new Contacto(1, "Juanito", "+56912345678", "contacto@email.com");
    }

    @Test
    void listar_retorna200() throws Exception {
        List<Contacto> lista = new ArrayList<>();
        lista.add(contactoEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/contactos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juanito"));
    }

    @Test
    void listar_vacio_retorna204() throws Exception {
        when(service.listar()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/contactos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("No encontrado"));

        mockMvc.perform(get("/api/v1/contactos/99"))
                .andExpect(status().isNotFound());
    }
}