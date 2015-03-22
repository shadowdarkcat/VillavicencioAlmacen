package mx.com.villavicencio.almacen.system.venta.nota.nota.bo.impl;

import java.math.BigDecimal;
import java.util.Collection;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.credito.bo.CreditoBo;
import mx.com.villavicencio.almacen.system.credito.dto.DtoCredito;
import mx.com.villavicencio.almacen.system.credito.factory.CreditoFactory;
import mx.com.villavicencio.almacen.system.movimientos.cargos.bo.CargosBo;
import mx.com.villavicencio.almacen.system.movimientos.cargos.dto.DtoCargos;
import mx.com.villavicencio.almacen.system.movimientos.cargos.factory.CargosFactory;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.bo.MovimientosBo;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.dto.DtoMovimientos;
import mx.com.villavicencio.almacen.system.movimientos.movimientos.factory.MovimientosFactory;
import mx.com.villavicencio.almacen.system.pedidos.datos.bo.DatosPedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.datos.dto.DtoDatosPedido;
import mx.com.villavicencio.almacen.system.pedidos.datos.factory.DatosPedidoFactory;
import mx.com.villavicencio.almacen.system.pedidos.pedido.bo.PedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.bo.DetalleNotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.almacen.system.venta.nota.nota.bo.NotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dao.NotaVentaDao;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.utils.StringUtils;
import mx.com.villavicencio.almacen.utils.TraductorUtils;

/**
 *
 * @author Gabriel J
 */
public class NotaVentaBoImpl implements NotaVentaBo {

    private NotaVentaDao notaVentaDao;
    private DetalleNotaVentaBo detalleNotaVentaBo;
    private CargosBo cargosBo;
    private MovimientosBo movimientosBo;
    private PedidoBo pedidoBo;
    private DatosPedidoBo datosBo;
    private ClienteBo clienteBo;
    private VendedorBo vendedorBo;
    private CreditoBo creditoBo;

    @Override
    public Collection<DtoNotaVenta> findAll(DtoUsuario user) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.notaVentaDao.findAll();
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoNotaVenta findById(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            BigDecimal total = BigDecimal.ZERO;
            object = this.notaVentaDao.findById(object);
            DtoDetalleNotaVenta detalleNotaVenta = DetalleNotaVentaFactory.newInstance();
            detalleNotaVenta.setIdNotaVenta(object.getIdNotaVenta());
            object.setDetalles(this.detalleNotaVentaBo.findAll(user, detalleNotaVenta));
            DtoDatosPedido datos = DatosPedidoFactory.newInstance();
            DtoPedido pedido = object.getPedido();
            datos.setIdPedido(pedido.getIdPedido());
            datos = this.datosBo.findById(user, datos);
            if ((datos.getIdCliente() != null) && (datos.getIdCliente() != 0)) {
                DtoCliente cliente = ClienteFactory.newInstance(datos.getIdCliente());
                pedido.setCliente(this.clienteBo.findById(user, cliente));
            } else if ((datos.getIdVendedor() != null) && (datos.getIdVendedor() != 0)) {
                DtoVendedor vendedor = VendedorFactory.newInstance(datos.getIdVendedor());
                pedido.setVendedor(this.vendedorBo.findById(user, vendedor));
            }
            if ((object.getPedido() != null) && (object.getPedido().getIdPedido() != null)) {
                object.setPedido(this.pedidoBo.findById(user, object.getPedido()));
            }
            for (DtoDetalleNotaVenta detalle : object.getDetalles()) {
                total = total.add(detalle.getSubTotal());
            }
            object.setTotal(total);
            object.setCantidadLetra(TraductorUtils.traducir(total));
            return object;
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public Integer findByFolio(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            return this.notaVentaDao.findByFolio(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void ingresar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public DtoNotaVenta insert(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            DtoCargos cargos = CargosFactory.newInstance();
            DtoMovimientos movimientos = MovimientosFactory.newInstance();
            DtoCredito credito = CreditoFactory.newInstance(object.getIdCredito());
            object = this.notaVentaDao.insert(object);
            for (DtoDetalleNotaVenta detalles : object.getDetalles()) {
                detalles.setIdNotaVenta(object.getIdNotaVenta());
                detalles.setOpcion(object.getOpcion());
                this.detalleNotaVentaBo.ingresar(user, detalles);
            }
            cargos.setCargo(object.getTotal());
            cargos.setFechaCargo(object.getFecha());
            cargos.setOpcion(object.getOpcion());
            cargos = cargosBo.insert(user, cargos);
            movimientos.setOpcion(object.getOpcion());
            movimientos.setIdCargos(cargos.getIdCargos());
            movimientos.setIdNotaVenta(object.getIdNotaVenta());
            movimientos.setIdCredito(object.getIdCredito());
            this.movimientosBo.ingresar(user, movimientos);
            object.getPedido().setOpcion(GenericTypes.THREE);
            this.pedidoBo.updateStatus(user, object.getPedido());
            DtoCredito findById = this.creditoBo.findById(user, credito);
            if (!StringUtils.isReallyEmptyOrNull(findById.getFolioNota())) {
                credito = findById;
                credito.setFolioNota(findById.getFolioNota() + "," + object.getFolio());
                credito.setOpcion(GenericTypes.MODIFY);
                this.creditoBo.modificar(user, credito);
            }
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
        return object;
    }

    @Override
    public void modificar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            this.notaVentaDao.modificar(object);
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    @Override
    public void eliminar(DtoUsuario user, DtoNotaVenta object) {
        if ((user != null) && (user.getIdUsuario() != 0)) {
            ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.NO_DESARROLLADO));
        } else {
            throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
        }
    }

    public void setNotaVentaDao(NotaVentaDao notaVentaDao) {
        this.notaVentaDao = notaVentaDao;
    }

    public void setDetalleNotaVentaBo(DetalleNotaVentaBo detalleNotaVentaBo) {
        this.detalleNotaVentaBo = detalleNotaVentaBo;
    }

    public void setCargosBo(CargosBo cargosBo) {
        this.cargosBo = cargosBo;
    }

    public void setMovimientosBo(MovimientosBo movimientosBo) {
        this.movimientosBo = movimientosBo;
    }

    public void setPedidoBo(PedidoBo pedidoBo) {
        this.pedidoBo = pedidoBo;
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

    public void setCreditoBo(CreditoBo creditoBo) {
        this.creditoBo = creditoBo;
    }
}
