package mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.jdbc.Jdbc;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.credito.dao.sql.sql.SqlCredito;
import mx.com.villavicencio.almacen.system.movimientos.abonos.dao.sql.SqlAbonos;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.sql.SqlCargos;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.MovimientosDao;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.sql.function.Function;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.sql.sql.SqlMovimientos;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.sql.SqlNotaVenta;
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
public class MovimientosDaoImpl extends JdbcDaoSupport implements MovimientosDao {

    @Override
    public Collection<DtoMovimientos> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoMovimientos findById(DtoMovimientos object) {
        Object[] args = {object.getIdNotaVenta()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_MOVIMIENTO, args, new MovimientosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoMovimientos findByIdDevolucion(DtoMovimientos object) {
        Object[] args = {object.getIdDevoluciones()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_MOVIMIENTO_DEVOLUCION, args, new MovimientosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }
    
    @Override
    public void ingresar(DtoMovimientos object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_MOVIMIENTOS);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoMovimientos object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_MOVIMIENTOS);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }

    @Override
    public void eliminar(DtoMovimientos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Boolean verifyExistDevolucion(DtoMovimientos object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.VERIFY_EXIST_DEVOLUCION);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, getParameterFunction(object));
            return executeFunction != 0;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR)
                    + "\n" + MovimientosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_VERIFICAR));
        }
    }

    private SqlParameterSource getParameterFunction(DtoMovimientos object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA + "S", object.getIdNotaVenta(), Types.INTEGER);
        return source;
    }

    private SqlParameterSource getParameterProcedure(DtoMovimientos object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlMovimientos.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlCargos.ID_CARGOS, object.getIdCargos(), Types.INTEGER);
        source.addValue(SqlAbonos.ID_ABONOS, object.getIdAbonos(), Types.INTEGER);
        source.addValue(SqlCredito.ID_CREDITO, object.getIdCredito(), Types.INTEGER);
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA, object.getIdNotaVenta(), Types.INTEGER);
        source.addValue(SqlDevoluciones.ID_DEVOLUCIONES, object.getIdDevoluciones(), Types.INTEGER);
        return source;
    }

    private static class MovimientosRowMapper implements RowMapper<DtoMovimientos> {

        @Override
        public DtoMovimientos mapRow(ResultSet rs, int i) throws SQLException {
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            movimientos.setIdCargos(rs.getInt(SqlCargos.ID_CARGOS));
            movimientos.setIdAbonos(rs.getInt(SqlAbonos.ID_ABONOS));
            movimientos.setIdCredito(rs.getInt(SqlCredito.ID_CREDITO));
            movimientos.setIdNotaVenta(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA));
            movimientos.setIdDevoluciones(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES));
            return movimientos;
        }
    }
}
