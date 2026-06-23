package v1.Configuracion_de_Descuentos.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de transferencia de datos (DTO) que representa un beneficio o rebaja arancelaria aplicable a las cuentas médicas")
public class DTO_descuentos {

    @Schema(description = "Identificador único del registro de descuento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre descriptivo de la promoción, convenio o beneficio", example = "Convenio Adulto Mayor - Fonasa Plus")
    private String nombrePromocion;

    @Schema(description = "Porcentaje de rebaja que se aplicará sobre el total arancelario", example = "15.0")
    private Double porcentajeDescuento;

    @Schema(description = "Código alfanumérico interno requerido para autorizar y validar el beneficio en caja", example = "CONV-2026-AM")
    private String codigoAutorizacion;

    @Schema(description = "Estado operativo del descuento; determina si está disponible para ser aplicado", example = "true")
    private Boolean activo; 
}