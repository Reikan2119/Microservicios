package v1.inventario.modelo;

import java.time.LocalDate;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventario_items")
@Schema(description = "Entidad de persistencia que representa un artículo físico o insumo médico dentro del stock del hospital")
public class inventario_item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Usamos el accessMode moderno para ocultar el ID de los formularios POST de inserción
    @Schema(description = "Identificador único correlativo del ítem generado por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    // Código único para insumos
    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Código SKU único e invariable para identificar el insumo clínico", example = "GAZA-STERIL-05", maxLength = 50, requiredMode = Schema.RequiredMode.REQUIRED)
    private String sku;

    @Column(name = "nombre", nullable = false, length = 100)
    @Schema(description = "Nombre descriptivo oficial del material médico", example = "Gaza Estéril 10x10 cm", maxLength = 100, requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(name = "stock_actual", nullable = false)
    @Schema(description = "Número de unidades físicas actualmente disponibles en la bodega central", example = "500", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stockActual;

    @Column(name = "fecha_vencimiento_lote")
    @Schema(description = "Fecha de caducidad correspondiente al lote actual de insumos", example = "2026-12-31")
    private LocalDate fechaVencimientoLote;
}