package v1.inventario.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto de Transferencia de Datos (DTO) que expone el resumen del estado actual y vigencia de un insumo médico")
public class inventarioDTO {

    @Schema(description = "Identificador único del ítem en el almacén", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Código SKU único asignado al insumo clínico", example = "BISTURI-01")
    private String sku;

    @Schema(description = "Nombre descriptivo comercial del insumo médico", example = "Bisturí Quirúrgico Desechable Nro 15")
    private String nombre;

    @Schema(description = "Cantidad exacta de existencias disponibles en stock", example = "150")
    private Integer stockActual;

    @Schema(description = "Fecha límite de vigencia de las unidades pertenecientes al lote actual", example = "2026-12-31")
    private LocalDate fechaVencimientoLote;

}