package mx.com.villavicencio.almacen.system.menu.bo.impl;

import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.menu.bo.MenuBo;
import mx.com.villavicencio.almacen.system.menu.dao.MenuDao;
import mx.com.villavicencio.almacen.system.menu.dto.MenuItem;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public class MenuBoImpl implements MenuBo {

    private MenuDao menuDao;

    @Override
    public Collection<MenuItem> getMenu(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenu(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Collection<MenuItem> getMenuPrivilegios(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            return this.menuDao.getMenuPrivilegios(user);
        } else {
            ApplicationMessages.warnMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO)
            );
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public String getConvercionMenu(DtoUsuario user) {
        return primerRecorridoMenu(getMenu(user), getMenuPrivilegios(user));
    }

    private String primerRecorridoMenu(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        str.append("<ul id='nodoPrincipal' class='expandIcon'>");
        str.append(recorridoMenu(menu, menuDeUsuario));
        str.append("</ul>");
        return str.toString();
    }

    private String recorridoMenu(Collection<MenuItem> menu, Collection<MenuItem> menuDeUsuario) {
        StringBuilder str = new StringBuilder();
        Integer index = 0;
        if (menu != null && !menu.isEmpty()) {
            for (MenuItem menuHijo : menu) {
                if ((menuHijo.getLink() == null) || (menuHijo.getLink() == "null")) {
                    if (index != 0) {
                        str.append("</fieldset>");
                    }
                    str.append("<fieldset>")
                            .append("<li><input type='checkbox' name='idMenu' id='idMenu' class='parentCheckBox'");
                } else {
                    str.append("<li><input type='checkbox' name='idMenu' id='idMenu' class='childCheckBox'");
                }
                index = index + 1;
                str.append(existeNodo(menuHijo, menuDeUsuario) ? "" : "checked")
                        .append(" value='")
                        .append(menuHijo.getId())
                        .append("'><span class='text-muted'>")
                        .append(menuHijo.getLeyenda())
                        .append("</span>");
                if (menuHijo.getChildItems() != null && !menuHijo.getChildItems().isEmpty()) {
                    str.append("<ul>");
                    str.append(recorridoMenu(menuHijo.getChildItems(), menuDeUsuario));
                    str.append("</ul>");
                }
                str.append("</li>");
            }
        }
        return str.toString();
    }

    private boolean existeNodo(MenuItem nodo, Collection<MenuItem> menuDeUsuario) {
        if (menuDeUsuario.contains(nodo)) {
            return true;
        } else {
            boolean bandera = false;
            for (MenuItem menuItem : menuDeUsuario) {
                if (menuItem.getChildItems() != null && !menuItem.getChildItems().isEmpty()) {
                    if (bandera) {
                        return true;
                    } else {
                        bandera = existeNodo(nodo, menuItem.getChildItems());
                    }
                }
            }
            return bandera;
        }
    }

    public void setMenuDao(MenuDao menuDao) {
        this.menuDao = menuDao;
    }
}
