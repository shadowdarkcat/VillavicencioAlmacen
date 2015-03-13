package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.jdbc.Jdbc;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.DevolucionesDao;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.function.Function;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
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
public class DevolucionesDaoImpl extends JdbcDaoSupport implements DevolucionesDao {

    @Override
    public Collection<DtoDevoluciones> findAll() {
        try {
            return getJdbcTemplate().query(View.VIEW_DEVOLUCIONES, new DevolucionesRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDevoluciones findById(DtoDevoluciones object) {
        Object[] args = {object.getIdDevoluciones()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_DEVOLUCION, args, new DevolucionesRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoDevoluciones insert(DtoDevoluciones object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DEVOLUCIONES);
            simpleJdbcCall.execute(getProcedureParameter(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_DEVOLUCIONES);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdDevoluciones(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void modificar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getProcedureParameter(DtoDevoluciones object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDevoluciones.ID_DEVOLUCIONES, object.getIdDevoluciones(), Types.INTEGER);
        source.addValue(SqlDevoluciones.FECHA_DEVOLUCION, object.getFechaDevolucion(), Types.DATE);
        source.addValue(SqlDevoluciones.OBSERVACIONES, object.getObservaciones(), Types.VARCHAR);
        return source;
    }

    private static class DevolucionesRowMapper implements RowMapper<DtoDevoluciones> {

        @Override
        public DtoDevoluciones mapRow(ResultSet rs, int i) throws SQLException {
            DtoDevoluciones devoluciones = DevolucionesFactory.newInstance();
            devoluciones.setIdDevoluciones(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES));
            devoluciones.setFechaDevolucion(rs.getDate(SqlDevoluciones.FECHA_DEVOLUCION));
            devoluciones.setObservaciones(rs.getString(SqlDevoluciones.OBSERVACIONES));
            return devoluciones;
        }
    }
}
