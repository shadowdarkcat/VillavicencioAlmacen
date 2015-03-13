package mx.com.villavicencio.almacen.system.productos.datos.dao;

import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;


/**
 *
 * @author Gabriel J
 */
public interface DatosDao {

    void productoEstablecidoVendedor(DtoVendedor vendedor, DtoProducto producto);

    void productoPersonalizadoVendedor(DtoVendedor vendedor, DtoProducto producto);

    void productoEstablecidoCliente(DtoCliente cliente, DtoProducto producto);

    void productoPersonalizadoCliente(DtoCliente cliente, DtoProducto producto);

    Boolean verifyExistEstablecidoVendedor(DtoVendedor vendedor, DtoProducto producto);

    Boolean verifyExistPersonalizadoVendedor(DtoVendedor vendedor, DtoProducto producto);

    Boolean verifyExistEstablecidoCliente(DtoCliente cliente, DtoProducto producto);

    Boolean verifyExistPersonalizadoCliente(DtoCliente cliente, DtoProducto producto);
}
