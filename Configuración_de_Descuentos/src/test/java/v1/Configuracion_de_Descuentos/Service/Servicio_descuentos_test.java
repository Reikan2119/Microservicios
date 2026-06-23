package v1.Configuracion_de_Descuentos.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import v1.Configuracion_de_Descuentos.modelo.Modelo_configuracion_de_descuento;
import v1.Configuracion_de_Descuentos.repositorio.Repositorio_descuentos;
import v1.Configuracion_de_Descuentos.servicio.Servicio_descuentos;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Blindaje contra stubbings innecesarios
public class Servicio_descuentos_test {

    @Mock
    private Repositorio_descuentos repository;

    @InjectMocks
    private Servicio_descuentos service;

    private Modelo_configuracion_de_descuento descuentoEjemplo;

    @BeforeEach
    void setUp() {
        descuentoEjemplo = new Modelo_configuracion_de_descuento();
        descuentoEjemplo.setId(1L);
        descuentoEjemplo.setNombrePromocion("CAMP_NAVIDAD");
        descuentoEjemplo.setPorcentajeDescuento(15.5);
        descuentoEjemplo.setCodigoAutorizacion("NAV2026");
        descuentoEjemplo.setActivo(true);
    }

    @Test
    void listar_retornaElementos() {
        List<Modelo_configuracion_de_descuento> lista = new ArrayList<>();
        lista.add(descuentoEjemplo);
        when(repository.findAll()).thenReturn(lista);

        List<Modelo_configuracion_de_descuento> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorCodigo_encontrado() {
        when(repository.findByCodigoAutorizacion("NAV2026")).thenReturn(Optional.of(descuentoEjemplo));

        Modelo_configuracion_de_descuento resultado = service.buscarPorCodigoAutorizacion("NAV2026");

        assertNotNull(resultado);
        assertEquals("NAV2026", resultado.getCodigoAutorizacion()); 
    }

    @Test
    void buscarPorId_noEncontrado_lanzaException() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99L);
        });
        assertEquals("Configuración de descuento no encontrada", exception.getMessage());
    }

    @Test
    void guardar_codigoNuevo_exitoso() {
        // Simulamos un ítem nuevo sin ID
        Modelo_configuracion_de_descuento nuevo = new Modelo_configuracion_de_descuento(null, "PROMO", 10.0, "NUEVO10", true);
        
        when(repository.existsByCodigoAutorizacion("NUEVO10")).thenReturn(false);
        when(repository.save(any(Modelo_configuracion_de_descuento.class))).thenReturn(nuevo);

        Modelo_configuracion_de_descuento resultado = service.guardar(nuevo);

        assertNotNull(resultado);
        verify(repository, times(1)).save(nuevo);
    }

    @Test
    void guardar_codigoDuplicado_lanzaException() {
        Modelo_configuracion_de_descuento duplicado = new Modelo_configuracion_de_descuento(null, "PROMO", 10.0, "NAV2026", true);
        when(repository.existsByCodigoAutorizacion("NAV2026")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardar(duplicado);
        });
        assertEquals("El código de autorización ya está registrado para otra campaña", exception.getMessage());
        verify(repository, never()).save(any());
    }
}