package mx.com.villavicencio.almacen.system.cliente.dao.sql.view;

import mx.com.villavicencio.almacen.generics.spring.dao.sql.SqlSentence;
import mx.com.villavicencio.almacen.system.cliente.dao.sql.sql.SqlCliente;
import mx.com.villavicencio.almacen.system.vendedor.dao.sql.sql.SqlVendedor;


/**
 *
 * @author Gabriel J
 */
public class View extends SqlSentence {

    private static final String TBL_CLIENTE = "vw_cliente";
    private static final String TBL_CODIGO_POSTAL = "codigopostal";
    private static final String TBL_CLIENTES = "vw_clientevendedor";

    /* QUERY CLIENTES */
    public static final String CLIENTES;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CLIENTE).append(SEMICOLON);
        CLIENTES = qry.toString();
    }
    /* FIN QUERY CLIENTES */

    /* QUERY CLIENTE */
    public static final String CLIENTE;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CLIENTE).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlCliente.ID_CLIENTE)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        CLIENTE = qry.toString();
    }
    /* FIN QUERY CLIENTE */

    /* QUERY CLIENTE */
    public static final String CLIENTE_VENDEDOR;

    static {
        StringBuilder qry = new StringBuilder();
        qry.append(SELECT).append(NEW_LINE).append(TAB).append(ALL).append(NEW_LINE)
                .append(FROM).append(NEW_LINE).append(TAB).append(TBL_CLIENTE).append(NEW_LINE)
                .append(WHERE).append(NEW_LINE).append(TAB).append(SqlVendedor.ID_VENDEDOR)
                .append(EQUALS).append(PARAMETER).append(SEMICOLON);
        CLIENTE_VENDEDOR = qry.toString();
    }
    /* FIN QUERY CLIENTE */

    public static void main(String[] args) {
        System.out.println(CLIENTES);
    }
}
