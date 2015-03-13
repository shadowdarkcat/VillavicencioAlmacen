package mx.com.villavicencio.almacen.system.vendedor.bo.impl;

import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.productos.bo.ProductoBo;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.productos.factory.ProductoFactory;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.almacen.system.vendedor.dao.VendedorDao;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;

/**
 *
 * @author Gabriel J
 */
public class VendedorBoImpl implements VendedorBo {

    private VendedorDao vendedorDao;
    private ProductoBo productoBo;
    
    @Override
    public Collection<DtoVendedor> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoVendedor findById(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.vendedorDao.findById(object);
            Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoVendedorById(user, object.getIdVendedor());
            Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoVendedorById(user, object.getIdVendedor());
            if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
                object.setEstablecidos(establecido);
            } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
                object.setPersonalizados(personalizado);
            } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
                object.setPersonalizados(personalizado);
            }
            object.setEstablecidos(getProductoNuevo(user, object.getEstablecidos()));
            object.setPersonalizados(getProductoNuevoPersonalizado(user, object.getPersonalizados()));
            //object.setCredito(this.creditoBo.findCreditoByIdVendedor(user, CreditoFactory.newInstance(), object.getIdVendedor()));
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoVendedor object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

     private Collection<DtoProducto> getProductoNuevo(DtoUsuario user, Collection<DtoProducto> object) {

        Collection<DtoProducto> findAll = productoBo.findAll(user);
        Integer id = null;
        if (object != null) {
            for (DtoProducto productos : findAll) {
                for (DtoProducto products : object) {
                    id = products.getIdProducto();
                    if (Objects.equals(productos.getIdProducto(), id)) {
                        break;
                    }
                }
                if (!Objects.equals(productos.getIdProducto(), id)) {
                    DtoProducto producto = ProductoFactory.newInstance();
                    producto.setIdProducto(productos.getIdProducto());
                    producto.setClaveProducto(productos.getClaveProducto());
                    producto.setImagenProducto(productos.getImagenProducto());

                    producto.setNombreProducto(productos.getNombreProducto());
                    producto.setPeso(productos.getPeso());
                    producto.setPesoMaximo(productos.getPesoMaximo());
                    producto.setPesoMinimo(productos.getPesoMinimo());
                    producto.setCostoUnitario(productos.getCostoUnitario());
                    producto.setVisible(productos.getVisible());
                    object.add(producto);
                }
            }
        }
        return object;
    }

    private Collection<DtoProducto> getProductoNuevoPersonalizado(DtoUsuario user, Collection<DtoProducto> object) {

        Collection<DtoProducto> findAll = productoBo.findAll(user);
        String clave = null;
        if (object != null) {
            for (DtoProducto productos : findAll) {
                for (DtoProducto products : object) {
                    clave = products.getClaveProducto();
                    if (Objects.equals(productos.getClaveProducto(), clave)) {
                        break;
                    }
                }
                if (!Objects.equals(productos.getClaveProducto(), clave)) {
                    DtoProducto producto = ProductoFactory.newInstance();
                    producto.setIdProducto(productos.getIdProducto());
                    producto.setClaveProducto(productos.getClaveProducto());
                    producto.setImagenProducto(productos.getImagenProducto());

                    producto.setNombreProducto(productos.getNombreProducto());
                    producto.setPeso(productos.getPeso());
                    producto.setPesoMaximo(productos.getPesoMaximo());
                    producto.setPesoMinimo(productos.getPesoMinimo());
                    producto.setCostoUnitario(productos.getCostoUnitario());
                    producto.setVisible(productos.getVisible());
                    object.add(producto);
                }
            }
        }
        return object;
    }
    
    public void setVendedorDao(VendedorDao vendedorDao) {
        this.vendedorDao = vendedorDao;
    }

    public void setProductoBo(ProductoBo productoBo) {
        this.productoBo = productoBo;
    }
}
