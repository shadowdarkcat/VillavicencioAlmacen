package mx.com.villavicencio.almacen.system.venta.nota.nota.dao.impl;

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
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.factory.PedidoFactory;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.NotaVentaDao;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.function.Function;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.sql.SqlNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.factory.NotaVentaFactory;
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
public class NotaVentaDaoImpl extends JdbcDaoSupport implements NotaVentaDao {

    @Override
    public Collection<DtoNotaVenta> findAll() {
        try {
            return getJdbcTemplate().query(View.VIEW_NOTAS_VENTA, new NotaVentaRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoNotaVenta findById(DtoNotaVenta object) {
        Object[] args = {object.getIdNotaVenta()};
        try {
            return getJdbcTemplate().queryForObject(View.VIEW_NOTA_VENTA, args, new NotaVentaRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoNotaVenta insert(DtoNotaVenta object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_NOTA_VENTA);
            simpleJdbcCall.execute(getProcedureParameter(object));
            simpleJdbcCall = Jdbc.getSimpleJdbcCallFunction(getJdbcTemplate(), Function.LAST_INDEX_NOTA_VENTA);
            Integer executeFunction = simpleJdbcCall.executeFunction(Integer.class, GenericTypes.NONE);
            object.setIdNotaVenta(executeFunction);
            return object;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoNotaVenta object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_NOTA_VENTA);
            simpleJdbcCall.execute(getProcedureParameter(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + NotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }

    @Override
    public void eliminar(DtoNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));

    }

    private SqlParameterSource getProcedureParameter(DtoNotaVenta object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlNotaVenta.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA, object.getIdNotaVenta(), Types.INTEGER);
        source.addValue(SqlNotaVenta.FECHA, object.getFecha(), Types.DATE);
        source.addValue(SqlNotaVenta.FOLIO, object.getFolio(), Types.INTEGER);
        source.addValue(SqlNotaVenta.NOMBRE_CLIENTE, object.getNombreCliente(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.DIRECCION, object.getDireccion(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.RFC, object.getRfc(), Types.VARCHAR);
        source.addValue(SqlNotaVenta.STATUS_NOTA_VENTA, object.getStatusNotaVenta(), Types.CHAR);
        source.addValue(SqlNotaVenta.OBSERVACIONES, object.getObservaciones(), Types.VARCHAR);
        source.addValue(SqlPedido.ID_PEDIDO, object.getPedido().getIdPedido(),Types.INTEGER);
        return source;
    }

    private static class NotaVentaRowMapper implements RowMapper<DtoNotaVenta> {

        @Override
        public DtoNotaVenta mapRow(ResultSet rs, int i) throws SQLException {
            DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
            notaVenta.setIdNotaVenta(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA));
            notaVenta.setFecha(rs.getDate(SqlNotaVenta.FECHA));
            String format = String.format("%04d", rs.getInt(SqlNotaVenta.FOLIO));
            notaVenta.setFolio(format);
            notaVenta.setNombreCliente(rs.getString(SqlNotaVenta.NOMBRE_CLIENTE));
            notaVenta.setDireccion(rs.getString(SqlNotaVenta.DIRECCION));
            notaVenta.setRfc(rs.getString(SqlNotaVenta.RFC));
            notaVenta.setStatusNotaVenta(rs.getString(SqlNotaVenta.STATUS_NOTA_VENTA).charAt(GenericTypes.ZERO));
            notaVenta.setObservaciones(rs.getString(SqlNotaVenta.OBSERVACIONES));
            notaVenta.setPedido(
                    PedidoFactory.newInstance(
                            rs.getInt(SqlPedido.ID_PEDIDO))
            );
            return notaVenta;
        }
    }
}
