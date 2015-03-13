package mx.com.villavicencio.almacen.system.venta.nota.detalle.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.bo.DetalleNotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.DetalleNotaVentaDao;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class DetalleNotaVentaBoImpl implements DetalleNotaVentaBo {

    private DetalleNotaVentaDao detalleNotaVentaDao;

    @Override
    public Collection<DtoDetalleNotaVenta> findAll(DtoUsuario user, DtoDetalleNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.detalleNotaVentaDao.findAll(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoDetalleNotaVenta> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoDetalleNotaVenta findById(DtoUsuario user, DtoDetalleNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoDetalleNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.detalleNotaVentaDao.ingresar(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoDetalleNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoDetalleNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDetalleNotaVentaDao(DetalleNotaVentaDao detalleNotaVentaDao) {
        this.detalleNotaVentaDao = detalleNotaVentaDao;
    }

}
