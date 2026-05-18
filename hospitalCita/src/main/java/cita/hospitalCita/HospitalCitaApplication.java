package cita.hospitalCita;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HospitalCitaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalCitaApplication.class, args);
	}

}
