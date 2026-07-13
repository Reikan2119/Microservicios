package v1.Configuracion_de_Descuentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ConfiguracionDeDescuentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfiguracionDeDescuentosApplication.class, args);
	}

}
