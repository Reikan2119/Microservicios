package  cita.hospitalCita.dto;

import lombok.*;

// 🔥 DTO PARA TIPO DE ATENCIÓN
// Representa un dato interno (misma BD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAtencionDTO {

    private Integer id;
    private String nombre;
}