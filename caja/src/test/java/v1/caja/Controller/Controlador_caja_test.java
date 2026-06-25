package v1.caja.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;
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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import v1.caja.Controlador.Controlador_caja;
import v1.caja.Modelo.ModeloCaja;
import v1.caja.Servicio.Servicio_caja;

@WebMvcTest(Controlador_caja.class)
public class Controlador_caja_test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Servicio_caja servicio;

    private ModeloCaja registroCajaEjemplo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        registroCajaEjemplo = new ModeloCaja(
            1L, 1000.0, 400.0, 300.0, 200.0, 100.0, "PROMO10", LocalDateTime.now(), 5L
        );

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listar_retornaListaRegistros200() throws Exception {
        List<ModeloCaja> lista = new ArrayList<>();
        lista.add(registroCajaEjemplo);
        when(servicio.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/finanzas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].totalVentasDiarias").value(1000.0))
                .andExpect(jsonPath("$[0].codigoDescuentoAplicado").value("PROMO10"));
    }

    @Test
    void buscarPorCajero_retornaListaCajero200() throws Exception {
        List<ModeloCaja> lista = new ArrayList<>();
        lista.add(registroCajaEjemplo);
        when(servicio.buscarPorCajero(5L)).thenReturn(lista);

        mockMvc.perform(get("/api/v1/finanzas/cajero/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idCajero").value(5));
    }

    @Test
    void guardar_procesaRegistroExitosamente200() throws Exception {
        when(servicio.guardar(any(ModeloCaja.class))).thenReturn(registroCajaEjemplo);
        String jsonBody = objectMapper.writeValueAsString(registroCajaEjemplo);

        mockMvc.perform(post("/api/v1/finanzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void eliminar_ejecutaBorrado200() throws Exception {
        doNothing().when(servicio).eliminar(1L);

        mockMvc.perform(delete("/api/v1/finanzas/1"))
                .andExpect(status().isOk());
    }
}