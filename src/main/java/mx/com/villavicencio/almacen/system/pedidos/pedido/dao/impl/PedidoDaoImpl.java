package mx.com.villavicencio.almacen.system.pedidos.pedido.dao.impl;

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
import mx.com.villavicencio.almacen.system.pedidos.detalle.dao.sql.sql.SqlDetallePedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.PedidoDao;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.sql.sql.SqlPedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.factory.PedidoFactory;
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
public class PedidoDaoImpl extends JdbcDaoSupport implements PedidoDao {

    @Override
    public Collection<DtoPedido> findAll() {
        try {
            return getJdbcTemplate().query(View.PEDIDOS, new PedidosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + PedidoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoPedido findById(DtoPedido object) {
        Object[] args = {object.getIdPedido()};
        try {
            return getJdbcTemplate().queryForObject(View.PEDIDO, args, new PedidosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + PedidoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public void ingresar(DtoPedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoPedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoPedido object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PEDIDO);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE)
                    + "\n" + PedidoDaoImpl.class.getSimpleName(), ex);
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE));
        }
    }
    
    @Override
    public void deleteProducto(DtoPedido object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DELETE_PRODUCTO_NOTA);
            simpleJdbcCall.execute(getParameterProcedureDelete(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE)
                    + "\n" + PedidoDaoImpl.class.getSimpleName(), ex);
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_DELETE));
        }
    }

    @Override
    public void updateStatus(DtoPedido object){
       try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_PEDIDO);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY)
                    + "\n" + PedidoDaoImpl.class.getSimpleName(), ex);
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MODIFY));
        }
    }
    
     private SqlParameterSource getParameterProcedureDelete(DtoPedido object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlPedido.ID_PEDIDO, object.getIdPedido(), Types.INTEGER);
        source.addValue(SqlDetallePedido.ID_DETALLE_PEDIDO, object.getDetalle().getIdDetallePedido(), Types.INTEGER);
        return source;
    }
     
     private SqlParameterSource getParameterProcedure(DtoPedido object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlPedido.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlPedido.ID_PEDIDO, object.getIdPedido(), Types.INTEGER);
        source.addValue(SqlPedido.FECHA, object.getFecha(), Types.DATE);
        source.addValue(SqlPedido.FOLIO, object.getFolio(), Types.VARCHAR);
        source.addValue(SqlPedido.NOMBRE_VENDEDOR, object.getNombreVendedor(), Types.VARCHAR);
        source.addValue(SqlPedido.STATUS_ALMACEN, object.getStatusAlmacen(), Types.CHAR);
        return source;
    }
     
    private static class PedidosRowMapper implements RowMapper<DtoPedido> {

        @Override
        public DtoPedido mapRow(ResultSet rs, int i) throws SQLException {
            DtoPedido pedido = PedidoFactory.newInstance();
            pedido.setIdPedido(rs.getInt(SqlPedido.ID_PEDIDO));
            pedido.setFecha(rs.getDate(SqlPedido.FECHA));
            String format = String.format("%04d", rs.getInt(SqlPedido.FOLIO));
            pedido.setFolio(format);
            pedido.setNombreVendedor(rs.getString(SqlPedido.NOMBRE_VENDEDOR));
            pedido.setStatusAlmacen(rs.getString(SqlPedido.STATUS_ALMACEN).charAt(GenericTypes.ZERO));
            return pedido;
        }
    }
}
