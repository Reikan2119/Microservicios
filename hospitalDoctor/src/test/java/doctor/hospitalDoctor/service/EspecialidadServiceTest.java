package doctor.hospitalDoctor.service;

import static org.junit.jupiter.api.Assertions.*;
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

import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.repository.EspecialidadRepository;

@ExtendWith(MockitoExtension.class)
public class EspecialidadServiceTest {

    @Mock
    private EspecialidadRepository repository;

    @InjectMocks
    private EspecialidadService service;

    private Especialidad especialidadEjemplo;

    @BeforeEach
    void setUp() {
        especialidadEjemplo = new Especialidad();
        especialidadEjemplo.setId(1);
        especialidadEjemplo.setNombre("Cardiologia");
    }

    @Test
    void listar_retornaListaConElementos() {
        // ARRANGE
        List<Especialidad> lista = new ArrayList<>();
        lista.add(especialidadEjemplo);
        when(repository.findAll()).thenReturn(lista);

        // ACT
        List<Especialidad> resultado = service.listar();

        // ASSERT
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Cardiologia", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(especialidadEjemplo));

        // ACT
        Especialidad resultado = service.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Cardiologia", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado_lanzaException() {
        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99);
        });
        assertEquals("Especialidad no encontrada con el ID: 99", exception.getMessage());
    }

    @Test
    void guardar_exitoso() {
        // ARRANGE
        when(repository.save(especialidadEjemplo)).thenReturn(especialidadEjemplo);

        // ACT
        Especialidad resultado = service.guardar(especialidadEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Cardiologia", resultado.getNombre());
        verify(repository, times(1)).save(especialidadEjemplo);
    }

    @Test
    void actualizar_exitoso() {
        // ARRANGE
        Especialidad datosNuevos = new Especialidad();
        datosNuevos.setNombre("Neurologia");

        when(repository.findById(1)).thenReturn(Optional.of(especialidadEjemplo));
        // any() intercepta el objeto que se va a guardar y simula su retorno
        when(repository.save(any(Especialidad.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Especialidad resultado = service.actualizar(1, datosNuevos);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Neurologia", resultado.getNombre());
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE - Para tu método, eliminar primero busca por id
        when(repository.findById(1)).thenReturn(Optional.of(especialidadEjemplo));
        doNothing().when(repository).delete(especialidadEjemplo);

        // ACT & ASSERT
        assertDoesNotThrow(() -> service.eliminar(1));
        verify(repository, times(1)).delete(especialidadEjemplo);
    }
}