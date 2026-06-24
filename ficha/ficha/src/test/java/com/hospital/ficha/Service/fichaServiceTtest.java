package com.hospital.ficha.Service;

// Importaciones esenciales de JUnit para realizar aserciones y Mockito para simulaciones
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

import com.hospital.ficha.Model.fichaModel;
import com.hospital.ficha.Repository.fichaRepository;

// Habilitamos la extensión de Mockito para manejar de forma automática la inicialización de los Mocks
@ExtendWith(MockitoExtension.class)
public class fichaServiceTtest {

    // Simula la capa del repositorio sin conectarse a la base de datos real
    @Mock
    private fichaRepository repository;

    // Crea una instancia real del servicio e inyecta el repositorio simulado (Mock) dentro de él
    @InjectMocks
    private fichaService service;

    private fichaModel fichaEjemplo;

    // Reinicia y prepara un objeto base de ficha clínica antes de que corra cada test individual
    @BeforeEach
    void setUp() {
        fichaEjemplo = new fichaModel(1, "Control General", "Presión arterial estable", false, "Sano");
    }

    @Test
    void getAllFichas_retornaListaDeFichasCompleta() {
        // PREPARACIÓN: Creamos una lista ficticia y le ordenamos al repositorio mock que la devuelva
        List<fichaModel> lista = new ArrayList<>();
        lista.add(fichaEjemplo);
        when(repository.findAll()).thenReturn(lista);

        // EJECUCIÓN: Invocamos el método del servicio real
        List<fichaModel> resultado = service.getAllFichas();

        // VERIFICACIÓN: Comprobamos que la lista contenga los datos y que el mock haya sido consultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Control General", resultado.get(0).getExaminacion());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getFichaById_existente_retornaFichaCorrecta() {
        // PREPARACIÓN: Simulamos que al buscar el ID 1 se encuentra el objeto dentro de un Optional
        when(repository.findById(1)).thenReturn(Optional.of(fichaEjemplo));

        // EJECUCIÓN
        fichaModel resultado = service.getFichaById(1);

        // VERIFICACIÓN: Validamos que no sea nulo y que corresponda al ID consultado
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void getFichaById_inexistente_retornaNull() {
        // PREPARACIÓN: Simulamos que para el ID 99 la base de datos devuelve un vacío
        when(repository.findById(99)).thenReturn(Optional.empty());

        // EJECUCIÓN
        fichaModel resultado = service.getFichaById(99);

        // VERIFICACIÓN: El servicio de Diego está diseñado para retornar null mediante un .orElse(null)
        assertNull(resultado);
    }

    @Test
    void createFicha_guardaYRetornaLaNuevaFicha() {
        // PREPARACIÓN: Indicamos que al salvar cualquier entidad, retorne la ficha de ejemplo con su ID
        when(repository.save(any(fichaModel.class))).thenReturn(fichaEjemplo);

        // EJECUCIÓN
        fichaModel resultado = service.createFicha(fichaEjemplo);

        // VERIFICACIÓN: Confirmamos el guardado exitoso
        assertNotNull(resultado);
        assertEquals("Sano", resultado.getDiagnostico());
        verify(repository, times(1)).save(fichaEjemplo);
    }

    @Test
    void updateFicha_existente_modificaCamposYGuarda() {
        // PREPARACIÓN: Entrenamos al mock para que encuentre el registro original en la BD
        when(repository.findById(1)).thenReturn(Optional.of(fichaEjemplo));
        
        // Creamos una estructura con las nuevas modificaciones que simula enviar el frontend
        fichaModel datosNuevos = new fichaModel(null, "Examen Cardíaco", "Arritmia leve detectada", true, "Soplo Cardíaco");
        
        // Le indicamos al mock que al guardar la entidad modificada, nos retorne el mismo argumento ingresado
        when(repository.save(any(fichaModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // EJECUCIÓN
        fichaModel resultado = service.updateFicha(1, datosNuevos);

        // VERIFICACIÓN: Validamos que cada set del servicio haya reemplazado los valores antiguos de forma correcta
        assertNotNull(resultado);
        assertEquals("Examen Cardíaco", resultado.getExaminacion());
        assertEquals("Arritmia leve detectada", resultado.getNotas());
        assertTrue(resultado.getRiesgo());
        assertEquals("Soplo Cardíaco", resultado.getDiagnostico());
    }

    @Test
    void updateFicha_inexistente_retornaNullSinGuardar() {
        // PREPARACIÓN: Indicamos que la ficha no existe en la base de datos ficticia
        when(repository.findById(99)).thenReturn(Optional.empty());
        fichaModel datosNuevos = new fichaModel(null, "Test", "Test", false, "Test");

        // EJECUCIÓN
        fichaModel resultado = service.updateFicha(99, datosNuevos);

        // VERIFICACIÓN: Al no encontrar la ficha original, el servicio de Diego retorna null por defecto
        assertNull(resultado);
        // Garantizamos bajo contrato de pruebas que la base de datos jamás ejecutó un comando de guardado
        verify(repository, never()).save(any(fichaModel.class));
    }
}