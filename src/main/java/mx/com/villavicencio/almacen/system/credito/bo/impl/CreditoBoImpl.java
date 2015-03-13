package mx.com.villavicencio.almacen.system.credito.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.credito.bo.CreditoBo;
import mx.com.villavicencio.almacen.system.credito.dao.CreditoDao;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;


/**
 *
 * @author Gabriel J
 */
public class CreditoBoImpl implements CreditoBo {

    private CreditoDao creditoDao;

    @Override
    public Collection<DtoCredito> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCredito findById(DtoUsuario user, DtoCredito object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.creditoDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCredito findCreditoByIdVendedor(DtoUsuario user, DtoCredito object, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.creditoDao.findCreditoByIdVendedor(object, id);
            return this.findById(user, object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCredito findCreditoByIdCliente(DtoUsuario user, DtoCredito object, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.creditoDao.findCreditoByIdCliente(object, id);
            return this.findById(user, object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoCredito object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoCredito object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.creditoDao.modificar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoCredito object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setCreditoDao(CreditoDao creditoDao) {
        this.creditoDao = creditoDao;
    }

}
