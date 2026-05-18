/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.caja.Servicio;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import v1.caja.DTO.DTOcaja;
import v1.caja.Modelo.ModeloCaja;
import  v1.caja.repositorio.Repositorio_caja;
/**
 *
 * @author diego
 */

@Service
public class Servicio_caja {
   @Autowired
    private Repositorio_caja cajaRepository;

    // Inyectamos el cliente HTTP que configuramos en el Paso 1
    @Autowired
    private RestTemplate restTemplate;

    // URL base de red local donde corre el microservicio de descuentos (Ejemplo: puerto 8082)
    private final String URL_MICROSERVICIO_DESCUENTOS = "http://localhost:8085/api/descuentos/validar/";

    public List<ModeloCaja> listar() {
        return cajaRepository.findAll();
    }

    public ModeloCaja buscarPorId(Long id) {
        return cajaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de caja no encontrado"));
    }

    public List<ModeloCaja> buscarPorCajero(Long idCajero) {
        return cajaRepository.findByIdCajero(idCajero);
    }

    public ModeloCaja guardar(ModeloCaja entrada) {
        // 1. Calculamos el total de dinero bruto recibido en la caja presencial
        Double totalBruto = entrada.getMontoEfectivo() + 
                            entrada.getMontoDebito() + 
                            entrada.getMontoCredito() + 
                            entrada.getMontoTransferencia();
        
        // 2. Comunicación entre Microservicios
        // Si la secretaria escribió un código de descuento, se procede a consultar al otro sistema
        if (entrada.getCodigoDescuentoAplicado() != null && !entrada.getCodigoDescuentoAplicado().trim().isEmpty()) {
            try {
                // Construimos la URL dinámica: "http://localhost:8085/api/descuentos/validar/ADULTO-MAYOR-15"
                String urlCompleta = URL_MICROSERVICIO_DESCUENTOS + entrada.getCodigoDescuentoAplicado().trim();
                
                // Realizamos la llamada HTTP GET síncrona
                DTOcaja descuento = restTemplate.getForObject(urlCompleta, DTOcaja.class);
                
                // Si el descuento existe y está vigente, alteramos matemáticamente el cobro
                if (descuento != null && descuento.getActivo()) {
                    Double rebaja = totalBruto * (descuento.getPorcentajeDescuento() / 100.0);
                    totalBruto = totalBruto - rebaja;
                    System.out.println("-> Interacción Exitosa: Descuento aplicado '" + descuento.getNombrePromocion() + "' (-$" + rebaja + ")");
                }
            } catch (Exception e) {
                // Capturamos cualquier error (por ejemplo, si el microservicio de descuentos arroja un 404 o 500 porque el código no existe)
                throw new RuntimeException("Error en la transacción: El código de descuento ingresado no es válido o está vencido.");
            }
        }
        
        // 3. Asignamos el valor final calculado neto tras la consulta del microservicio
        entrada.setTotalVentasDiarias(totalBruto);
        
        if (entrada.getId() == null) {
            entrada.setFechaHoraCierre(LocalDateTime.now());
        }
        
        return cajaRepository.save(entrada);
    }

    public void eliminar(Long id) {
        if (!cajaRepository.existsById(id)) {
            throw new RuntimeException("El registro de caja especificado no existe");
        }
        cajaRepository.deleteById(id);
    }
}
