package com.hospital.notificaciones.Service;

// Importaciones para las aserciones de JUnit y las funciones de simulación de Mockito
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hospital.notificaciones.Model.notificacionModel;
import com.hospital.notificaciones.Repository.notificacionRepository;

// Habilitamos Mockito para gestionar el ciclo de vida de los mocks en JUnit 5
@ExtendWith(MockitoExtension.class)
public class notificacionesServiceTest {

    // Simula la capa de acceso a datos sin escribir en una base de datos real
    @Mock
    private notificacionRepository repository;

    // Crea la instancia real del servicio e inyecta el repositorio simulado en él
    @InjectMocks
    private notificacionesService service;

    private notificacionModel notificacionEjemplo;

    // Inicializa un objeto base antes de cada caso de prueba individual
    @BeforeEach
    void setUp() {
        notificacionEjemplo = new notificacionModel(1, "Email", "Su cita médica está confirmada.", "Enviado");
    }

    @Test
    void obtenerTodas_retornaListaCompletaDeNotificaciones() {
        // PREPARACIÓN: Creamos una lista ficticia con nuestro objeto y entrenamos al mock
        List<notificacionModel> lista = new ArrayList<>();
        lista.add(notificacionEjemplo);
        when(repository.findAll()).thenReturn(lista);

        // EJECUCIÓN: Llamamos al método bajo análisis
        List<notificacionModel> resultado = service.obtenerTodas();

        // VERIFICACIÓN: Validamos que la lista contenga los datos del repositorio simulado
        assertNotNull(resultado); // Comprueba que la lista no sea un valor nulo
        assertEquals(1, resultado.size()); // Asegura que tenga exactamente 1 elemento
        assertEquals("Email", resultado.get(0).getMedioContacto()); // Confirma el valor del primer registro
        verify(repository, times(1)).findAll(); // Certifica que se consultó al repositorio exactamente una vez
    }

    @Test
    void enviarNotificacion_instanciaYGuardaCorrectamenteElModelo() {
        // PREPARACIÓN: Capturamos el objeto que el servicio cree internamente al usar .save()
        // Indicamos que cuando se guarde cualquier objeto notificacionModel, simplemente lo devuelva
        when(repository.save(any(notificacionModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // EJECUCIÓN: Pasamos los dos String planos que recibe el método de Diego
        service.enviarNotificacion("SMS", "Recordatorio de ayuno para mañana.");

        // VERIFICACIÓN: Verificamos que el repositorio recibió la llamada de guardado (.save)
        // verify con 'any' nos ayuda a confirmar que la lógica interna transformó los strings en una entidad válida
        verify(repository, times(1)).save(any(notificacionModel.class));
    }
}