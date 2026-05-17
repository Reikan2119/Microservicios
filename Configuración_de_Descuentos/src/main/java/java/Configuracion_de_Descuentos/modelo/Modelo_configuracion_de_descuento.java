/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package java.Configuracion_de_Descuentos.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author diego
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "descuentos_configuracion")
public class Modelo_configuracion_de_descuento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "Nombre_Promocion", nullable = false, length = 150)
    private String nombrePromocion;

    @Column(name = "Porcentaje_Descuento", nullable = false)
    private Double porcentajeDescuento;

    @Column(name = "Codigo_Autorizacion", nullable = false, unique = true, length = 50)
    private String codigoAutorizacion;

    @Column(name = "Activo", nullable = false)
    private Boolean activo;
    

}
