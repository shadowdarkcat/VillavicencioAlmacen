package mx.com.villavicencio.almacen.system.menu.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.menu.dao.MenuDao;
import mx.com.villavicencio.almacen.system.menu.dao.sql.sql.SqlMenu;
import mx.com.villavicencio.almacen.system.menu.dto.MenuItem;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 *
 * @author Gabriel J
 */
public class MenuDaoImpl extends JdbcDaoSupport implements MenuDao {

    @Override
    public Collection<MenuItem> getMenu(DtoUsuario user) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {0};
            menu = getJdbcTemplate().query(SqlMenu.MENU, params, new MenuRowMapper());
            for (MenuItem item : menu) {
                item.setChildItems(getChildItems(item));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU), ex);
        }
        return menu;
    }

    @Override
    public Collection<MenuItem> getMenuPrivilegios(DtoUsuario user) {
        Collection<MenuItem> menu = null;
        Collection<Integer[]> privilegiosIds;
        try {
            Object[] params = {user.getIdUsuario()};
            privilegiosIds = getJdbcTemplate().query(SqlMenu.PRIVILEGIOS, params, new MenuPrivilegioRowMapper());
            menu = getMenu(user);
            menu = filtrarMenuUsuarios(menu, privilegiosIds);

        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_MENU_PRIVILEGIOS));
        }
        return menu;
    }

    @Override
    public void insertMultiplesPrivilegios(DtoUsuario usuario, Collection<MenuItem> nuevoMenu) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + MenuDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void insertPrivilegio(DtoUsuario usuario, MenuItem menuItem) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + MenuDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void deletePrivilegios(DtoUsuario usuario) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO)
                + "\n" + MenuDaoImpl.class.getSimpleName());
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    private Collection<MenuItem> getChildItems(MenuItem item) {
        Collection<MenuItem> menu = null;

        try {
            Object[] params = {item.getId()};
            menu = getJdbcTemplate().query(SqlMenu.MENU, params, new MenuRowMapper());
            for (MenuItem itemS : menu) {
                itemS.setParentItem(item);
                itemS.setChildItems(getChildItems(itemS));
            }
        } catch (DataAccessException ex) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU)
                    + "\n" + MenuDaoImpl.class.getSimpleName(), ex);
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ERROR_ROW_MENU), ex);
        }
        return menu;
    }

    private Collection<MenuItem> filtrarMenuUsuarios(Collection<MenuItem> menu, Collection<Integer[]> privilegiosIds) {
        Iterator<MenuItem> recorrerMenux = menu.iterator();
        while (recorrerMenux.hasNext()) {
            MenuItem menuItem = recorrerMenux.next();

            boolean bandera = true;
            for (Integer[] recorridoId : privilegiosIds) {
                if (recorridoId[0].intValue() == menuItem.getId() && bandera) {
                    bandera = false;
                }
            }
            if (bandera) {
                recorrerMenux.remove();
            } else {
                if (menuItem.getChildItems() != null && menuItem.getChildItems().size() > 0) {
                    menuItem.setChildItems(filtrarMenuUsuarios(menuItem.getChildItems(), privilegiosIds));
                }
            }
        }
        return menu;
    }

    static class MenuRowMapper implements RowMapper<MenuItem> {

        @Override
        public MenuItem mapRow(ResultSet rs, int i) throws SQLException {
            MenuItem menuItem = new MenuItem();
            menuItem.setId(rs.getInt(SqlMenu.ID));
            menuItem.setLink(rs.getString(SqlMenu.LINK));
            menuItem.setLeyenda(rs.getString(SqlMenu.TEXTO));
            menuItem.setToolTip(rs.getString(SqlMenu.DESCRIPCION));
            menuItem.setTarget(rs.getString(SqlMenu.TARGET));
            return menuItem;
        }

    }

    static class MenuPrivilegioRowMapper implements RowMapper<Integer[]> {

        @Override
        public Integer[] mapRow(ResultSet rs, int i) throws SQLException {
            Integer[] privilegios = new Integer[2];
            privilegios[0] = rs.getInt(SqlMenu.ID_MENU);
            privilegios[1] = rs.getInt(SqlMenu.ID_USUARIO);
            return privilegios;
        }

    }

}
