package com.hospital.recetas.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ficha", url = "http://localhost:8087/api/v1/fichas")
public interface recetaClient {
    @GetMapping("/api/v1/recetas")
    String getFichaById();

}
