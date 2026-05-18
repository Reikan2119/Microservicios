/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.Configuracion_de_Descuentos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author diego
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DTO_descuentos {
    private Long id;
    private String nombrePromocion;
    private Double porcentajeDescuento;
    private String codigoAutorizacion;
    private Boolean activo; 
}
