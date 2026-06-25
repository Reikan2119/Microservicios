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

import io.swagger.v3.oas.annotations.media.Schema;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ingresos_caja")
@Schema(description = "Entidad de persistencia que registra el balance, arqueo y cierre contable de los ingresos de una caja del hospital")
public class ModeloCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único correlativo del registro de caja", example = "105", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "total_ventas_diarias", nullable = false)
    @Schema(description = "Suma total de los ingresos percibidos durante el turno o día", example = "1250500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double totalVentasDiarias;

    @Column(name = "monto_efectivo", nullable = false)
    @Schema(description = "Total recaudado físicamente en papel moneda (efectivo)", example = "350000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double montoEfectivo;

    @Column(name = "monto_debito", nullable = false)
    @Schema(description = "Total recaudado a través de transacciones con tarjeta de débito", example = "450000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double montoDebito;

    @Column(name = "monto_credito", nullable = false)
    @Schema(description = "Total recaudado a través de transacciones con tarjeta de crédito", example = "300000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double montoCredito;

    @Column(name = "monto_transferencia", nullable = false)
    @Schema(description = "Total recibido mediante transferencias bancarias electrónicas directas", example = "150500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double montoTransferencia;

    @Column(name = "codigo_descuento_aplicado", length = 50)
    @Schema(description = "Código de beneficio arancelario utilizado (Conecta lógicamente con el microservicio CONFIGURACIONDESCUENTOS)", example = "CRUZ-BLANCA-2026")
    private String codigoDescuentoAplicado;

    @Column(name = "fecha_hora_cierre", nullable = false)
    @Schema(description = "Fecha y hora exacta en la que el cajero efectuó el cierre técnico de la caja", example = "2026-06-22T20:00:00.000Z", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime fechaHoraCierre;

    @Column(name = "id_cajero", nullable = false)
    @Schema(description = "ID de referencia del funcionario responsable de la caja", example = "12", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long idCajero;
}