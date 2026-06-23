package v1.inventario.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import v1.inventario.modelo.inventario_item;
import v1.inventario.repositorio.inventario_repo;
import v1.inventario.service.inventario_service;

@ExtendWith(MockitoExtension.class)
public class inventario_service_test {

    @Mock
    private inventario_repo repository;

    @InjectMocks
    private inventario_service service;

    private inventario_item itemEjemplo;

    @BeforeEach
    void setUp() {
        itemEjemplo = new inventario_item();
        itemEjemplo.setId(1L);
        itemEjemplo.setSku("BISTURI-01");
        itemEjemplo.setNombre("Bisturí Quirúrgico N11");
        itemEjemplo.setStockActual(20);
        itemEjemplo.setFechaVencimientoLote(LocalDate.now().plusMonths(6));
    }

    @Test
    void listar_retornaElementos() {
        List<inventario_item> lista = new ArrayList<>();
        lista.add(itemEjemplo);
        when(repository.findAll()).thenReturn(lista);

        List<inventario_item> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorSku_encontrado() {
        when(repository.findBySku("BISTURI-01")).thenReturn(Optional.of(itemEjemplo));

        inventario_item resultado = service.buscarPorSku("BISTURI-01");

        assertNotNull(resultado);
        // Dejo este con "BISTURI-01" para que corra limpio si ya terminaste el experimento
        assertEquals("BISTURI-01", resultado.getSku()); 
    }

    @Test
    void buscarPorSku_noEncontrado_lanzaException() {
        when(repository.findBySku("X-99")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorSku("X-99");
        });
        assertEquals("Insumo no encontrado con el SKU especificado", exception.getMessage());
    }

    @Test
    void guardar_skuNuevo_exitoso() {
    // 1. Comentamos la línea que Mockito detecta como innecesaria
    // when(repository.existsBySku(anyString())).thenReturn(false);
    
    when(repository.save(any(inventario_item.class))).thenReturn(itemEjemplo);

    // 2. Ejecutamos
    inventario_item resultado = service.guardar(itemEjemplo);

    // 3. Verificaciones
    assertNotNull(resultado);
    verify(repository, times(1)).save(any(inventario_item.class));
}

    @Test
    void guardar_skuDuplicado_lanzaException() {
        inventario_item itemNuevoDuplicado = new inventario_item(null, "BISTURI-01", "Otro Bisturí", 10, LocalDate.now());
        when(repository.existsBySku("BISTURI-01")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.guardar(itemNuevoDuplicado);
        });
        assertEquals("El SKU ya se encuentra registrado", exception.getMessage());
        verify(repository, never()).save(any());
    }
}