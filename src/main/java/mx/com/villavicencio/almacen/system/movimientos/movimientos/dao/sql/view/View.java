package mx.com.villavicencio.almacen.system.movimientos.movimientos.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao.sql.sql.SqlDevoluciones;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.sql.sql.SqlNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_MOVIMIENTOS = "vw_movimientos";

    /* QUERY MOVIMIENTO */
    public static final String VIEW_MOVIMIENTO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_MOVIMIENTOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlNotaVenta.ID_NOTA_VENTA).append(EQUALS).append(PARAMETER)
                .append(NEW_LINE).append(LIMIT).append(GenericTypes.ONE)
                .append(SEMICOLON);
        VIEW_MOVIMIENTO = qry.toString();
    }
    /* FIN QUERY MOVIMIENTO*/

    /* QUERY MOVIMIENTO POR DEVOLUCION */
    public static final String VIEW_MOVIMIENTO_DEVOLUCION;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_MOVIMIENTOS)
                .append(NEW_LINE).append(WHERE).append(NEW_LINE).append(TAB)
                .append(SqlDevoluciones.ID_DEVOLUCIONES).append(EQUALS).append(PARAMETER)
                .append(NEW_LINE).append(LIMIT).append(GenericTypes.ONE)
                .append(SEMICOLON);
        VIEW_MOVIMIENTO_DEVOLUCION = qry.toString();
    }
    /* FIN QUERY MOVIMIENTO POR DEVOLUCION */
}
