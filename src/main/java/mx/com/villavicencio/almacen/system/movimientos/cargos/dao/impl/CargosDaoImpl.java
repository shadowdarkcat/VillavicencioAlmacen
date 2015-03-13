package mx.com.villavicencio.almacen.system.movimientos.cargos.dao.impl;

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
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.CargosDao;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.function.Function;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.sql.SqlCargos;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.system.movimientos.cargos.factory.CargosFactory;
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
public class CargosDaoImpl extends JdbcDaoSupport implements CargosDao {

    @Override
    public Collection<DtoCargos> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCargos findById(DtoCargos object) {
        Object[] args = {object.getIdCargos()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_CARGO, args, new CargosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + CargosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoCargos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoCargos insert(DtoCargos object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CARGOS);
            simpleJdbcCall.execute(getProcedureParameter(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_CARGO);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdCargos(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + CargosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoCargos object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_CARGOS);
            simpleJdbcCall.execute(getProcedureParameter(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + CargosDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }

    @Override
    public void eliminar(DtoCargos object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getProcedureParameter(DtoCargos object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlCargos.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlCargos.ID_CARGOS, object.getIdCargos(), Types.INTEGER);
        source.addValue(SqlCargos.FECHA_CARGO, object.getFechaCargo(), Types.DATE);
        source.addValue(SqlCargos.CARGO, object.getCargo(), Types.DECIMAL);
        source.addValue(SqlCargos.CARGO_ANTERIOR, object.getCargoAnterior(), Types.DECIMAL);
        return source;
    }

    private static class CargosRowMapper implements RowMapper<DtoCargos> {

        @Override
        public DtoCargos mapRow(ResultSet rs, int i) throws SQLException {
            DtoCargos cargos = CargosFactory.newInstance();
            cargos.setIdCargos(rs.getInt(SqlCargos.ID_CARGOS));
            cargos.setFechaCargo(rs.getDate(SqlCargos.FECHA_CARGO));
            cargos.setCargo(rs.getBigDecimal(SqlCargos.CARGO));
            cargos.setCargoAnterior(rs.getBigDecimal(SqlCargos.CARGO_ANTERIOR));
            return cargos;
        }
    }
}
