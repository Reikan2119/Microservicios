package com.hospital.notificaciones.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.notificaciones.Model.notificacionModel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/notificaciones")
// Agrupa los endpoints bajo la sección "Notificaciones" en la interfaz de Swagger
@Tag(name = "Notificaciones", description = "Operaciones para la gestión y envío de alertas a pacientes")
class notificacionesController {

    @Autowired
    private com.hospital.notificaciones.Service.notificacionesService notificacionesService;

    // GET: Listar todas las notificaciones
    @GetMapping
    @Operation(summary = "Obtener historial de notificaciones", description = "Retorna una lista completa con todas las notificaciones emitidas y su estado actual")
    public List<notificacionModel> obtenerTodas() {
        return notificacionesService.obtenerTodas();
    }

    // POST: Enviar una notificación
    @PostMapping
    @Operation(
        summary = "Enviar una nueva notificación", 
        description = "Registra y despacha una alerta utilizando parámetros de consulta directos (Query Parameters/Form Data)"
    )
    public void enviarNotificacion(
        // @Parameter documenta las variables individuales que no son un JSON completo
        @Parameter(description = "Canal de envío del mensaje", example = "WhatsApp", required = true) 
        String medioContacto, 
        
        @Parameter(description = "Contenido de la alerta médica", example = "Su cita ha sido agendada para mañana.", required = true) 
        String mensaje
    ) {
        notificacionesService.enviarNotificacion(medioContacto, mensaje);
    }

}