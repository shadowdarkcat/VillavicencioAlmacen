package mx.com.villavicencio.almacen.system.cliente.bo.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.almacen.system.cliente.dao.ClienteDao;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.credito.bo.CreditoBo;
import mx.com.villavicencio.almacen.system.credito.datos.bo.DatosCreditoBo;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.productos.bo.ProductoBo;
import mx.com.villavicencio.almacen.system.productos.datos.bo.DatosBo;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.productos.factory.ProductoFactory;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.bo.VendedorBo;

/**
 *
 * @author Gabriel J
 */
public class ClienteBoImpl implements ClienteBo {

    private ClienteDao clienteDao;
    private ProductoBo productoBo;
    private CreditoBo creditoBo;
    private DatosBo datosBo;
    private DatosCreditoBo datosCreditoBo;
    private VendedorBo vendedorBo;

    @Override
    public Collection<DtoCliente> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.clienteDao.findAll();
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoCliente findById(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            object = this.clienteDao.findById(object);
            object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
            Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoClienteById(user, object.getIdCliente());
            Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoClienteById(user, object.getIdCliente());
            if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
                object.setEstablecidos(establecido);
            } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
                object.setPersonalizados(personalizado);
            } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
                object.setPersonalizados(personalizado);
            }
            object.setEstablecidos(getProductoNuevo(user, object.getEstablecidos()));
            object.setPersonalizados(getProductoNuevoPersonalizado(user, object.getPersonalizados()));
            DtoCredito findCreditoByIdCliente = this.creditoBo.findCreditoByIdCliente(user, object.getCredito(), object.getIdCliente());
            if ((findCreditoByIdCliente != null) && (findCreditoByIdCliente.getIdCredito() != null)) {
                object.setCredito(findCreditoByIdCliente);
            }
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void modificar(DtoUsuario user, DtoCliente object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoCliente object) {
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

    @Override
    public Collection<DtoCliente> findByIdVendedor(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            DtoCliente clienteFind = ClienteFactory.newInstance(user.getIdVendedor());
            Collection<DtoCliente> collection = new ArrayList<>();
            if (user.getIdUsuario() == 1) {
                for (DtoCliente clientes : this.clienteDao.findAll()) {
                    collection.add(getDtoCliente(clientes, user));
                }
            } else {
                for (DtoCliente clientes : this.clienteDao.findCollectionById(clienteFind)) {
                    collection.add(getDtoCliente(clientes, user));
                }
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    private DtoCliente getDtoCliente(DtoCliente clientes, DtoUsuario user) {
        DtoCliente cliente = ClienteFactory.newInstance();
        cliente.setIdCliente(clientes.getIdCliente());
        cliente.setEmpresa(clientes.getEmpresa());
        cliente.setRazonSocial(clientes.getRazonSocial());
        cliente.setRfc(clientes.getRfc());
        cliente.setCorreoEmpresa(clientes.getCorreoEmpresa());
        cliente.setSitioWeb(clientes.getSitioWeb());
        cliente.setNombre(clientes.getNombre());
        cliente.setApellidoPaterno(clientes.getApellidoPaterno());
        cliente.setApellidoMaterno(clientes.getApellidoMaterno());
        cliente.setRfc(clientes.getRfc());
        cliente.setCalle(clientes.getCalle());
        cliente.setCodigoPostal(clientes.getCodigoPostal());
        cliente.setColonia(clientes.getColonia());
        cliente.setDelegacion(clientes.getDelegacion());
        cliente.setMunicipio(clientes.getMunicipio());
        cliente.setEstado(clientes.getEstado());
        cliente.setCiudad(clientes.getCiudad());
        cliente.setNoExterior(clientes.getNoExterior());
        cliente.setNoInterior(clientes.getNoInterior());
        cliente.setTelefono1(clientes.getTelefono1());
        cliente.setTelefono2(clientes.getTelefono2());
        cliente.setTelefono3(clientes.getTelefono3());
        cliente.setContacto1(clientes.getCorreo1());
        cliente.setContacto2(clientes.getContacto2());
        cliente.setContacto3(clientes.getContacto3());
        cliente.setCorreo1(clientes.getCorreo1());
        cliente.setCorreo2(clientes.getCorreo2());
        cliente.setCorreo3(clientes.getCorreo3());
        cliente.setCredito(clientes.getCredito());
        cliente.setVisible(clientes.getVisible());
        Collection<DtoProducto> establecido = this.productoBo.findDatosProductoEstablecidoClienteById(user, clientes.getIdCliente());
        Collection<DtoProducto> personalizado = this.productoBo.findDatosProductoPersonalizadoClienteById(user, clientes.getIdCliente());
        if ((!establecido.isEmpty()) && (personalizado.isEmpty())) {
            cliente.setEstablecidos(establecido);
        } else if ((!personalizado.isEmpty()) && (establecido.isEmpty())) {
            cliente.setPersonalizados(personalizado);
        } else if ((!establecido.isEmpty()) && (!personalizado.isEmpty())) {
            cliente.setPersonalizados(personalizado);
        }
        return cliente;
    }

    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

    public void setProductoBo(ProductoBo productoBo) {
        this.productoBo = productoBo;
    }

    public void setCreditoBo(CreditoBo creditoBo) {
        this.creditoBo = creditoBo;
    }

    public void setDatosBo(DatosBo datosBo) {
        this.datosBo = datosBo;
    }

    public void setDatosCreditoBo(DatosCreditoBo datosCreditoBo) {
        this.datosCreditoBo = datosCreditoBo;
    }

    public void setVendedorBo(VendedorBo vendedorBo) {
        this.vendedorBo = vendedorBo;
    }
}
