package cita.hospitalCita.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cita.hospitalCita.service.AtencionService;

import cita.hospitalCita.client.DoctorClient;
import cita.hospitalCita.client.PacienteClient;
import cita.hospitalCita.dto.AtencionDetalleDTO;
import cita.hospitalCita.dto.DoctorDTO;
import cita.hospitalCita.dto.PacienteDTO;
import cita.hospitalCita.model.Atencion;
import cita.hospitalCita.model.TipoAtencion;
import cita.hospitalCita.repository.AtencionRepository;

@ExtendWith(MockitoExtension.class)
public class AtencionControllerTest {

    @Mock
    private AtencionRepository repository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private DoctorClient doctorClient;

    @InjectMocks
    private AtencionService service; 

    private Atencion atencionEjemplo;
    private TipoAtencion tipoAtencionEjemplo;
    private PacienteDTO pacienteDTOEjemplo;
    private DoctorDTO doctorDTOEjemplo;

    @BeforeEach
    void setUp() {
        tipoAtencionEjemplo = new TipoAtencion(1, "Consulta General");
        atencionEjemplo = new Atencion(1, new Date(), "Resfrío común", 1, 1, tipoAtencionEjemplo);
        
        // 💡 2. SOLUCIÓN CON SETTERS (Evita errores si tus DTOs no tienen @AllArgsConstructor)
        pacienteDTOEjemplo = new PacienteDTO();
        pacienteDTOEjemplo.setId(1);
        pacienteDTOEjemplo.setNombrePaciente("Juan Perez");
        pacienteDTOEjemplo.setNombreContacto("Juanito");
        pacienteDTOEjemplo.setEmail("contacto@email.com");
        pacienteDTOEjemplo.setTelefono("+56912345678");

        doctorDTOEjemplo = new DoctorDTO();
        doctorDTOEjemplo.setId(1);
        doctorDTOEjemplo.setNombreDoctor("Cristhyan Silva");
        doctorDTOEjemplo.setEspecialidad("Cardiologia");
    }

    @Test
    void listar_retornaElementos() {
        // ARRANGE
        List<Atencion> lista = new ArrayList<>();
        lista.add(atencionEjemplo);
        when(repository.findAll()).thenReturn(lista);

        // ACT
        List<Atencion> resultado = service.listar();

        // ASSERT
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(atencionEjemplo));

        // ACT
        Atencion resultado = service.buscarPorId(1);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Resfrío común", resultado.getDiagnostico());
    }

    @Test
    void buscarPorId_noEncontrado_lanzaException() {
        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99);
        });
        assertEquals("Atención no encontrada", exception.getMessage());
    }

    @Test
    void guardar_exitoso() {
        // ARRANGE
        when(repository.save(atencionEjemplo)).thenReturn(atencionEjemplo);

        // ACT
        Atencion resultado = service.guardar(atencionEjemplo);

        // ASSERT
        assertNotNull(resultado);
        verify(repository, times(1)).save(atencionEjemplo);
    }

    @Test
    void obtenerDetalle_exitoso_uneMicroservicios() {
        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(atencionEjemplo));
        when(pacienteClient.obtenerPaciente(1)).thenReturn(pacienteDTOEjemplo);
        when(doctorClient.obtenerDoctor(1)).thenReturn(doctorDTOEjemplo);

        // ACT
        AtencionDetalleDTO detalle = service.obtenerDetalle(1);

        // ASSERT
        assertNotNull(detalle);
        assertEquals(1, detalle.getId());
        assertEquals("Juan Perez", detalle.getPaciente().getNombrePaciente());
        assertEquals("Cristhyan Silva", detalle.getDoctor().getNombreDoctor());
        assertEquals("Consulta General", detalle.getTipoAtencion().getNombre());
    }

    @Test
    void obtenerDetalle_pacienteNulo_lanzaException() {
        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(atencionEjemplo));
        when(pacienteClient.obtenerPaciente(1)).thenReturn(null);

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.obtenerDetalle(1);
        });
        assertEquals("Paciente no encontrado", exception.getMessage());
    }
}