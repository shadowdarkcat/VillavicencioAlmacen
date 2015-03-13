package mx.com.villavicencio.almacen.system.pedidos.datos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.pedidos.datos.bo.DatosPedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.datos.dao.DatosPedidoDao;
import mx.com.villavicencio.almacen.system.pedidos.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;


/**
 *
 * @author Gabriel J
 */
public class DatosPedidoBoImpl implements DatosPedidoBo {

    private DatosPedidoDao datosPedidoDao;

    @Override
    public Collection<DtoDatosPedido> findDatosById(DtoUsuario user, DtoDatosPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosPedidoDao.findDatosById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoDatosPedido> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoDatosPedido findById(DtoUsuario user, DtoDatosPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.datosPedidoDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoDatosPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
             this.datosPedidoDao.ingresar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoDatosPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoDatosPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setDatosPedidoDao(DatosPedidoDao datosPedidoDao) {
        this.datosPedidoDao = datosPedidoDao;
    }
}
