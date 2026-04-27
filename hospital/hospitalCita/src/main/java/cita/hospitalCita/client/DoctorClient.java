package  cita.hospitalCita.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cita.hospitalCita.dto.DoctorDTO;


//  @FeignClient:
// Esta anotación le dice a Spring que esta interfaz
// representa un cliente HTTP hacia otro microservicio.
//
// name → nombre lógico del microservicio
// url → dirección donde está corriendo el MS de Doctores
@FeignClient(name = "doctorMS", url = "http://localhost:8082")
public interface DoctorClient {

    //  Este método representa una llamada HTTP GET
    // hacia el microservicio de doctores.
    //
    //  Equivale a hacer esto manualmente:
    // GET http://localhost:8082/api/v1/doctores/{id}
    //
    //  @PathVariable("id"):
    // Se usa para reemplazar {id} en la URL
   @GetMapping("/api/v1/doctores/dto/{id}")
    DoctorDTO obtenerDoctor(@PathVariable("id") Integer id);
}