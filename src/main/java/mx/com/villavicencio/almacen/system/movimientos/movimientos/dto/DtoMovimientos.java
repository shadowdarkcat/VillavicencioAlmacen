package mx.com.villavicencio.almacen.system.movimientos.movimientos.dto;

/**
 *
 * @author Gabriel J
 */
public class DtoMovimientos {

    private Integer opcion;
    private Integer idCargos;
    private Integer idAbonos;
    private Integer idCredito;
    private Integer idNotaVenta;
    private Integer idDevoluciones;

    public Integer getOpcion() {
        return opcion;
    }

    public void setOpcion(Integer opcion) {
        this.opcion = opcion;
    }

    public Integer getIdCargos() {
        return idCargos;
    }

    public void setIdCargos(Integer idCargos) {
        this.idCargos = idCargos;
    }

    public Integer getIdAbonos() {
        return idAbonos;
    }

    public void setIdAbonos(Integer idAbonos) {
        this.idAbonos = idAbonos;
    }

    public Integer getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(Integer idCredito) {
        this.idCredito = idCredito;
    }

    public Integer getIdNotaVenta() {
        return idNotaVenta;
    }

    public void setIdNotaVenta(Integer idNotaVenta) {
        this.idNotaVenta = idNotaVenta;
    }

    public Integer getIdDevoluciones() {
        return idDevoluciones;
    }

    public void setIdDevoluciones(Integer idDevoluciones) {
        this.idDevoluciones = idDevoluciones;
    }
}
