package  cita.hospitalCita.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cita.hospitalCita.dto.DoctorDTO;



@FeignClient(name = "doctorMS", url = "http://localhost:8082")
public interface DoctorClient {

    
   @GetMapping("/api/v1/doctores/dto/{id}")
    DoctorDTO obtenerDoctor(@PathVariable("id") Integer id);
}