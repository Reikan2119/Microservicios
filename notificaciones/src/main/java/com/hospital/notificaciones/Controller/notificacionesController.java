package com.hospital.notificaciones.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notificaciones")
class notificacionesController {

    @Autowired
    private com.hospital.notificaciones.Service.notificacionesService notificacionesService;

    @PostMapping
    public void enviarNotificacion(String medioContacto, String mensaje) {
        notificacionesService.enviarNotificacion(medioContacto, mensaje);
    }

}
