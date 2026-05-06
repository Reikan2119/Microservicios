package  cita.hospitalCita.dto;
import lombok.*;

// 🔥 DTO PARA PACIENTE
// Representa los datos que obtenemos desde el microservicio de pacientes
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {

    // 🔹 ID del paciente
    private Integer id;

    // 🔹 Nombre del paciente
    private String nombre;

    // 🔹 Apellido del paciente
    private String apellido;

    // ⚠️ IMPORTANTE:
    // Solo incluimos lo necesario
    //
    // 👉 NO incluimos:
    // - Dirección
    // - Teléfono
    // - Email
    //
    // 🔥 ¿Por qué?
    // Porque este microservicio NO necesita esos datos
}