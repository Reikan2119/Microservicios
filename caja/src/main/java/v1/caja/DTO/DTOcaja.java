package v1.caja.DTO;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Objeto de transferencia de datos (DTO) que replica la información del descuento dentro del módulo de finanzas para aplicar rebajas en el cobro")
public class DTOcaja {

    @Schema(description = "Identificador único de la promoción en el sistema", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nombre del convenio o promoción detectada", example = "Convenio Isapre CruzBlanca Costamed")
    private String nombrePromocion;

    @Schema(description = "Porcentaje de rebaja arancelaria que se restará al total de la atención", example = "20.0")
    private Double porcentajeDescuento;

    @Schema(description = "Código de validación necesario para aplicar la promoción en caja", example = "CRUZ-BLANCA-2026")
    private String codigoAutorizacion;

    @Schema(description = "Estado actual de vigencia del descuento", example = "true")
    private Boolean activo;
}