package mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.sql.SqlNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_NOTA_VENTA = "vw_notaVenta";

    /* QUERY NOTAS VENTA */
    public static final String VIEW_NOTAS_VENTA;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_NOTA_VENTA)
                .append(SEMICOLON);
        VIEW_NOTAS_VENTA = qry.toString();
    }
    /* FIN QUERY NOTAS VENTA */

    /* QUERY NOTA VENTA */
    public static final String VIEW_NOTA_VENTA;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_NOTA_VENTA)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlNotaVenta.ID_NOTA_VENTA).append(EQUALS).append(PARAMETER)
                .append(SEMICOLON);
        VIEW_NOTA_VENTA = qry.toString();
    }
    /* FIN QUERY NOTA VENTA */
}
