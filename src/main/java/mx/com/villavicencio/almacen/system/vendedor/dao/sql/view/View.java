package mx.com.villavicencio.almacen.system.vendedor.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.system.vendedor.dao.sql.sql.SqlVendedor;




/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_VENDEDOR = "vw_vendedores";

    /* QUERY VENDEDORES */
    public static final String VENDEDORES;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_VENDEDOR).append(SEMICOLON);
        VENDEDORES = qry.toString();
    }
    /* FIN QUERY VENDEDORES */

    /* QUERY VENDEDOR */
    public static final String VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_VENDEDOR).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        VENDEDOR = qry.toString();
    }
    /* FIN QUERY VENDEDOR */
}
