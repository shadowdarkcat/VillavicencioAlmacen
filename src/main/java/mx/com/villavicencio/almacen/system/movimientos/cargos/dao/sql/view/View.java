package mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dao.sql.sql.SqlCargos;

/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_CARGOS = "vw_cargos";
    /* QUERY CARGO */
    public static final String VIEW_CARGO;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL)
                .append(NEW_LINE).append(FROM).append(NEW_LINE).append(TAB)
                .append(TBL_CARGOS).append(NEW_LINE).append(WHERE)
                .append(NEW_LINE).append(TAB).append(SqlCargos.ID_CARGOS)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);

        VIEW_CARGO = qry.toString();
    }
    /* QUERY CARGO */
}
