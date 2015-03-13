package mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.jdbc.Jdbc;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dao.DetalleDevolucionesDao;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dao.sql.procedure.Procedure;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dao.sql.sql.SqlDetallesDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
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
public class DetalleDevolucionesDaoImpl extends JdbcDaoSupport implements DetalleDevolucionesDao {

    @Override
    public Collection<DtoDetalleDevoluciones> findAll() {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoDetalleDevoluciones> findAll(DtoDetalleDevoluciones object) {
        Object[] args = {object.getIdDevoluciones()};
        try {
            return getJdbcTemplate().query(View.VIEW_DETALLE_DEVOLUCIONES, args, new DetalleDevolucionesRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND));
        }
    }

    @Override
    public DtoDetalleDevoluciones findById(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void ingresar(DtoDetalleDevoluciones object) {
        try {
            SimpleJdbcCall simpleJdbcCall = Jdbc.getSimpleJdbcCall(getJdbcTemplate(), Procedure.PROCEDURE_DETALLE_DEVOLUCIONES);
            simpleJdbcCall.execute(getParameterProcedure(object));
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT)
                    + "\n" + DetalleDevolucionesDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_INSERT));
        }
    }

    @Override
    public void modificar(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoDetalleDevoluciones object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private SqlParameterSource getParameterProcedure(DtoDetalleDevoluciones object) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(SqlDevoluciones.ID_DEVOLUCIONES, object.getIdDevoluciones(), Types.INTEGER);
        source.addValue(SqlDetallesDevoluciones.NOMBRE_PRODUCTO, object.getNombreProducto(), Types.VARCHAR);
        source.addValue(SqlDetallesDevoluciones.CANTIDAD, object.getCantidadPiezas(), Types.INTEGER);
        source.addValue(SqlDetallesDevoluciones.PESO_KILOS, object.getCantidadKilos(), Types.DECIMAL);
        source.addValue(SqlDetallesDevoluciones.COSTO_UNITARIO, object.getCosto(), Types.DECIMAL);
        source.addValue(SqlDetallesDevoluciones.SUBTOTAL, object.getSubtotal(), Types.DECIMAL);
        return source;
    }

    private static class DetalleDevolucionesRowMapper implements RowMapper<DtoDetalleDevoluciones> {

        @Override
        public DtoDetalleDevoluciones mapRow(ResultSet rs, int i) throws SQLException {
            DtoDetalleDevoluciones detalleDevoluciones = DetalleDevolucionesFactory.newInstance();
            detalleDevoluciones.setIdDevoluciones(rs.getInt(SqlDevoluciones.ID_DEVOLUCIONES));
            detalleDevoluciones.setNombreProducto(rs.getString(SqlDetallesDevoluciones.NOMBRE_PRODUCTO));
            detalleDevoluciones.setCantidadPiezas(rs.getInt(SqlDetallesDevoluciones.CANTIDAD));
            detalleDevoluciones.setCantidadKilos(rs.getBigDecimal(SqlDetallesDevoluciones.PESO_KILOS));
            detalleDevoluciones.setCosto(rs.getBigDecimal(SqlDetallesDevoluciones.COSTO_UNITARIO));
            detalleDevoluciones.setSubtotal(rs.getBigDecimal(SqlDetallesDevoluciones.SUBTOTAL));
            return detalleDevoluciones;
        }
    }
}
