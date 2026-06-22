package pacientes.hospitalPaciente.Service;

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

import pacientes.hospitalPaciente.Model.Contacto;
import pacientes.hospitalPaciente.Model.Paciente;
import pacientes.hospitalPaciente.Repository.ContactoRepository;
import pacientes.hospitalPaciente.Repository.PacienteRepository;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private ContactoRepository contactoRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente pacienteEjemplo;
    private Contacto contactoEjemplo;

    @BeforeEach
    void setUp() {
        contactoEjemplo = new Contacto(1, "Juanito", "+56912345678", "contacto@email.com");
        pacienteEjemplo = new Paciente(1, "12345678-9", "Juan", "Perez", 30, contactoEjemplo);
    }

    @Test
    void listarPacientes_retornaElementos() {
        List<Paciente> lista = new ArrayList<>();
        lista.add(pacienteEjemplo);
        when(pacienteRepository.findAll()).thenReturn(lista);

        List<Paciente> resultado = pacienteService.listarPacientes();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorRut_encontrado() {
        when(pacienteRepository.findByRut("12345678-9")).thenReturn(Optional.of(pacienteEjemplo));

        Paciente resultado = pacienteService.buscarPorRut("12345678-9");

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void guardar_conContactoNuevo_guardaContactoYPaciente() {
        // Configuramos un contacto sin ID (nuevo)
        Contacto contactoNuevo = new Contacto(null, "Juanito", "+56912345678", "contacto@email.com");
        Paciente pacienteNuevo = new Paciente(null, "12345678-9", "Juan", "Perez", 30, contactoNuevo);

        when(contactoRepository.save(contactoNuevo)).thenReturn(contactoEjemplo);
        when(pacienteRepository.save(pacienteNuevo)).thenReturn(pacienteEjemplo);

        Paciente resultado = pacienteService.guardar(pacienteNuevo);

        assertNotNull(resultado);
        verify(contactoRepository, times(1)).save(contactoNuevo);
        verify(pacienteRepository, times(1)).save(pacienteNuevo);
    }

    @Test
    void guardar_sinContacto_lanzaException() {
        Paciente pacienteInvalido = new Paciente(null, "12345678-9", "Juan", "Perez", 30, null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.guardar(pacienteInvalido);
        });

        assertEquals("No se puede registrar un paciente sin un contacto asociado", exception.getMessage());
    }

    @Test
    void eliminar_noExiste_lanzaException() {
        when(pacienteRepository.existsById(99)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pacienteService.eliminar(99);
        });

        assertEquals("Paciente no existe", exception.getMessage());
    }
}