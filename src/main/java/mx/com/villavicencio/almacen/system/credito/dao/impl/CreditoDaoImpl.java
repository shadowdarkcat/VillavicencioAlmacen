package mx.com.villavicencio.almacen.system.credito.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.credito.dao.CreditoDao;
import mx.com.villavicencio.almacen.system.credito.dao.sql.sql.SqlCredito;
import mx.com.villavicencio.almacen.system.credito.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.credito.factory.CreditoFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class CreditoDaoImpl extends JdbcDaoSupport implements CreditoDao {

    @Override
    public Collection<DtoCredito> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCredito findById(DtoCredito object) {
        Object[] args = {object.getIdCredito()};
        try {
            return getJdbcTemplate().queryForObject(View.CREDITO, args, new CreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoCredito findCreditoByIdVendedor(DtoCredito object, Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_CREDITO, args, new DatosCreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoCredito findCreditoByIdCliente(DtoCredito object, Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_CREDITO_CLIENTE, args, new DatosCreditoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return CreditoFactory.newInstance();
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + CreditoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoCredito object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoCredito object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoCredito object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + CreditoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class CreditoRowMapper implements RowMapper<DtoCredito> {

        @Override
        public DtoCredito mapRow(ResultSet rs, int i) throws SQLException {
            DtoCredito credito = CreditoFactory.newInstance();
            credito.setIdCredito(rs.getInt(SqlCredito.ID_CREDITO));
            credito.setTipoCredito(rs.getString(SqlCredito.TIPO_CREDITO));
            credito.setFechaRegistro(rs.getDate(SqlCredito.FECHA_REGISTRO));
            credito.setFechaPago(rs.getDate(SqlCredito.FECHA_PAGO));
            credito.setFolioNota(rs.getString(SqlCredito.FOLIO_NOTA));
            credito.setCantidadMonetaria(rs.getBigDecimal(SqlCredito.CANTIDAD_MONETARIA));
            credito.setEstatusCredito(rs.getString(SqlCredito.ESTATUS_CREDITO));
            return credito;
        }
    }

    private static class DatosCreditoRowMapper implements RowMapper<DtoCredito> {

        @Override
        public DtoCredito mapRow(ResultSet rs, int i) throws SQLException {
            DtoCredito credito = CreditoFactory.newInstance();
            credito.setIdCredito(rs.getInt(SqlCredito.ID_CREDITO));
            return credito;
        }
    }
}
