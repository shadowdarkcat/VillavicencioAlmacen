package mx.com.villavicencio.almacen.system.credito.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;

/**
 *
 * @author Gabriel J
 */
public interface CreditoDao extends GenericDao<DtoCredito> {

   
    DtoCredito findCreditoByIdVendedor(DtoCredito object, Integer id);
    
    DtoCredito findCreditoByIdCliente(DtoCredito object, Integer id);
}
