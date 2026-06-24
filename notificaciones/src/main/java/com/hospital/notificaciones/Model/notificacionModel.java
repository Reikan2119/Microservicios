package com.hospital.notificaciones.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notificaciones")
// Describe la estructura de datos para la sección de componentes de Swagger
@Schema(description = "Entidad que representa el registro histórico y el estado de una notificación enviada al paciente")
public class notificacionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Se define como READ_ONLY para que Swagger no te obligue a digitar un ID ficticio al hacer un POST
    @Schema(description = "Identificador único correlativo de la notificación", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Column(nullable = false)
    // Indica el canal de comunicación ocupado
    @Schema(description = "El canal o medio por el cual se tomó contacto con el paciente", example = "Email", requiredMode = Schema.RequiredMode.REQUIRED)
    private String medioContacto;

    @Column(nullable = false)
    // Describe el cuerpo del recado
    @Schema(description = "Texto completo con el cuerpo del mensaje o alerta médica despachada", example = "Recordatorio: Examen de laboratorio agendado para mañana a las 8:00 AM.", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mensaje;
    
    @Column(nullable = false)
    // Detalla el ciclo de vida del mensaje
    @Schema(description = "Estado administrativo del envío de la notificación en el sistema", example = "Enviado", allowableValues = {"Pendiente", "Enviado", "Fallido"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String estado;

}