package mx.com.villavicencio.almacen.system.productos.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.productos.bo.ProductoBo;
import mx.com.villavicencio.almacen.system.productos.dao.ProductoDao;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class ProductoBoImpl implements ProductoBo {

    private ProductoDao productoDao;

    @Override
    public Boolean verifyExist(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findAll();
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findById(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findById(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto insertPersonalizado(DtoUsuario user, DtoProducto personalizado) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoProducto object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoEstablecidoVendedorById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoPersonalizadoVendedorById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoVendedorById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoPersonalizadoVendedorById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoClienteById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoEstablecidoClienteById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(DtoUsuario user, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findDatosProductoPersonalizadoClienteById(id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoClienteById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoPersonalizadoClienteById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoEstablecidoClienteById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoEstablecidoClienteById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoProducto findProductoEstablecidoVendedorById(DtoUsuario user, DtoProducto producto, Integer id) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.productoDao.findProductoEstablecidoVendedorById(producto, id);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }
}
