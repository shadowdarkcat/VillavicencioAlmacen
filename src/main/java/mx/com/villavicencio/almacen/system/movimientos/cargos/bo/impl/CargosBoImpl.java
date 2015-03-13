package mx.com.villavicencio.almacen.system.movimientos.cargos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.movimientos.cargos.bo.CargosBo;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.CargosDao;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class CargosBoImpl implements CargosBo {

    private CargosDao cargosDao;

    @Override
    public Collection<DtoCargos> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCargos findById(DtoUsuario user, DtoCargos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.cargosDao.findById(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoCargos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCargos insert(DtoUsuario user, DtoCargos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.cargosDao.insert(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoCargos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.cargosDao.modificar(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoCargos object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setCargosDao(CargosDao cargosDao) {
        this.cargosDao = cargosDao;
    }

}
