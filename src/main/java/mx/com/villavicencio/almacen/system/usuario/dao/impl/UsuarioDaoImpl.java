package mx.com.villavicencio.almacen.system.usuario.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.usuario.dao.UsuarioDao;
import mx.com.villavicencio.almacen.system.usuario.dao.sql.sql.SqlUsuario;
import mx.com.villavicencio.almacen.system.usuario.dao.sql.view.View;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.usuario.factory.UsuarioFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class UsuarioDaoImpl extends JdbcDaoSupport implements UsuarioDao {

    @Override
    public DtoUsuario validateUsuario(DtoUsuario object) {
        Object[] args = {object.getNombreUsuario(), object.getPassword()};
        try {
            return getJdbcTemplate().queryForObject(View.USUARIO, args, new UsuariosRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return null;
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public Collection<DtoUsuario> findAll() {
        try {
            return getJdbcTemplate().query(View.USUARIOS, new UsuariosRowMapper());
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public DtoUsuario findById(DtoUsuario object) {
        Object[] args = {object.getNombreUsuario(), object.getPassword()};
        try {
            return getJdbcTemplate().queryForObject(View.USUARIO, args, new UsuariosRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            return UsuarioFactory.newInstance();
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND)
                    + "\n" + UsuarioDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_FIND), ex);
        }
    }

    @Override
    public void ingresar(DtoUsuario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + UsuarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoUsuario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + UsuarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void eliminar(DtoUsuario object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + UsuarioDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private static class UsuariosRowMapper implements RowMapper<DtoUsuario> {

        @Override
        public DtoUsuario mapRow(ResultSet rs, int i) throws SQLException {
            DtoUsuario usuario = UsuarioFactory.newInstance();
            usuario.setIdUsuario(rs.getInt(SqlUsuario.ID_USUARIO));
            usuario.setNoTelefono(rs.getString(SqlUsuario.NO_TELEFONO));
            usuario.setNombreCompleto(rs.getString(SqlUsuario.NOMBRE_COMPLETO));
            usuario.setNombreUsuario(rs.getString(SqlUsuario.NOMBRE_USUARIO));
            usuario.setPassword(rs.getString(SqlUsuario.PASSWORD));
            usuario.setPuesto(rs.getString(SqlUsuario.PUESTO));
            return usuario;
        }
    }
}
