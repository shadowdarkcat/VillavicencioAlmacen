package mx.com.villavicencio.almacen.system.pedidos.pedido.bo.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.inventario.bo.InventarioBo;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.inventario.factory.InventarioFactory;
import mx.com.villavicencio.almacen.system.pedidos.datos.bo.DatosPedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.almacen.system.pedidos.datos.factory.DatosPedidoFactory;
import mx.com.villavicencio.almacen.system.pedidos.detalle.bo.DetalleBo;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.system.pedidos.detalle.factory.DetallePedidoFactory;
import mx.com.villavicencio.almacen.system.pedidos.pedido.bo.PedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dao.PedidoDao;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.bo.NotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;

/**
 *
 * @author Gabriel J
 */
public class PedidoBoImpl implements PedidoBo {

    private PedidoDao pedidoDao;
    private DetalleBo detalleBo;
    private DatosPedidoBo datosBo;
    private ClienteBo clienteBo;
    private VendedorBo vendedorBo;
    private InventarioBo inventarioBo;
    private NotaVentaBo notaVentaBo;

    @Override
    public Collection<DtoPedido> findAll(DtoUsuario user) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoPedido> collection = new ArrayList<>();
            for (DtoPedido pedidos : this.pedidoDao.findAll()) {
                DtoDatosPedido datos = DatosPedidoFactory.newInstance();
                DtoPedido pedido = pedidos;
                datos.setIdPedido(pedido.getIdPedido());
                datos = this.datosBo.findById(user, datos);
                if ((datos.getIdCliente() != null) && (datos.getIdCliente() != 0)) {
                    DtoCliente cliente = ClienteFactory.newInstance(datos.getIdCliente());
                    pedido.setCliente(this.clienteBo.findById(user, cliente));
                    pedido = findCredito(user, pedido);
                } else if ((datos.getIdVendedor() != null) && (datos.getIdVendedor() != 0)) {
                    DtoVendedor vendedor = VendedorFactory.newInstance(datos.getIdVendedor());
                    pedido.setVendedor(this.vendedorBo.findById(user, vendedor));
                    pedido = findCredito(user, pedido);
                }
                collection.add(pedido);
            }
            return collection;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoPedido findById(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            Collection<DtoDetallePedido> collection = new ArrayList<>();
            DtoCliente cliente = object.getCliente();
            DtoVendedor vendedor = object.getVendedor();
            object = this.pedidoDao.findById(object);
            object.setCliente(cliente);
            object.setVendedor(vendedor);
            DtoDatosPedido datos = DatosPedidoFactory.newInstance();
            if ((object.getCliente() != null) && (object.getCliente().getIdCliente() != null)) {
                datos.setIdCliente(object.getCliente().getIdCliente());
                object.setCliente(this.clienteBo.findById(user, object.getCliente()));

            } else if ((object.getVendedor() != null) && (object.getVendedor().getIdVendedor() != null)) {
                datos.setIdVendedor(object.getVendedor().getIdVendedor());
                object.setVendedor(this.vendedorBo.findById(user, object.getVendedor()));
            }
            datos.setIdPedido(object.getIdPedido());
            Collection<DtoDatosPedido> findDatosById = this.datosBo.findDatosById(user, datos);
            for (DtoDatosPedido detalles : findDatosById) {
                DtoDetallePedido detalle = DetallePedidoFactory.newInstance(detalles.getIdDetallePedido());
                detalle.setComision(NumberUtils.convertPorcentajeToNumber(detalle.getComision()));
                DtoDetallePedido findById = this.detalleBo.findById(user, detalle);
                DtoInventario inventario = InventarioFactory.newInstance();
                inventario.setNombreProducto(findById.getNombreProducto());
                findById.setIsAgotado(inventarioBo.isAgotado(user, inventario));
                collection.add(findById);
            }
            object.setDetalles(collection);
            return object;
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoPedido findCredito(DtoUsuario user, DtoPedido object) {
        if ((object.getCliente().getCredito() != null) && (!StringUtils.isReallyEmptyOrNull(object.getCliente().getCredito().getFolioNota()))) {
            if (("PLAZO".equals(object.getCliente().getCredito().getTipoCredito())) || ("MONETARIO".equals(object.getCliente().getCredito().getTipoCredito()))) {
                BigDecimal cargo = BigDecimal.ZERO;
                BigDecimal credito = object.getCliente().getCredito().getCantidadMonetaria();
                BigDecimal disponible;
                if (!object.getCliente().getCredito().getFolioNota().contains(",")) {
                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta.setFolio(object.getCliente().getCredito().getFolioNota());
                    notaVenta.setIdNotaVenta(this.notaVentaBo.findByFolio(user, notaVenta));
                    notaVenta = this.notaVentaBo.findById(user, notaVenta);
                    for (DtoDetalleNotaVenta detalles : notaVenta.getDetalles()) {
                        cargo = cargo.add(detalles.getSubTotal());
                    }
                    disponible = credito.subtract(cargo);
                    object.getCliente().setCantidadMonetaria(disponible);
                } else {
                    for (String str : StringUtils.split(object.getCliente().getCredito().getFolioNota())) {
                        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                        notaVenta.setFolio(str);
                        notaVenta.setIdNotaVenta(this.notaVentaBo.findByFolio(user, notaVenta));
                        notaVenta = this.notaVentaBo.findById(user, notaVenta);
                        for (DtoDetalleNotaVenta detalles : notaVenta.getDetalles()) {
                            cargo = cargo.add(detalles.getSubTotal());
                        }
                    }
                    disponible = credito.subtract(cargo);
                    object.getCliente().setCantidadMonetaria(disponible);
                }
            }
        } else if ((object.getVendedor().getCredito() != null) && (!StringUtils.isReallyEmptyOrNull(object.getVendedor().getCredito().getFolioNota()))) {
            if (("PLAZO".equals(object.getVendedor().getCredito().getTipoCredito())) || ("MONETARIO".equals(object.getVendedor().getCredito().getTipoCredito()))) {
                BigDecimal cargo = BigDecimal.ZERO;
                BigDecimal credito = object.getVendedor().getCredito().getCantidadMonetaria();
                BigDecimal disponible;
                if (!object.getVendedor().getCredito().getFolioNota().contains(",")) {
                    DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                    notaVenta.setFolio(object.getVendedor().getCredito().getFolioNota());
                    notaVenta.setIdNotaVenta(this.notaVentaBo.findByFolio(user, notaVenta));
                    notaVenta = this.notaVentaBo.findById(user, notaVenta);
                    for (DtoDetalleNotaVenta detalles : notaVenta.getDetalles()) {
                        cargo = cargo.add(detalles.getSubTotal());
                    }
                    disponible = credito.subtract(cargo);
                    object.getVendedor().setCantidadMonetaria(disponible);
                } else {
                    for (String str : StringUtils.split(object.getCliente().getCredito().getFolioNota())) {
                        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
                        notaVenta.setFolio(str);
                        notaVenta.setIdNotaVenta(this.notaVentaBo.findByFolio(user, notaVenta));
                        notaVenta = this.notaVentaBo.findById(user, notaVenta);
                        for (DtoDetalleNotaVenta detalles : notaVenta.getDetalles()) {
                            cargo = cargo.add(detalles.getSubTotal());
                        }
                    }
                    disponible = credito.subtract(cargo);
                    object.getVendedor().setCantidadMonetaria(disponible);
                }
            }
        }
        return object;
    }

    @Override
    public void ingresar(DtoUsuario user, DtoPedido object) {
        ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
    }

    @Override
    public void modificar(DtoUsuario user, DtoPedido object) {
        Boolean flag = Boolean.FALSE;
        Integer opcion = object.getOpcion();
        DtoDetallePedido request = object.getDetalle();
        if ((user.getIdUsuario() != 0) && (user != null)) {
            DtoDatosPedido datos = DatosPedidoFactory.newInstance();
            if ((object.getCliente() != null) && (object.getCliente().getIdCliente() != null)) {
                datos.setIdCliente(object.getCliente().getIdCliente());
            } else if ((object.getVendedor() != null) && (object.getVendedor().getIdVendedor() != null)) {
                datos.setIdVendedor(object.getVendedor().getIdVendedor());
            }
            datos.setIdPedido(object.getIdPedido());
            Collection<DtoDatosPedido> findDatosById = this.datosBo.findDatosById(user, datos);
            for (DtoDatosPedido detalles : findDatosById) {
                DtoDetallePedido detalle = DetallePedidoFactory.newInstance(detalles.getIdDetallePedido());
                DtoDetallePedido findById = this.detalleBo.findById(user, detalle);
                if (Objects.equals(request.getNombreProducto(), findById.getNombreProducto())
                        && (!request.getIsMuestra())) {
                    request.setOpcion(opcion);
                    Integer cantidad = findById.getCantidadPiezas() + request.getCantidadPiezas();
                    request.setIdDetallePedido(findById.getIdDetallePedido());
                    request.setCantidadPiezas(cantidad);
                    this.detalleBo.modificar(user, request);
                    flag = Boolean.TRUE;
                    break;
                }
            }
            if (!Objects.equals(flag, Boolean.TRUE)) {
                datos = DatosPedidoFactory.newInstance();
                request.setOpcion(GenericTypes.INSERT);
                request = this.detalleBo.insert(user, request);
                datos.setIdCliente(object.getCliente().getIdCliente() != null ? object.getCliente().getIdCliente() : null);
                datos.setIdDetallePedido(request.getIdDetallePedido());
                datos.setIdPedido(object.getIdPedido());
                datos.setIdVendedor(object.getVendedor().getIdVendedor() != null ? object.getVendedor().getIdVendedor() : null);
                this.datosBo.ingresar(user, datos);
            }

        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.pedidoDao.eliminar(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void deleteProducto(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.pedidoDao.deleteProducto(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void updateStatus(DtoUsuario user, DtoPedido object) {
        if ((user.getIdUsuario() != 0) && (user != null)) {
            this.pedidoDao.updateStatus(object);
        } else {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setPedidoDao(PedidoDao pedidoDao) {
        this.pedidoDao = pedidoDao;
    }

    public void setDetalleBo(DetalleBo detalleBo) {
        this.detalleBo = detalleBo;
    }

    public void setDatosBo(DatosPedidoBo datosBo) {
        this.datosBo = datosBo;
    }

    public void setClienteBo(ClienteBo clienteBo) {
        this.clienteBo = clienteBo;
    }

    public void setVendedorBo(VendedorBo vendedorBo) {
        this.vendedorBo = vendedorBo;
    }

    public void setInventarioBo(InventarioBo inventarioBo) {
        this.inventarioBo = inventarioBo;
    }

    public void setNotaVentaBo(NotaVentaBo notaVentaBo) {
        this.notaVentaBo = notaVentaBo;
    }
}
