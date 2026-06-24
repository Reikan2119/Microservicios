package com.hospital.notificaciones.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.notificaciones.Model.notificacionModel;
import com.hospital.notificaciones.Repository.notificacionRepository;

@Service
public class notificacionesService {

    @Autowired
    private notificacionRepository notificacionRepository;

    public List<notificacionModel> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public void enviarNotificacion(String medioContacto, String mensaje) {
        notificacionModel notificacion = new notificacionModel();
        notificacion.setMedioContacto(medioContacto);
        notificacion.setMensaje(mensaje);
        notificacion.setEstado("Enviado");
        notificacionRepository.save(notificacion);
    }

}
