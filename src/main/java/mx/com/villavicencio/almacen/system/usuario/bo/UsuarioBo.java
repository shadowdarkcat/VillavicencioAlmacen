package mx.com.villavicencio.almacen.system.usuario.bo;

import java.util.Collection;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface UsuarioBo {

    Collection<DtoUsuario> findAll(DtoUsuario user);

    DtoUsuario validar(DtoUsuario user);

    String getMenu(DtoUsuario user);
    
    DtoUsuario findById(DtoUsuario user, DtoUsuario object);
}
