package mx.com.villavicencio.almacen.system.pedidos.detalle.dto;

import java.math.BigDecimal;

/**
 *
 * @author Gabriel J
 */
public class DtoDetallePedido {

    private Integer idDetallePedido;
    private Integer idProducto;
    private String nombreProducto;
    private BigDecimal costoUnitario;
    private Integer cantidadPiezas;
    private String peso;
    private BigDecimal comision;
    private Integer opcion;
    private BigDecimal pesoMinimo;
    private BigDecimal pesoMaximo;
    private Boolean isMuestra;
    private Boolean isAgotado;
    private String observacion;

    public Integer getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public Integer getCantidadPiezas() {
        return cantidadPiezas;
    }

    public void setCantidadPiezas(Integer cantidadPiezas) {
        this.cantidadPiezas = cantidadPiezas;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public BigDecimal getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(BigDecimal pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public BigDecimal getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(BigDecimal pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    public Boolean getIsMuestra() {
        return isMuestra;
    }

    public void setIsMuestra(Boolean isMuestra) {
        this.isMuestra = isMuestra;
    }

    public Boolean getIsAgotado() {
        return isAgotado;
    }

    public void setIsAgotado(Boolean isAgotado) {
        this.isAgotado = isAgotado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
