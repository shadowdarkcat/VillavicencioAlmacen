package mx.com.villavicencio.almacen.system.pedidos.pedido.dto;

import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class DtoPedido {

    private Integer idPedido;
    private Date fecha;
    private String folio;
    private String nombreVendedor;
    private Character statusAlmacen;
    private Collection<DtoDetallePedido> detalles;
    private DtoCliente cliente;
    private DtoVendedor vendedor;
    private DtoDetallePedido detalle;
    private Integer opcion;
    private String notaPedido;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public Character getStatusAlmacen() {
        return statusAlmacen;
    }

    public void setStatusAlmacen(Character statusAlmacen) {
        this.statusAlmacen = statusAlmacen;
    }

    public Collection<DtoDetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DtoDetallePedido> detalles) {
        this.detalles = detalles;
    }

    public DtoCliente getCliente() {
        return cliente;
    }

    public void setCliente(DtoCliente cliente) {
        this.cliente = cliente;
    }

    public DtoVendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(DtoVendedor vendedor) {
        this.vendedor = vendedor;
    }

    public DtoDetallePedido getDetalle() {
        return detalle;
    }

    public void setDetalle(DtoDetallePedido detalle) {
        this.detalle = detalle;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public String getNotaPedido() {
        return notaPedido;
    }

    public void setNotaPedido(String notaPedido) {
        this.notaPedido = notaPedido;
    }
}
