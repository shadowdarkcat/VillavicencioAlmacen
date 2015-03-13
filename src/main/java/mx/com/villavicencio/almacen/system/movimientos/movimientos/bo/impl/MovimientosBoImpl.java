package mx.com.villavicencio.almacen.system.movimientos.movimientos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.bo.MovimientosBo;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.MovimientosDao;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class MovimientosBoImpl implements MovimientosBo {

    private MovimientosDao movimientosDao;

    @Override
    public Collection<DtoMovimientos> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoMovimientos findById(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.movimientosDao.findById(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoMovimientos findByIdDevolucion(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.movimientosDao.findByIdDevolucion(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.movimientosDao.ingresar(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.movimientosDao.modificar(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Boolean verifyExistDevolucion(DtoUsuario user, DtoMovimientos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.movimientosDao.verifyExistDevolucion(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setMovimientosDao(MovimientosDao movimientosDao) {
        this.movimientosDao = movimientosDao;
    }
}
