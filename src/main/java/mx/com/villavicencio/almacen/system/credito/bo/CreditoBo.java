package mx.com.villavicencio.almacen.system.credito.bo;

import mx.com.villavicencio.almacen.generics.spring.bo.GenericBo;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface CreditoBo extends GenericBo<DtoUsuario, DtoCredito> {

    DtoCredito findCreditoByIdVendedor(DtoUsuario user, DtoCredito object, Integer id);
    
    DtoCredito findCreditoByIdCliente(DtoUsuario user, DtoCredito object, Integer id);
}
