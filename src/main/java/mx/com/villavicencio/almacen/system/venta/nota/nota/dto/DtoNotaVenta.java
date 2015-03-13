package mx.com.villavicencio.almacen.system.venta.nota.nota.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class DtoNotaVenta {

    private Integer idNotaVenta;
    private String folio;
    private Date fecha;
    private String nombreCliente;
    private String direccion;
    private String rfc;
    private Collection<DtoDetalleNotaVenta> detalles;
    private BigDecimal total;
    private String cantidadLetra;
    private String leyenda;
    private String observaciones;
    private DtoPedido pedido;
    private Character statusNotaVenta;
    private Integer opcion;
    
    public Integer getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(Integer idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public Collection<DtoDetalleNotaVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DtoDetalleNotaVenta> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCantidadLetra() {
        return cantidadLetra;
    }

    public void setCantidadLetra(String cantidadLetra) {
        this.cantidadLetra = cantidadLetra;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public DtoPedido getPedido() {
        return pedido;
    }

    public void setPedido(DtoPedido pedido) {
        this.pedido = pedido;
    }
    
    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Character getStatusNotaVenta() {
        return statusNotaVenta;
    }

    public void setStatusNotaVenta(Character statusNotaVenta) {
        this.statusNotaVenta = statusNotaVenta;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }
}
