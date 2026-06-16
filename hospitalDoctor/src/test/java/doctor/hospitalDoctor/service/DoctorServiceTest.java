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

import doctor.hospitalDoctor.model.Doctor;
import doctor.hospitalDoctor.model.Especialidad;
import doctor.hospitalDoctor.repository.DoctorRepository;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock 
    private DoctorRepository doctorRepository;

    @InjectMocks 
    private DoctorService doctorService;

    private Doctor doctorEjemplo;
    private Especialidad especialidadEjemplo;

    @BeforeEach
    void setUp() {
        especialidadEjemplo = new Especialidad();
        especialidadEjemplo.setId(1);
        especialidadEjemplo.setNombre("Cardiologia");

        doctorEjemplo = new Doctor();
        doctorEjemplo.setId(1);
        doctorEjemplo.setNombre("Mapache");
        doctorEjemplo.setApellido("loco");
        doctorEjemplo.setEspecialidad(especialidadEjemplo);
    }
    
    @Test
    void listar_retornaListaConElementos() {
        // ARRANGE
        List<Doctor> lista = new ArrayList<>();
        lista.add(doctorEjemplo);
        when(doctorRepository.findAll()).thenReturn(lista);

        // ACT
        List<Doctor> resultado = doctorService.listar();

        // ASSERT
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctorEjemplo));

        // ACT
        Doctor resultado = doctorService.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Mapache", resultado.getNombre()); // Corregido case-sensitive
    }

    @Test
    void buscarPorId_noEncontrado() {
        // ARRANGE
        when(doctorRepository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.buscarPorId(99);
        });
        assertEquals("Doctor no encontrado", exception.getMessage());
    }

    @Test
    void guardar() {
        // ARRANGE
        when(doctorRepository.save(doctorEjemplo)).thenReturn(doctorEjemplo);

        // ACT
        Doctor resultado = doctorService.guardar(doctorEjemplo);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Mapache", resultado.getNombre());
    }

    @Test
    void eliminar_exitoso() {
        // ARRANGE
        when(doctorRepository.existsById(1)).thenReturn(true);

        // ACT & ASSERT
        assertDoesNotThrow(() -> doctorService.eliminar(1));
        verify(doctorRepository, times(1)).deleteById(1); 
    }

    @Test
    void eliminar_noExiste_lanzaException() {
        // ARRANGE
        when(doctorRepository.existsById(99)).thenReturn(false);

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.eliminar(99);
        });
        assertEquals("Doctor no existe", exception.getMessage());
    }

    @Test
    void actualizar_exitoso() {
        // ARRANGE
        Doctor datosNuevos = new Doctor();
        datosNuevos.setNombre("NuevoNombre");
        datosNuevos.setApellido("NuevoApellido");
        datosNuevos.setEspecialidad(especialidadEjemplo);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctorEjemplo));
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        Doctor resultado = doctorService.actualizar(1, datosNuevos);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("NuevoNombre", resultado.getNombre());
        assertEquals("NuevoApellido", resultado.getApellido());
    }
}