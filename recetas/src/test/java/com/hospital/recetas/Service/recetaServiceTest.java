package com.hospital.recetas.Service;

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

import com.hospital.recetas.Model.recetaModel;
import com.hospital.recetas.Repository.recetaRepository;

// Habilita el soporte de Mockito para JUnit 5
@ExtendWith(MockitoExtension.class)

// Evita fallos si configuramos algún simulador (stub) que no se use de forma estricta en un test específico
@MockitoSettings(strictness = Strictness.LENIENT)
public class recetaServiceTest {

    // Simula el comportamiento del repositorio sin tocar la base de datos real
    @Mock
    private recetaRepository repository;

    // Inyecta automáticamente los mocks de arriba dentro del servicio real
    @InjectMocks
    private recetaService service;

    private recetaModel recetaEjemplo;

    // Se ejecuta antes de cada método @Test para reiniciar los datos de prueba
    @BeforeEach
    void setUp() {
        recetaEjemplo = new recetaModel();
        recetaEjemplo.setId(1);
        recetaEjemplo.setCodigo("REC-2026-001");
        recetaEjemplo.setMedicamentos("Paracetamol 500mg, Ibuprofeno 400mg");
        recetaEjemplo.setFechaVencimiento("2026-12-31");
    }

    @Test
    void listar_retornaListaDeRecetas() {
        // PREPARACIÓN: Creamos una lista simulada y le decimos al mock que la devuelva
        List<recetaModel> lista = new ArrayList<>();
        lista.add(recetaEjemplo);
        when(repository.findAll()).thenReturn(lista);

        // EJECUCIÓN: Llamamos al método real del servicio
        List<recetaModel> resultado = service.listar();

        // VERIFICACIÓN: Validamos que la lista no esté vacía y contenga el elemento esperado
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("REC-2026-001", resultado.get(0).getCodigo());
    }

    @Test
    void guardar_retornaRecetaGuardada() {
        // PREPARACIÓN: Indicamos que cuando se guarde cualquier recetaModel, retorne nuestra recetaEjemplo
        when(repository.save(any(recetaModel.class))).thenReturn(recetaEjemplo);

        // EJECUCIÓN: Guardamos el objeto
        recetaModel resultado = service.guardar(recetaEjemplo);

        // VERIFICACIÓN: Comprobamos que no sea nulo y se haya persistido de forma correcta
        assertNotNull(resultado);
        assertEquals("REC-2026-001", resultado.getCodigo());
        verify(repository, times(1)).save(recetaEjemplo); // Verifica que se llamó exactamente una vez al save
    }

    @Test
    void getRecetaById_existente_retornaReceta() {
        // PREPARACIÓN: Simulamos que al buscar el ID 1, el repositorio lo encuentra envuelto en un Optional
        when(repository.findById(1)).thenReturn(Optional.of(recetaEjemplo));

        // EJECUCIÓN
        recetaModel resultado = service.getRecetaById(1);

        // VERIFICACIÓN
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void getRecetaById_inexistente_retornaNull() {
        // PREPARACIÓN: Simulamos que al buscar el ID 99, el repositorio devuelve un vacío
        when(repository.findById(99)).thenReturn(Optional.empty());

        // EJECUCIÓN
        recetaModel resultado = service.getRecetaById(99);

        // VERIFICACIÓN: El servicio está programado para devolver null si no existe (.orElse(null))
        assertNull(resultado);
    }

    @Test
    void actualizar_existente_modificaYGuarda() {
        // PREPARACIÓN: El registro original está en la BD ficticia
        when(repository.findById(1)).thenReturn(Optional.of(recetaEjemplo));
        
        // Creamos los nuevos datos de actualización
        recetaModel nuevosDatos = new recetaModel(null, "REC-NUEVA", "Amoxicilina 500mg", "2027-01-01");
        
        // Simulamos el guardado de la receta modificada
        when(repository.save(any(recetaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // EJECUCIÓN
        recetaModel resultado = service.actualizar(1, nuevosDatos);

        // VERIFICACIÓN: Validamos que los datos antiguos hayan cambiado por los nuevos
        assertNotNull(resultado);
        assertEquals("REC-NUEVA", resultado.getCodigo());
        assertEquals("Amoxicilina 500mg", resultado.getMedicamentos());
    }

    @Test
    void actualizar_inexistente_lanzaRuntimeException() {
        // PREPARACIÓN: No se encuentra la receta con ID 99
        when(repository.findById(99)).thenReturn(Optional.empty());

        // EJECUCIÓN Y VERIFICACIÓN: Validamos que lance la excepción esperada y su mensaje descriptivo
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.actualizar(99, recetaEjemplo);
        });

        assertEquals("Receta no encontrada", exception.getMessage());
        verify(repository, never()).save(any()); // Nos aseguramos de que jamás se intente guardar nada
    }

    @Test
    void deleteReceta_ejecutaLlamadaEliminar() {
        // PREPARACIÓN: Indicamos que el método void no haga nada al llamarse
        doNothing().when(repository).deleteById(1);

        // EJECUCIÓN
        service.deleteReceta(1);

        // VERIFICACIÓN: Asegura que el servicio delegó correctamente la acción al repositorio
        verify(repository, times(1)).deleteById(1);
    }
}