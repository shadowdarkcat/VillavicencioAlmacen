package mx.com.villavicencio.almacen.system.usuario.dao;

import mx.com.villavicencio.almacen.generics.spring.dao.GenericDao;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;

/**
 *
 * @author Gabriel J
 */
public interface UsuarioDao extends GenericDao<DtoUsuario> {

    DtoUsuario validateUsuario(DtoUsuario object);
}
