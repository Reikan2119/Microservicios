package com.hospital.notificaciones.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "notificaciones_service", url = "http://localhost:8088/api/v1/notificaciones")
public interface notificacionesClient {
    @GetMapping("/api/v1/notificaciones")
    String obtenerNotificaciones();
}
