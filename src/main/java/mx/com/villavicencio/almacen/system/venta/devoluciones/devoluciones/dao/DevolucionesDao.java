package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;

/**
 *
 * @author Gabriel J
 */
public interface DevolucionesDao extends GenericDao<DtoDevoluciones>{

    DtoDevoluciones insert(DtoDevoluciones object);
}
