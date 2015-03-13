package mx.com.villavicencio.almacen.system.pedidos.pedido.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.exception.MessageException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.commons.response.Response;
import mx.com.villavicencio.almacen.generics.content.Content;
import mx.com.villavicencio.almacen.generics.dispatcher.Dispatcher;
import mx.com.villavicencio.almacen.generics.formats.Formats;
import mx.com.villavicencio.almacen.generics.print.ClosePrint;
import mx.com.villavicencio.almacen.generics.service.Service;
import mx.com.villavicencio.almacen.generics.text.Text;
import mx.com.villavicencio.almacen.generics.types.GenericTypes;
import mx.com.villavicencio.almacen.generics.variables.Variables;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.security.SecurityUtils;
import mx.com.villavicencio.almacen.system.cliente.bo.ClienteBo;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.inventario.bo.InventarioBo;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.inventario.factory.InventarioFactory;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.system.pedidos.detalle.factory.DetallePedidoFactory;
import mx.com.villavicencio.almacen.system.pedidos.pedido.bo.PedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.factory.PedidoFactory;
import mx.com.villavicencio.almacen.system.productos.bo.ProductoBo;
import mx.com.villavicencio.almacen.system.productos.dto.DtoProducto;
import mx.com.villavicencio.almacen.system.productos.factory.ProductoFactory;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.bo.VendedorBo;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;
import mx.com.villavicencio.almacen.utils.TableUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class PedidosController extends HttpServlet {

    private DtoUsuario user;
    private RequestDispatcher dispatcher;
    private PedidoBo pedidoBo;
    private InventarioBo inventarioBo;
    private ClienteBo clienteBo;
    private VendedorBo vendedorBo;
    private ProductoBo productoBo;

    private Gson gson;

    private static final int INIT_PEDIDO = 0;
    private static final int GET_PEDIDO = 1;
    private static final int GET_DETALLE_PEDIDO = 2;
    private static final int SET_NOTA_VENTA = 3;
    private static final int GET_PRODUCTOS = 4;
    private static final int GET_PRODUCTO = 5;
    private static final int UPDATE_NOTA = 6;
    private static final int DELETE_PEDIDO = 7;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        pedidoBo = (PedidoBo) context.getBean(Service.PEDIDO_SERVICE);
        inventarioBo = (InventarioBo) context.getBean(Service.INVENTARIO_SERVICE);
        clienteBo = (ClienteBo) context.getBean(Service.CLIENTE_SERVICE);
        vendedorBo = (VendedorBo) context.getBean(Service.VENDEDOR_SERVICE);
        productoBo = (ProductoBo) context.getBean(Service.PRODUCTO_SERVICE);
        gson = new GsonBuilder().setDateFormat(Formats.DATE_FORMAT).create();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        user = (DtoUsuario) request.getSession(false).getAttribute(Variables.USER);
        try {
            response = Response.getHttpServletResponse(response);
            request.setCharacterEncoding(Content.UTF8);

            if (SecurityUtils.isValidUrl(request) && SecurityUtils.isValidSession(request)) {
                method(NumberUtils.stringToNumber(request.getParameter(Content.METHOD)), request, response);
            } else {
                throw new ApplicationException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            }
        } catch (ApplicationException | IOException | NumberFormatException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        }
    }

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DtoPedido pedido;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_PEDIDO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                case GET_PEDIDO:
                    pedido = getPedidoFromRequest(request);
                    pedido.setOpcion(GenericTypes.THREE);
                    pedidoBo.updateStatus(user, pedido);
                    pedido = pedidoBo.findById(user, pedido);
                    pedido.setNotaPedido(TableUtils.createTableNotaPedido(request.getParameter(Variables.CONTEXT), NumberUtils.stringToNumber(request.getParameter(Text.ID_ACTION)), pedido, user, inventarioBo));
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case GET_DETALLE_PEDIDO:
                    pedido = getPedidoFromRequest(request);
                    pedido = pedidoBo.findById(user, pedido);
                    out = response.getWriter();
                    out.print(gson.toJson(pedido));
                    break;

                case SET_NOTA_VENTA:
                    if ((!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PEDIDO)))
                            && (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_DETALLE)))) {
                        DtoDetallePedido detalle = DetallePedidoFactory.newInstance(request.getParameter(Text.ID_DETALLE));
                        pedido = PedidoFactory.newInstance(request.getParameter(Text.ID_PEDIDO));
                        pedido.setDetalle(detalle);
                        this.pedidoBo.deleteProducto(user, pedido);
                        out = response.getWriter();
                        out.print(gson.toJson(Boolean.TRUE));
                    }
                    break;

                case GET_PRODUCTOS:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CLIENTE))) {
                        out = response.getWriter();
                        DtoCliente cliente = ClienteFactory.newInstance(request.getParameter(Text.ID_CLIENTE));
                        cliente = this.clienteBo.findById(user, cliente);
                        out.print(gson.toJson(cliente));
                    } else if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR))) {
                        out = response.getWriter();
                        DtoVendedor vendedor = VendedorFactory.newInstance(request.getParameter(Text.ID_VENDEDOR));
                        vendedor = this.vendedorBo.findById(user, vendedor);
                        out.print(gson.toJson(vendedor));
                    }
                    break;

                case GET_PRODUCTO:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PRODUCTO))) {
                        out = response.getWriter();
                        DtoProducto producto = ProductoFactory.newInstance(request.getParameter(Text.ID_PRODUCTO));
                        DtoInventario inventario = InventarioFactory.newInstance();
                        Integer idCliente = (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CLIENTE_NEW)) ? NumberUtils.stringToNumber(request.getParameter(Text.ID_CLIENTE_NEW)) : null);
                        Integer idVendedor = (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR_NEW)) ? NumberUtils.stringToNumber(request.getParameter(Text.ID_VENDEDOR_NEW)) : null);
                        Integer tipoProducto = (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TIPO_PRODUCTO)) ? NumberUtils.stringToNumber(request.getParameter(Text.TIPO_PRODUCTO)) : null);
                        if ((!NumberUtils.isReallyEmptyOrNull(idCliente)) && (tipoProducto == 0)) {
                            producto = this.productoBo.findProductoEstablecidoClienteById(user, producto, idCliente);
                        } else if ((!NumberUtils.isReallyEmptyOrNull(idCliente)) && (tipoProducto == 1)) {
                            producto = this.productoBo.findProductoPersonalizadoClienteById(user, producto, idCliente);
                        } else if ((!NumberUtils.isReallyEmptyOrNull(idVendedor)) && (tipoProducto == 0)) {
                            producto = this.productoBo.findProductoEstablecidoVendedorById(user, producto, idVendedor);
                        } else if ((!NumberUtils.isReallyEmptyOrNull(idVendedor)) && (tipoProducto == 1)) {
                            producto = this.productoBo.findProductoPersonalizadoVendedorById(user, producto, idVendedor);
                        }
                        inventario.setNombreProducto(producto.getNombreProducto());
                        producto.setIsAgotado(this.inventarioBo.isAgotado(user, inventario));
                        out.print(gson.toJson(producto));
                    }
                    break;

                case UPDATE_NOTA:
                    pedido = getPedidoFromRequest(request);
                    pedido.setDetalle(getDetalleFromRequest(request));
                    pedido.setOpcion(GenericTypes.MODIFY);
                    pedidoBo.modificar(user, pedido);
                    out = response.getWriter();
                    out.print(gson.toJson(Boolean.TRUE));
                    break;

                case DELETE_PEDIDO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    pedido = getPedidoFromRequest(request);
                    pedido.setOpcion(GenericTypes.DELETE);
                    pedidoBo.eliminar(user, pedido);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                default:
                    Exception ex = MessageException.messageException(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO), PropertiesBean.getErrorFile().getProperty(Property.OPCION_INVALIDA));
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
                    ApplicationMessages.errorMessage(ex.getMessage(), ex.getCause());
                    request.setAttribute(Variables.EXCEPTION, ex);
                    dispatcher.forward(request, response);
            }
        } catch (ApplicationException | IOException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        } finally {
            if (out != null) {
                ClosePrint.closePrintWriter(out);
            }
        }
    }

    private DtoPedido getPedidoFromRequest(HttpServletRequest request) {
        DtoPedido pedido = PedidoFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PEDIDO))) {
            pedido.setIdPedido(NumberUtils.stringToNumber(request.getParameter(Text.ID_PEDIDO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.STATUS))) {
            pedido.setStatusAlmacen(StringUtils.stringToChar(request.getParameter(Text.STATUS)));
        }

        pedido.setCliente(getClienteFromRequest(request));
        pedido.setVendedor(getVendedorFromRequest(request));
        return pedido;
    }

    private DtoCliente getClienteFromRequest(HttpServletRequest request) {
        DtoCliente cliente = ClienteFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_CLIENTE))) {
            cliente.setIdCliente(NumberUtils.stringToNumber(request.getParameter(Text.ID_CLIENTE)));
        }
        return cliente;
    }

    private DtoVendedor getVendedorFromRequest(HttpServletRequest request) {
        DtoVendedor vendedor = VendedorFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_VENDEDOR))) {
            vendedor.setIdVendedor(NumberUtils.stringToNumber(request.getParameter(Text.ID_VENDEDOR)));
        }
        return vendedor;
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private DtoDetallePedido getDetalleFromRequest(HttpServletRequest request) {
        DtoDetallePedido detalle = DetallePedidoFactory.newInstance();
        detalle.setIdProducto(NumberUtils.stringToNumber(request.getParameter((Text.RESPONSE + GenericTypes.LEFT_BRACKET + "idProducto" + GenericTypes.RIGHT_BRACKET))));
        detalle.setNombreProducto(request.getParameter((Text.RESPONSE + GenericTypes.LEFT_BRACKET + "nombreProducto" + GenericTypes.RIGHT_BRACKET)));
        detalle.setCostoUnitario(NumberUtils.stringToBigDecimal(request.getParameter((Text.RESPONSE + GenericTypes.LEFT_BRACKET + "costoUnitario" + GenericTypes.RIGHT_BRACKET))));
        detalle.setCantidadPiezas(NumberUtils.stringToNumber(request.getParameter(Text.CANTIDAD_PIEZAS)));
        detalle.setPeso(request.getParameter((Text.RESPONSE + GenericTypes.LEFT_BRACKET + "peso" + GenericTypes.RIGHT_BRACKET)));
        detalle.setComision(NumberUtils.convertNumberToPorcentaje(
                NumberUtils.stringToBigDecimal(request.getParameter(Text.RESPONSE + GenericTypes.LEFT_BRACKET + "comision" + GenericTypes.RIGHT_BRACKET))));
        detalle.setPesoMinimo(NumberUtils.stringToBigDecimal(request.getParameter(Text.RESPONSE + GenericTypes.LEFT_BRACKET + "pesoMinimo" + GenericTypes.RIGHT_BRACKET)));
        detalle.setPesoMaximo(NumberUtils.stringToBigDecimal(request.getParameter(Text.RESPONSE + GenericTypes.LEFT_BRACKET + "pesoMaximo" + GenericTypes.RIGHT_BRACKET)));

        if (Boolean.valueOf(request.getParameter(Text.CHK_MUESTRA))) {
            detalle.setIsMuestra(Boolean.TRUE);
        } else {
            detalle.setIsMuestra(Boolean.FALSE);
        }

        return detalle;
    }

}
