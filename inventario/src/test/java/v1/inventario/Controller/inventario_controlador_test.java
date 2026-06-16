package v1.inventario.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
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

// Enlazamos directo con la lógica real de Diego
import v1.inventario.controlador.inventario_controlador;
import v1.inventario.modelo.inventario_item;
import v1.inventario.service.inventario_service;

@WebMvcTest(inventario_controlador.class)
public class inventario_controlador_test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private inventario_service service;

    private inventario_item itemEjemplo;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        itemEjemplo = new inventario_item();
        itemEjemplo.setId(1L);
        itemEjemplo.setSku("BISTURI-01");
        itemEjemplo.setNombre("Bisturí Quirúrgico N11");
        itemEjemplo.setStockActual(20);
        itemEjemplo.setFechaVencimientoLote(LocalDate.of(2026, 12, 31));

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void listar_retornaLista200() throws Exception {
        List<inventario_item> lista = new ArrayList<>();
        lista.add(itemEjemplo);
        when(service.listar()).thenReturn(lista);

        mockMvc.perform(get("/api/v1/inventario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value("BISTURI-01"))
                .andExpect(jsonPath("$[0].nombre").value("Bisturí Quirúrgico N11"));
    }

    @Test
    void buscarPorSku_retornaObjeto200() throws Exception {
        when(service.buscarPorSku("BISTURI-01")).thenReturn(itemEjemplo);

        mockMvc.perform(get("/api/v1/inventario/sku/BISTURI-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value("BISTURI-01")) // 💥 Cambiar a "JERINGA-05" si quieres forzar error
                .andExpect(jsonPath("$.stockActual").value(20));
    }

    @Test
    void guardar_retornaObjetoGuardado200() throws Exception {
        when(service.guardar(any(inventario_item.class))).thenReturn(itemEjemplo);
        String jsonBody = objectMapper.writeValueAsString(itemEjemplo);

        mockMvc.perform(post("/api/v1/inventario")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizarStock_conRequestParam_retorna200() throws Exception {
        itemEjemplo.setStockActual(15);
        when(service.actualizarStock("BISTURI-01", -5)).thenReturn(itemEjemplo);

        mockMvc.perform(put("/api/v1/inventario/sku/BISTURI-01/stock")
                .param("cantidad", "-5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stockActual").value(15));
    }

    @Test
    void verificarAlertasStock_conParametro_retornaLista200() throws Exception {
        List<inventario_item> alertas = new ArrayList<>();
        alertas.add(itemEjemplo);
        // Sincronizado con el controlador real de Diego
        when(service.verificarAlertasStock(5)).thenReturn(alertas);

        mockMvc.perform(get("/api/v1/inventario/alertas").param("limite", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void eliminar_ejecutaCorrectamente200() throws Exception {
        doNothing().when(service).eliminar(1L);

        mockMvc.perform(delete("/api/v1/inventario/1"))
                .andExpect(status().isOk());
    }
}