package mx.com.villavicencio.almacen.system.vendedor.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.vendedor.dao.VendedorDao;
import mx.com.villavicencio.almacen.system.vendedor.dao.sql.sql.SqlVendedor;
import mx.com.villavicencio.almacen.system.vendedor.dao.sql.view.View;
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
public class VendedorDaoImpl extends JdbcDaoSupport implements VendedorDao {

    @Override
    public Collection<DtoVendedor> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + VendedorDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoVendedor findById(DtoVendedor object) {
        Object[] args = {object.getIdVendedor()};
        try {
            return getJdbcTemplate().queryForObject(View.VENDEDOR, args, new VendedoresRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return object;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + VendedorDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoVendedor object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + VendedorDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoVendedor object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + VendedorDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoVendedor object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + VendedorDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class VendedoresRowMapper implements RowMapper<DtoVendedor> {

        @Override
        public DtoVendedor mapRow(ResultSet rs, int i) throws SQLException {

            DtoVendedor vendedor = VendedorFactory.newInstance();

            vendedor.setIdVendedor(rs.getInt(SqlVendedor.ID_VENDEDOR));
            vendedor.setNombre(rs.getString(SqlVendedor.NOMBRE));
            vendedor.setApellidoPaterno(rs.getString(SqlVendedor.APELLIDO_PATERNO));
            vendedor.setApellidoMaterno(rs.getString(SqlVendedor.APELLIDO_MATERNO));
            vendedor.setRfc(rs.getString(SqlVendedor.RFC));
            vendedor.setCalle(rs.getString(SqlVendedor.CALLE));
            vendedor.setCp(rs.getString(SqlVendedor.CP));
            vendedor.setColonia(rs.getString(SqlVendedor.COLONIA));
            vendedor.setDelegacion(rs.getString(SqlVendedor.DELEGAGION));
            vendedor.setMunicipio(rs.getString(SqlVendedor.MUNICIPIO));
            vendedor.setEstado(rs.getString(SqlVendedor.ESTADO));
            vendedor.setCiudad(rs.getString(SqlVendedor.CIUDAD));
            vendedor.setNoExterior(rs.getString(SqlVendedor.NO_EXTERIOR));
            vendedor.setNoInterior(rs.getString(SqlVendedor.NO_INTERIOR));
            vendedor.setTelefono1(rs.getString(SqlVendedor.TELEFONO1));
            vendedor.setTelefono2(rs.getString(SqlVendedor.TELEFONO2));
            vendedor.setTelefono3(rs.getString(SqlVendedor.TELEFONO3));
            vendedor.setCorreo1(rs.getString(SqlVendedor.CORREO1));
            vendedor.setCorreo2(rs.getString(SqlVendedor.CORREO2));
            vendedor.setCorreo3(rs.getString(SqlVendedor.CORREO3));
            vendedor.setComision(rs.getBigDecimal(SqlVendedor.COMISION));
            vendedor.setVisible(rs.getBoolean(SqlVendedor.VISIBLE));
            vendedor.setExterno(rs.getBoolean(SqlVendedor.EXTERNO));
            return vendedor;
        }
    }
}
