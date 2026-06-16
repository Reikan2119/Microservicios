package v1.caja.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.databind.ObjectMapper;

import v1.caja.DTO.DTOcaja;
import v1.caja.Modelo.ModeloCaja;
import v1.caja.repositorio.Repositorio_caja;
import v1.caja.Servicio.Servicio_caja;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class Servicio_caja_test {

    @Mock
    private Repositorio_caja repository;

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @InjectMocks
    private Servicio_caja service;

    private ModeloCaja entradaCaja;
    private DTOcaja dtoDescuento;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        
        org.springframework.test.util.ReflectionTestUtils.setField(service, "restTemplate", restTemplate);

        entradaCaja = new ModeloCaja();
        entradaCaja.setId(null);
        entradaCaja.setMontoEfectivo(200.0);       // Total bruto: 1000.0
        entradaCaja.setMontoDebito(300.0);
        entradaCaja.setMontoCredito(400.0);
        entradaCaja.setMontoTransferencia(100.0);
        entradaCaja.setCodigoDescuentoAplicado("DESC10");
        entradaCaja.setIdCajero(10L);

        // Instanciamos el DTO original de Diego usando setters de Lombok
        dtoDescuento = new DTOcaja();
        dtoDescuento.setId(1L);
        dtoDescuento.setNombrePromocion("PROMO_X");
        dtoDescuento.setPorcentajeDescuento(10.0);
        dtoDescuento.setCodigoAutorizacion("DESC10");
        dtoDescuento.setActivo(true);

        objectMapper = new ObjectMapper();
    }

    @Test
    void guardar_conDescuentoValido_aplicaRebajaMatematica() throws Exception {
        String jsonResponse = objectMapper.writeValueAsString(dtoDescuento);

        mockServer.expect(requestTo("http://localhost:8085/api/descuentos/validar/DESC10"))
                  .andExpect(method(org.springframework.http.HttpMethod.GET))
                  .andRespond(withSuccess(jsonResponse, MediaType.APPLICATION_JSON));

        when(repository.save(any(ModeloCaja.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ModeloCaja resultado = service.guardar(entradaCaja);

        mockServer.verify();
        assertNotNull(resultado);
        assertEquals(900.0, resultado.getTotalVentasDiarias()); // 1000 - 10%
    }

    @Test
    void guardar_conDescuentoInexistente_lanzaRuntimeException() {
        mockServer.expect(requestTo("http://localhost:8085/api/descuentos/validar/DESC10"))
                  .andRespond(withResourceNotFound());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardar(entradaCaja);
        });

        assertTrue(exception.getMessage().contains("El código de descuento ingresado no es válido o está vencido."));
        verify(repository, never()).save(any());
    }

    @Test
    void guardar_sinCodigoDescuento_calculaTotalBrutoSinAlterar() {
        entradaCaja.setCodigoDescuentoAplicado(""); 
        when(repository.save(any(ModeloCaja.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ModeloCaja resultado = service.guardar(entradaCaja);

        assertNotNull(resultado);
        assertEquals(1000.0, resultado.getTotalVentasDiarias());
    }

    @Test
    void buscarPorId_noEncontrado_lanzaException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99L);
        });
        assertEquals("Registro de caja no encontrado", exception.getMessage());
    }
}