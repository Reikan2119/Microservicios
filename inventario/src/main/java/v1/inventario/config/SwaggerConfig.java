package v1.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi(){

        return new OpenAPI()
            .info(new Info()
                .title("Microservicio gestion inventario")
                .version("1.2")
                .description("Documentacion de API que permite la gestion del inventario en el sistema de atencion medica")
                
    );


    }

}

