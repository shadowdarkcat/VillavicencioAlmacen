package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto;

import java.util.Collection;
import java.util.Date;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class DtoDevoluciones {

    private Integer idDevoluciones;
    private Date fechaDevolucion;
    private Integer idNotaVenta;
    private String observaciones;
    private Collection<DtoDetalleDevoluciones> detalles;
    private Integer opcion;
    private DtoNotaVenta nota;
    
    public Integer getIdDevoluciones() {
        return idDevoluciones;
    }

    public void setIdDevoluciones(Integer idDevoluciones) {
        this.idDevoluciones = idDevoluciones;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Collection<DtoDetalleDevoluciones> getDetalles() {
        return detalles;
    }

    public void setDetalles(Collection<DtoDetalleDevoluciones> detalles) {
        this.detalles = detalles;
    }

    public Integer getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(Integer idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public DtoNotaVenta getNota() {
        return nota;
    }

    public void setNota(DtoNotaVenta nota) {
        this.nota = nota;
    }
}
