package mx.com.villavicencio.almacen.system.productos.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.productos.dao.ProductoDao;
import mx.com.villavicencio.almacen.system.productos.dao.sql.sql.SqlProducto;
import mx.com.villavicencio.almacen.system.productos.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.productos.factory.ProductoFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class ProductoDaoImpl extends JdbcDaoSupport implements ProductoDao {

    @Override
    public Collection<DtoProducto> findAll() {
        try {
            return getJdbcTemplate().query(View.PRODUCTOS, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findById(DtoProducto object) {
        Object[] args = {object.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.PRODUCTO, args, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoProducto object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ProductoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public DtoProducto insertPersonalizado(DtoProducto personalizado) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ProductoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoProducto object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ProductoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoProducto object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ProductoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Boolean verifyExist(DtoProducto object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + ProductoDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoVendedorById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_VENDEDOR_ESTABLECIDO, args, new ProductoRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoVendedorById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADOS, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }
    
    @Override
    public DtoProducto findProductoEstablecidoVendedorById(DtoProducto producto, Integer id) {
        Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_VENDEDOR_ESTABLECIDOS, args, new ProductoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoVendedorById(DtoProducto producto, Integer id) {
        Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_VENDEDOR_PERSONALIZADO, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoEstablecidoClienteById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_CLIENTE_ESTABLECIDO, args, new ProductoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoProducto> findDatosProductoPersonalizadoClienteById(Integer id) {
        Object[] args = {id};
        try {
            return getJdbcTemplate().query(View.DATOS_PRODUCTOS_CLIENTES_PERSONALIZADO, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findProductoPersonalizadoClienteById(DtoProducto producto, Integer id) {
        Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_CLIENTE_PERSONALIZADOS, args, new ProductoPersonalizadoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoProducto findProductoEstablecidoClienteById(DtoProducto producto, Integer id) {
        Object[] args = {id, producto.getIdProducto()};
        try {
            return getJdbcTemplate().queryForObject(View.DATOS_PRODUCTOS_CLIENTE_ESTABLECIDOS, args, new ProductoRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (IncorrectResultSizeDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + ProductoDaoImpl.class
                    .getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }
    
    private static class ProductoRowMapper implements RowMapper<DtoProducto> {

        @Override
        public DtoProducto mapRow(ResultSet rs, int i) {
            DtoProducto producto = ProductoFactory.newInstance();
            try {
                producto.setIdProducto(rs.getInt(SqlProducto.ID_PRODUCTOS));
                producto.setClaveProducto(rs.getString(SqlProducto.CLAVE_PRODUCTO));
                producto.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
                producto.setPeso(rs.getString(SqlProducto.PESO));
                producto.setPesoMinimo(rs.getBigDecimal(SqlProducto.PESO_MINIMO));
                producto.setPesoMaximo(rs.getBigDecimal(SqlProducto.PESO_MAXIMO));
                producto.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
                producto.setImagenProducto(rs.getString(SqlProducto.IMAGEN_PRODUCTO));
                producto.setVisible(rs.getBoolean(SqlProducto.VISIBLE));
                producto.setComision(rs.getBigDecimal(SqlProducto.COMISION));
            } catch (SQLException ex) {
                return producto;
            }
            return producto;
        }
    }

    private static class ProductoPersonalizadoRowMapper implements RowMapper<DtoProducto> {

        @Override
        public DtoProducto mapRow(ResultSet rs, int i) {
            DtoProducto producto = ProductoFactory.newInstance();
            try {
                producto.setIdProducto(rs.getInt(SqlProducto.ID_PRODUCTOS_PERSONALIZADOS));
                producto.setClaveProducto(rs.getString(SqlProducto.CLAVE_PRODUCTO));
                producto.setNombreProducto(rs.getString(SqlProducto.NOMBRE_PRODUCTO));
                producto.setPeso(rs.getString(SqlProducto.PESO));
                producto.setPesoMinimo(rs.getBigDecimal(SqlProducto.PESO_MINIMO));
                producto.setPesoMaximo(rs.getBigDecimal(SqlProducto.PESO_MAXIMO));
                producto.setCostoUnitario(rs.getBigDecimal(SqlProducto.COSTO_UNITARIO));
                producto.setImagenProducto(rs.getString(SqlProducto.IMAGEN_PRODUCTO));
                producto.setComision(rs.getBigDecimal(SqlProducto.COMISION));
            } catch (SQLException ex) {
                return producto;
            }
            return producto;
        }
    }
}
