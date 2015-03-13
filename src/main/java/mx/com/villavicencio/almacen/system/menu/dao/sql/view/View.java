package mx.com.villavicencio.almacen.system.menu.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.system.menu.dao.sql.sql.SqlMenu;
import mx.com.villavicencio.almacen.system.usuario.dao.sql.sql.SqlUsuario;


/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String VIEW_MENU = "vw_menualmacen";
    private static final String VIEW_PRIVILEGIOS = "vw_menuprivilegioalmacen";

    /* QUERY MENU */
    public static final String MENU;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_MENU).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlMenu.ID_PADRE)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        MENU = qry.toString();
    }
    /* FIN QUERY MENU */

    /* QUERY MENU PRIVILEGIOS */
    public static final String PRIVILEGIOS;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(VIEW_PRIVILEGIOS).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlUsuario.ID_USUARIO)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        PRIVILEGIOS = qry.toString();
    }
    /* FIN QUERY MENU PRIVILEGIOS */   
}
