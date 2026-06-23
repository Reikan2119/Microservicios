package v1.Configuracion_de_Descuentos.modelo;

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
@Table(name = "descuentos_configuracion")
@Schema(description = "Entidad de persistencia que almacena la configuración de campañas promocionales y convenios arancelarios de la clínica")
public class Modelo_configuracion_de_descuento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Schema(description = "Identificador único asignado automáticamente por la base de datos", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "Nombre_Promocion", nullable = false, length = 150)
    @Schema(description = "Nombre oficial de la campaña o convenio (Máximo 150 caracteres)", example = "Convenio Isapre CruzBlanca Costamed", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombrePromocion;

    @Column(name = "Porcentaje_Descuento", nullable = false)
    @Schema(description = "Valor decimal que representa la tasa de descuento aplicable", example = "20.0", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double porcentajeDescuento;

    @Column(name = "Codigo_Autorizacion", nullable = false, unique = true, length = 50)
    @Schema(description = "Código alfanumérico único para aplicar el beneficio en caja (Máximo 50 caracteres, no puede repetirse)", example = "CRUZ-BLANCA-2026", requiredMode = Schema.RequiredMode.REQUIRED)
    private String codigoAutorizacion;

    @Column(name = "Activo", nullable = false)
    @Schema(description = "Estado de vigencia del beneficio (true = Vigente y aplicable, false = Caducado/Pausado)", example = "true", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean activo;
}