package v1.Configuracion_de_Descuentos.Controller;

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

import v1.Configuracion_de_Descuentos.controlador.Controlador_descuento;
import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import v1.Configuracion_de_Descuentos.servicio.Servicio_descuentos;

@WebMvcTest(Controlador_descuento.class)
public class Controlador_descuento_test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Servicio_descuentos servicio;

    private Modelo_configuracion_de_descuento descuentoEjemplo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        descuentoEjemplo = new Modelo_configuracion_de_descuento();
        descuentoEjemplo.setId(1L);
        descuentoEjemplo.setNombrePromocion("CAMP_NAVIDAD");
        descuentoEjemplo.setPorcentajeDescuento(15.5);
        descuentoEjemplo.setCodigoAutorizacion("NAV2026");
        descuentoEjemplo.setActivo(true);

        objectMapper = new ObjectMapper();
    }

    @Test
    void listar_retornaLista200() throws Exception {
        List<Modelo_configuracion_de_descuento> lista = new ArrayList<>();
        lista.add(descuentoEjemplo);
        when(servicio.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/descuentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoAutorizacion").value("NAV2026"))
                .andExpect(jsonPath("$[0].nombrePromocion").value("CAMP_NAVIDAD"));
    }

    @Test
    void buscarPorCodigo_activo_retornaObjeto200() throws Exception {
        when(servicio.buscarPorCodigoAutorizacion("NAV2026")).thenReturn(descuentoEjemplo);

        mockMvc.perform(get("/api/v1/descuentos/validar/NAV2026"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoAutorizacion").value("NAV2026"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    void buscarPorCodigo_inactivo_lanzaError4xxO5xx() throws Exception {
        descuentoEjemplo.setActivo(false); // Forzamos la campaña a inactiva
        when(servicio.buscarPorCodigoAutorizacion("NAV2026")).thenReturn(descuentoEjemplo);

        // Capturamos la excepción ServletException que Spring Web arroja en el entorno de pruebas
        jakarta.servlet.ServletException exception = org.junit.jupiter.api.Assertions.assertThrows(
            jakarta.servlet.ServletException.class, () -> {
                mockMvc.perform(get("/api/v1/descuentos/validar/NAV2026"));
            }
        );

        // Validamos que la causa raíz contenga el mensaje exacto programado en el servicio
        org.junit.jupiter.api.Assertions.assertTrue(
            exception.getRootCause().getMessage().contains("La campaña promocional asociada a este código se encuentra inactiva")
        );
    }

    @Test
    void guardar_retornaCreado200() throws Exception {
        when(servicio.guardar(any(Modelo_configuracion_de_descuento.class))).thenReturn(descuentoEjemplo);
        String jsonBody = objectMapper.writeValueAsString(descuentoEjemplo);

        mockMvc.perform(post("/api/v1/descuentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void cambiarEstadoPromocion_retornaModificado200() throws Exception {
        descuentoEjemplo.setActivo(false);
        when(servicio.cambiarEstadoPromocion(1L, false)).thenReturn(descuentoEjemplo);

        mockMvc.perform(put("/api/v1/descuentos/1/estado")
                .param("activo", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void eliminar_ejecutaCorrectamente200() throws Exception {
        doNothing().when(servicio).eliminar(1L);

        mockMvc.perform(delete("/api/v1/descuentos/1"))
                .andExpect(status().isOk());
    }
}