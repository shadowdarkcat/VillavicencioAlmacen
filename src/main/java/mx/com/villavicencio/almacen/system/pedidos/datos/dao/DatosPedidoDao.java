package mx.com.villavicencio.almacen.system.pedidos.datos.dao;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.pedidos.datos.dto.DtoDatosPedido;

/**
 *
 * @author Gabriel J
 */
public interface DatosPedidoDao extends GenericDao<DtoDatosPedido> {

    Collection<DtoDatosPedido> findDatosById(DtoDatosPedido object);
}
