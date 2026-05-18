/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.caja.DTO;

import lombok.Data;

/**
 *
 * @author diego
 */

@Data
public class DTOcaja {
    private Long id;
    private String nombrePromocion;
    private Double porcentajeDescuento;
    private String codigoAutorizacion;
    private Boolean activo;
}
