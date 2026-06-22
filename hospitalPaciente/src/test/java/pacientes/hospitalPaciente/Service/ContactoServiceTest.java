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
import pacientes.hospitalPaciente.Repository.ContactoRepository;

@ExtendWith(MockitoExtension.class)
public class ContactoServiceTest {

    @Mock
    private ContactoRepository repository;

    @InjectMocks
    private ContactoService service;

    private Contacto contactoEjemplo;

    @BeforeEach
    void setUp() {
        contactoEjemplo = new Contacto();
        contactoEjemplo.setId(1);
        contactoEjemplo.setNombre("Juanito");
        contactoEjemplo.setTelefono("+56912345678");
        contactoEjemplo.setEmail("contacto@email.com");
    }

    @Test
    void listar_retornaElementos() {
        List<Contacto> lista = new ArrayList<>();
        lista.add(contactoEjemplo);
        when(repository.findAll()).thenReturn(lista);

        List<Contacto> resultado = service.listar();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(contactoEjemplo));

        Contacto resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Juanito", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado_lanzaException() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99);
        });
        assertTrue(exception.getMessage().contains("Contacto no encontrado con el ID: 99"));
    }

    @Test
    void guardar_exitoso() {
        when(repository.save(contactoEjemplo)).thenReturn(contactoEjemplo);

        Contacto resultado = service.guardar(contactoEjemplo);

        assertNotNull(resultado);
        verify(repository, times(1)).save(contactoEjemplo);
    }

    @Test
    void actualizar_exitoso() {
        Contacto datosNuevos = new Contacto();
        datosNuevos.setTelefono("+56999999999");
        datosNuevos.setEmail("nuevo@email.com");

        when(repository.findById(1)).thenReturn(Optional.of(contactoEjemplo));
        when(repository.save(any(Contacto.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Contacto resultado = service.actualizar(1, datosNuevos);

        assertNotNull(resultado);
        assertEquals("+56999999999", resultado.getTelefono());
        assertEquals("nuevo@email.com", resultado.getEmail());
    }
}