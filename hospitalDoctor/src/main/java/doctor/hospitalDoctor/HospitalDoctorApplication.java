package doctor.hospitalDoctor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HospitalDoctorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalDoctorApplication.class, args);
	}

}
