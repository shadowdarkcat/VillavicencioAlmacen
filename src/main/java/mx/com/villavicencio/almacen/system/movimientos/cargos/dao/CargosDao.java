package mx.com.villavicencio.almacen.system.movimientos.cargos.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;

/**
 *
 * @author Gabriel J
 */
public interface CargosDao extends GenericDao<DtoCargos> {

    DtoCargos insert(DtoCargos object);
}
