package com.hospital.config.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ficha_service", url = "http://localhost:8087")
public interface fichaClient {
@GetMapping("/api/v1/fichas")
    public java.util.List<com.hospital.ficha.Model.fichaModel> getAllFichas();

}
