package  cita.hospitalCita.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cita.hospitalCita.dto.PacienteDTO;



@FeignClient(name = "pacienteMS", url = "http://localhost:8081")
public interface PacienteClient {

    
    @GetMapping("/api/v1/pacientes/dto/{id}")
    PacienteDTO obtenerPaciente(@PathVariable("id") Integer id);
}