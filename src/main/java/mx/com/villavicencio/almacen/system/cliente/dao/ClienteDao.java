package mx.com.villavicencio.almacen.system.cliente.dao;

import java.util.Collection;
import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;

/**
 *
 * @author Gabriel J
 */
public interface ClienteDao extends GenericDao<DtoCliente> {

    Collection<DtoCliente> findCollectionById(DtoCliente object);
}
