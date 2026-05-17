package  cita.hospitalCita.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cita.hospitalCita.dto.PacienteDTO;


// 🔥 Cliente Feign para comunicarse con el microservicio de Pacientes
@FeignClient(name = "pacienteMS", url = "http://localhost:8081")
public interface PacienteClient {

    //  Este método representa una llamada HTTP GET
    //
    //  Equivale a:
    // GET http://localhost:8081/api/v1/pacientes/{id}
    //
    //  Retorna un PacienteDTO con los datos del paciente
    @GetMapping("/api/v1/pacientes/dto/{id}")
    PacienteDTO obtenerPaciente(@PathVariable("id") Integer id);
}