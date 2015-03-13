package mx.com.villavicencio.almacen.system.inventario.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.jdbc.Jdbc;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.inventario.dao.InventarioDao;
import mx.com.villavicencio.almacen.system.inventario.dao.sql.function.Function;
import mx.com.villavicencio.almacen.system.inventario.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.inventario.dao.sql.sql.SqlInventario;
import mx.com.villavicencio.almacen.system.inventario.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.inventario.factory.InventarioFactory;
import mx.com.villavicencio.almacen.system.productos.dao.sql.sql.SqlProducto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class InventarioDaoImpl extends JdbcDaoSupport implements InventarioDao {

    @Override
    public Collection<DtoInventario> findAll() {
        try {
            return getJdbcTemplate().query(View.VIEW_INVENTARIOS, new InventarioRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoInventario findById(DtoInventario object) {
        Object[] args = {object.getIdInventario()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_INVENTARIO, args, new InventarioRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoInventario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoInventario object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_INVENTARIO);
            simpleJdbcCall.execute(getProcedureParameter(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }

    @Override
    public void eliminar(DtoInventario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + InventarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Boolean isAgotado(DtoInventario object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXISTENCIA);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, getFunctionParameter(object));
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR));
        }
    }

    @Override
    public DtoInventario getExistencia(DtoInventario object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXISTENCIA);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, getFunctionParameter(object));
            object.setCantidadExistencia(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + InventarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    private SqlParameterSource getProcedureParameter(DtoInventario object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlInventario.ID_INVENTARIO, object.getIdInventario(), Types.INTEGER);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlInventario.CANTIDAD_EXISTENCIA, object.getCantidadExistencia(), Types.INTEGER);
        return source;
    }

    private SqlParameterSource getFunctionParameter(DtoInventario object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlProducto.NOMBRE_PRODUCTO + "s", object.getNombreProducto(), Types.VARCHAR);
        return source;
    }

    private static class InventarioRowMapper implements RowMapper<DtoInventario> {

        @Override
        public DtoInventario mapRow(ResultSet rs, int i) throws SQLException {
            DtoInventario inventario = InventarioFactory.newInstance();
            inventario.setIdInventario(rs.getInt(SqlInventario.ID_INVENTARIO));
            inventario.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            inventario.setCantidadExistencia(rs.getInt(SqlInventario.CANTIDAD_EXISTENCIA));
            return inventario;
        }
    }
}
