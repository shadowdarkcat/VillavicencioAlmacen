package mx.com.villavicencio.almacen.system.productos.datos.bo;

import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;


/**
 *
 * @author Gabriel J
 */
public interface DatosBo {

    void productoEstablecidoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto);

    void productoPersonalizadoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto);

    void productoEstablecidoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto);

    void productoPersonalizadoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto);

    Boolean verifyExistEstablecidoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto);

    Boolean verifyExistPersonalizadoVendedor(DtoUsuario user, DtoVendedor vendedor, DtoProducto producto);

    Boolean verifyExistEstablecidoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto);

    Boolean verifyExistPersonalizadoCliente(DtoUsuario user, DtoCliente cliente, DtoProducto producto);
}
