package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.movimientos.cargos.bo.CargosBo;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.system.movimientos.cargos.factory.CargosFactory;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.bo.MovimientosBo;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.bo.DetalleDevolucionesBo;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.bo.DevolucionesBo;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.DevolucionesDao;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.almacen.system.venta.nota.nota.bo.NotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.factory.NotaVentaFactory;

/**
 *
 * @author Gabriel J
 */
public class DevolucionesBoImpl implements DevolucionesBo {

    private DevolucionesDao devolucionesDao;
    private DetalleDevolucionesBo detalleDevolucionesBo;
    private MovimientosBo movimientosBo;
    private NotaVentaBo notaVentaBo;
    private CargosBo cargosBo;
    @Override
    public Collection<DtoDevoluciones> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            Collection<DtoDevoluciones> collection = new ArrayList<>();
            for (DtoDevoluciones devoluciones : this.devolucionesDao.findAll()) {
                DtoDetalleDevoluciones detalleDevoluciones = DetalleDevolucionesFactory.newInstance(devoluciones.getIdDevoluciones());
                DtoMovimientos movimientos = MovimientosFactory.newInstance();
                movimientos.setIdDevoluciones(detalleDevoluciones.getIdDevoluciones());
                devoluciones.setDetalles(this.detalleDevolucionesBo.findAll(user, detalleDevoluciones));
                movimientos = this.movimientosBo.findByIdDevolucion(user, movimientos);
                DtoNotaVenta notaVenta = NotaVentaFactory.newInstance(movimientos.getIdNotaVenta());
                devoluciones.setNota(this.notaVentaBo.findById(user, notaVenta));
                collection.add(devoluciones);
            }
            return collection;
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoDevoluciones findById(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            object = this.findById(user, object);
            object.setDetalles(
                    this.detalleDevolucionesBo.findAll(
                            user, DetalleDevolucionesFactory.newInstance(object.getIdDevoluciones()
                            )
                    )
            );
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setIdDevoluciones(object.getIdDevoluciones());
            movimientos = this.movimientosBo.findByIdDevolucion(user, movimientos);
            DtoNotaVenta notaVenta = NotaVentaFactory.newInstance(movimientos.getIdNotaVenta());
            object.setNota(this.notaVentaBo.findById(user, notaVenta));
            return object;
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            BigDecimal totalDevolucion = BigDecimal.ZERO;
            BigDecimal nuevoCargo = BigDecimal.ZERO;
            movimientos.setIdNotaVenta(object.getIdNotaVenta());
            object = this.devolucionesDao.insert(object);
            for (DtoDetalleDevoluciones detalles : object.getDetalles()) {
                detalles.setIdDevoluciones(object.getIdDevoluciones());
                this.detalleDevolucionesBo.ingresar(user, detalles);
                totalDevolucion = totalDevolucion.add(detalles.getSubtotal());
            }
            movimientos = this.movimientosBo.findById(user, movimientos);
            movimientos.setIdDevoluciones(object.getIdDevoluciones());
            movimientos.setOpcion(GenericTypes.MODIFY);
            this.movimientosBo.modificar(user, movimientos);
            DtoCargos cargos = CargosFactory.newInstance(movimientos.getIdCargos());
            cargos = cargosBo.findById(user, cargos);
            cargos.setCargoAnterior(cargos.getCargo());
            nuevoCargo = cargos.getCargo().subtract(totalDevolucion);
            cargos.setCargo(nuevoCargo);
            cargos.setOpcion(GenericTypes.MODIFY);
            cargosBo.modificar(user, cargos);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoDevoluciones object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistDevolucion(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setIdNotaVenta(object.getIdNotaVenta());
            return this.movimientosBo.verifyExistDevolucion(user, movimientos);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDevolucionesDao(DevolucionesDao devolucionesDao) {
        this.devolucionesDao = devolucionesDao;
    }

    public void setMovimientosBo(MovimientosBo movimientosBo) {
        this.movimientosBo = movimientosBo;
    }

    public void setDetalleDevolucionesBo(DetalleDevolucionesBo detalleDevolucionesBo) {
        this.detalleDevolucionesBo = detalleDevolucionesBo;
    }

    public void setNotaVentaBo(NotaVentaBo notaVentaBo) {
        this.notaVentaBo = notaVentaBo;
    }

    public void setCargosBo(CargosBo cargosBo) {
        this.cargosBo = cargosBo;
    }
}
