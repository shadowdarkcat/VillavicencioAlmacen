package mx.com.villavicencio.almacen.system.cliente.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.cliente.dao.ClienteDao;
import mx.com.villavicencio.almacen.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.almacen.system.cliente.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.credito.factory.CreditoFactory;
import mx.com.villavicencio.almacen.system.vendedor.dao.sql.sql.SqlVendedor;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.vendedor.factory.VendedorFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class ClienteDaoImpl extends JdbcDaoSupport implements ClienteDao {

    @Override
    public Collection<DtoCliente> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ClienteDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCliente findById(DtoCliente object) {
        Object[] args = {object.getIdCliente()};
        try {
            return getJdbcTemplate().queryForObject(View.CLIENTE, args, new ClientesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return object;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoCliente> findCollectionById(DtoCliente object) {
        Object[] args = {object.getIdCliente()};
        try {
            return getJdbcTemplate().query(View.CLIENTE_VENDEDOR, args, new ClientesRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return new ArrayList<>();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ClienteDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoCliente object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ClienteDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoCliente object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ClienteDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoCliente object) {
       ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ClienteDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }
    
    private static class ClientesRowMapper implements RowMapper<DtoCliente> {

        @Override
        public DtoCliente mapRow(ResultSet rs, int i) throws SQLException {
            DtoCliente cliente = ClienteFactory.newInstance();
            DtoCredito credito = CreditoFactory.newInstance();
            DtoVendedor vendedor = VendedorFactory.newInstance();

            cliente.setIdCliente(rs.getInt(SqlCliente.ID_CLIENTE));
            cliente.setEmpresa(rs.getString(SqlCliente.EMPRESA));
            cliente.setRazonSocial(rs.getString(SqlCliente.RAZON_SOCIAL));
            cliente.setRfc(rs.getString(SqlCliente.RFC));
            cliente.setCorreoEmpresa(rs.getString(SqlCliente.CORREO_EMPRESA));
            cliente.setSitioWeb(rs.getString(SqlCliente.SITIO_WEB));
            cliente.setCalle(rs.getString(SqlCliente.CALLE));
            cliente.setCodigoPostal(rs.getString(SqlCliente.CP));
            cliente.setColonia(rs.getString(SqlCliente.COLONIA));
            cliente.setDelegacion(rs.getString(SqlCliente.DELEGACION));
            cliente.setMunicipio(rs.getString(SqlCliente.MUNICIPIO));
            cliente.setEstado(rs.getString(SqlCliente.ESTADO));
            cliente.setCiudad(rs.getString(SqlCliente.CIUDAD));
            cliente.setNoExterior(rs.getString(SqlCliente.NO_EXTERIOR));
            cliente.setNoInterior(rs.getString(SqlCliente.NO_INTERIOR));
            cliente.setNombre(rs.getString(SqlCliente.NOMBRE));
            cliente.setApellidoPaterno(rs.getString(SqlCliente.APELLIDO_PATERNO));
            cliente.setApellidoMaterno(rs.getString(SqlCliente.APELLIDO_MATERNO));
            cliente.setTelefono1(rs.getString(SqlCliente.TELEFONO1));
            cliente.setTelefono2(rs.getString(SqlCliente.TELEFONO2));
            cliente.setTelefono3(rs.getString(SqlCliente.TELEFONO3));
            cliente.setCorreo1(rs.getString(SqlCliente.CORREO1));
            cliente.setCorreo2(rs.getString(SqlCliente.CORREO2));
            cliente.setCorreo3(rs.getString(SqlCliente.CORREO3));
            cliente.setContacto1(rs.getString(SqlCliente.CONTACTO1));
            cliente.setContacto2(rs.getString(SqlCliente.CONTACTO2));
            cliente.setContacto3(rs.getString(SqlCliente.CONTACTO3));
            credito.setTipoCredito(rs.getString(SqlCliente.TIPO_CREDITO));
            credito.setCantidadMonetaria(rs.getBigDecimal(SqlCliente.CANTIDAD_MONETARIA));
            cliente.setVisible(rs.getBoolean(SqlCliente.VISIBLE));
            cliente.setCredito(credito);
            vendedor.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            cliente.setVendedor(vendedor);
            return cliente;
        }
    }
}
