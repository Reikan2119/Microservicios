/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package v1.caja.Modelo;

import java.time.LocalDateTime;

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
@Table(name = "ingresos_caja")
public class ModeloCaja {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_ventas_diarias", nullable = false)
    private Double totalVentasDiarias;

    @Column(name = "monto_efectivo", nullable = false)
    private Double montoEfectivo;

    @Column(name = "monto_debito", nullable = false)
    private Double montoDebito;

    @Column(name = "monto_credito", nullable = false)
    private Double montoCredito;

    @Column(name = "monto_transferencia", nullable = false)
    private Double montoTransferencia;

    @Column(name = "codigo_descuento_aplicado", length = 50)
    private String codigoDescuentoAplicado;

    @Column(name = "fecha_hora_cierre", nullable = false)
    private LocalDateTime fechaHoraCierre;

    @Column(name = "id_cajero", nullable = false)
    private Long idCajero;
}
