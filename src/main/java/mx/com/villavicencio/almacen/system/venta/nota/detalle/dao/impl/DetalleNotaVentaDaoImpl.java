package mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.jdbc.Jdbc;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.DetalleNotaVentaDao;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.sql.sql.SqlDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.factory.DetalleNotaVentaFactory;
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
public class DetalleNotaVentaDaoImpl extends JdbcDaoSupport implements DetalleNotaVentaDao {

    @Override
    public Collection<DtoDetalleNotaVenta> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoDetalleNotaVenta> findAll(DtoDetalleNotaVenta object) {
        Object[] args = {object.getIdNotaVenta()};
        try {
            return getJdbcTemplate().query(View.VIEW_DETALLE_NOTA_VENTA, args, new DetalleNotaVentaRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDetalleNotaVenta findById(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void ingresar(DtoDetalleNotaVenta object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DETALLE_NOTA_VENTA);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDetalleNotaVenta object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + DetalleNotaVentaDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoDetalleNotaVenta object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDetalleNotaVenta.OPCION, object.getOpcion(), Types.INTEGER);
        source.addValue(SqlNotaVenta.ID_NOTA_VENTA, object.getIdNotaVenta(), Types.INTEGER);
        source.addValue(SqlDetalleNotaVenta.CANTIDAD, object.getCantidadPiezas(), Types.INTEGER);
        source.addValue(SqlProducto.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlDetalleNotaVenta.CANTIDAD_KILOS, object.getCantidadKilos(), Types.DECIMAL);
        source.addValue(SqlProducto.COSTO_UNITARIO, object.getCostoUnitario(), Types.DECIMAL);
        source.addValue(SqlDetalleNotaVenta.SUBTOTAL, object.getSubTotal(), Types.DECIMAL);
        source.addValue(SqlDetalleNotaVenta.OBSERVACIONES_EXCEDIDO, object.getObservacionExcedido(), Types.VARCHAR);
        return source;
    }

    private static class DetalleNotaVentaRowMapper implements RowMapper<DtoDetalleNotaVenta> {

        @Override
        public DtoDetalleNotaVenta mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetalleNotaVenta detalleNotaVenta = DetalleNotaVentaFactory.newInstance();
            detalleNotaVenta.setIdNotaVenta(rs.getInt(SqlNotaVenta.ID_NOTA_VENTA));
            detalleNotaVenta.setCantidadPiezas(rs.getInt(SqlDetalleNotaVenta.CANTIDAD));
            detalleNotaVenta.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
            detalleNotaVenta.setCantidadKilos(rs.getBigDecimal(SqlDetalleNotaVenta.CANTIDAD_KILOS));
            detalleNotaVenta.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
            detalleNotaVenta.setSubTotal(rs.getBigDecimal(SqlDetalleNotaVenta.SUBTOTAL));
            detalleNotaVenta.setObservacionExcedido(rs.getString(SqlDetalleNotaVenta.OBSERVACIONES_EXCEDIDO));
            return detalleNotaVenta;
        }
    }
}
