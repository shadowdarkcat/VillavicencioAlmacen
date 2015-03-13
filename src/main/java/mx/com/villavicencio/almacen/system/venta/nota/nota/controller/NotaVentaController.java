package mx.com.villavicencio.almacen.system.venta.nota.nota.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.cliente.factory.ClienteFactory;
import mx.com.villavicencio.almacen.system.pedidos.pedido.bo.PedidoBo;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.factory.PedidoFactory;
import mx.com.villavicencio.almacen.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.vendedor.factory.VendedorFactory;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.factory.DetalleNotaVentaFactory;
import mx.com.villavicencio.almacen.system.venta.nota.nota.bo.NotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.almacen.utils.DateUtils;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;
import mx.com.villavicencio.almacen.utils.TableUtils;
import mx.com.villavicencio.almacen.utils.Ticket;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class NotaVentaController extends HttpServlet {

    private DtoUsuario user;
    private UsuarioBo usersBo;
    private PedidoBo pedidoBo;
    private NotaVentaBo notaVentaBo;
    private RequestDispatcher dispatcher;
    private Gson gson;

    private static final int INIT_NOTA_VENTA = 0;
    private static final int AUTORIZA_NOTA_VENTA = 1;
    private static final int GET_NOTA_VENTA = 2;
    private static final int INSERT_NOTA_VENTA = 3;
    private static final int REIMPRIMIR_NOTA_VENTA = 4;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        usersBo = (UsuarioBo) context.getBean(Service.USUARIO_SERVICE);
        pedidoBo = (PedidoBo) context.getBean(Service.PEDIDO_SERVICE);
        notaVentaBo = (NotaVentaBo) context.getBean(Service.NOTA_VENTA_SERVICE);
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
        DtoNotaVenta notaVenta;
        PrintWriter out = null;
        Ticket ticket;
        try {
            switch (method) {

                case INIT_NOTA_VENTA:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);//cambiar a nota venta                   
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;

                case AUTORIZA_NOTA_VENTA:
                    DtoUsuario validUser = usersBo.validar(getUserFromRequest(request));
                    String res;
                    out = response.getWriter();
                    if ((validUser != null) && (validUser.getPuesto().equalsIgnoreCase(Variables.PUESTO_AUTORIZA))) {
                        res = "true," + validUser.getNombreCompleto();
                        out.print(gson.toJson(res));
                    } else {
                        out.print(gson.toJson("false,"));
                    }
                    break;

                case GET_NOTA_VENTA:
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta.setPedido(this.pedidoBo.findById(user, notaVenta.getPedido()));
                    String createTableNotaPedido = TableUtils.createTableNotaPedido(notaVenta, request.getParameter(Variables.CONTEXT), request.getParameter(Text.ID_ACTION));
                    out = response.getWriter();
                    out.print(gson.toJson(createTableNotaPedido));
                    break;

                case INSERT_NOTA_VENTA:
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta.setOpcion(GenericTypes.INSERT);
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PEDIDOS);
                    notaVenta = this.notaVentaBo.insert(user, notaVenta);
                    request.setAttribute(Variables.PEDIDOS, pedidoBo.findAll(user));
                    ticket = new Ticket();
                    if (Objects.equals(ticket.imprimir(notaVenta), Boolean.TRUE)) {
                        request.setAttribute(Variables.TICKET, Boolean.TRUE);
                    } else {
                        request.setAttribute(Variables.TICKET, Boolean.FALSE);
                        request.setAttribute(Variables.NOTA_VENTA, notaVenta);
                    }
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;

                case REIMPRIMIR_NOTA_VENTA:
                    ticket = new Ticket();
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta = this.notaVentaBo.findById(user, notaVenta);
                    out = response.getWriter();
                    if (Objects.equals(ticket.imprimir(notaVenta), Boolean.TRUE)) {
                        out.print(gson.toJson(Boolean.TRUE));
                    } else {
                        out.print(gson.toJson(Boolean.FALSE));
                    }
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

    private DtoUsuario getUserFromRequest(HttpServletRequest request) {
        DtoUsuario users = UsuarioFactory.newInstance();
        users.setNombreUsuario(request.getParameter(Text.USER).toUpperCase());
        users.setPassword(request.getParameter(Text.PASSWORD).toUpperCase());
        return users;
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

    private DtoNotaVenta getNotaVentaFromRequest(HttpServletRequest request) {
        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
            notaVenta.setIdNotaVenta(NumberUtils.stringToNumber(request.getParameter(Text.ID_NOTA_VENTA)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA))) {
            notaVenta.setFecha(DateUtils.stringToDate(request.getParameter(Text.FECHA)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FOLIO))) {
            notaVenta.setFolio(request.getParameter(Text.FOLIO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_CLIENTE))) {
            notaVenta.setNombreCliente(request.getParameter(Text.NOMBRE_CLIENTE));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.DIRECCION))) {
            notaVenta.setDireccion(request.getParameter(Text.DIRECCION));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.RFC))) {
            notaVenta.setRfc(request.getParameter(Text.RFC));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.USER))
                && (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TIPO_AUTORIZACION)))) {
            notaVenta.setObservaciones("UTORIZADO POR "
                    + request.getParameter(Text.USER) + " , MOTIVO :" + request.getParameter(Text.TIPO_AUTORIZACION).toUpperCase());
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.AUTORIZADO))) {
            notaVenta.setObservaciones(request.getParameter(Text.AUTORIZADO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.STATUS_NOTA_VENTA))) {
            notaVenta.setStatusNotaVenta(request.getParameter(Text.STATUS_NOTA_VENTA).charAt(GenericTypes.ZERO));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TOTAL_VENTA))) {
            notaVenta.setTotal(NumberUtils.stringToBigDecimal(request.getParameter(Text.TOTAL_VENTA)));
        }
        notaVenta.setPedido(getPedidoFromRequest(request));
        notaVenta.setDetalles(getDetallesFromRequest(request));
        notaVenta.setLeyenda(
                PropertiesBean.getLeyendasFile().getProperty(Property.FOOTER)
        );
        return notaVenta;
    }

    private DtoPedido getPedidoFromRequest(HttpServletRequest request) {
        DtoPedido pedido = PedidoFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_PEDIDO))) {
            pedido.setIdPedido(NumberUtils.stringToNumber(request.getParameter(Text.ID_PEDIDO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.STATUS))) {
            pedido.setStatusAlmacen(request.getParameter(Text.STATUS).charAt(GenericTypes.ZERO));
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

    private Collection<DtoDetalleNotaVenta> getDetallesFromRequest(HttpServletRequest request) {
        Collection<DtoDetalleNotaVenta> detalles = new ArrayList<>();

        String[] cantidades = null;
        String[] productos = null;
        String[] kilos = null;
        String[] costos = null;
        String[] isAgotado = null;
        String[] isMuestra = null;
        String[] subtotal = null;
        String[] observacionesExcedido = null;

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PIEZAS))) {
            cantidades = request.getParameterValues(Text.ARRAY_PIEZAS);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO))) {
            productos = request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_KILOS))) {
            kilos = request.getParameterValues(Text.ARRAY_KILOS);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_COSTO))) {
            costos = request.getParameterValues(Text.ARRAY_COSTO);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_CHK_AGOTADO))) {
            isAgotado = request.getParameterValues(Text.ARRAY_CHK_AGOTADO);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_CHK_MUESTRA))) {
            isMuestra = request.getParameterValues(Text.ARRAY_CHK_MUESTRA);
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_SUBTOTAL))) {
            subtotal = request.getParameterValues(Text.ARRAY_SUBTOTAL);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_EXCEDIDO))) {
            observacionesExcedido = request.getParameterValues(Text.ARRAY_EXCEDIDO);
        }

        if (!StringUtils.isReallyEmptyOrNull(productos)) {
            for (Integer index = 0; index < productos.length; index++) {
                DtoDetalleNotaVenta detalleNotaVenta = DetalleNotaVentaFactory.newInstance();
                detalleNotaVenta.setCantidadPiezas(NumberUtils.stringToNumber(cantidades[index]));
                detalleNotaVenta.setNombreProducto(productos[index]);
                detalleNotaVenta.setCantidadKilos(
                        NumberUtils.stringToBigDecimal(kilos[index])
                );
                detalleNotaVenta.setCostoUnitario(
                        NumberUtils.stringToBigDecimal(costos[index])
                );

                if ((!StringUtils.isReallyEmptyOrNull(isAgotado)) && (isAgotado.length > index)) {
                    detalleNotaVenta.setIsAgotado(Boolean.parseBoolean(isAgotado[index]));
                }

                if ((!StringUtils.isReallyEmptyOrNull(isMuestra)) && (isMuestra.length > index)) {
                    detalleNotaVenta.setIsMuestra(Boolean.parseBoolean(isMuestra[index]));
                }

                if (!StringUtils.isReallyEmptyOrNull(subtotal)) {
                    detalleNotaVenta.setSubTotal(NumberUtils.stringToBigDecimal(subtotal[index]));
                }
                if (!StringUtils.isReallyEmptyOrNull(observacionesExcedido)) {
                    if (observacionesExcedido.length > index) {
                        String numberToString = StringUtils.numberToString(NumberUtils.stringToBigDecimal(observacionesExcedido[index]));
                        String text = "Se excedio por " + numberToString + " pzas.";
                        detalleNotaVenta.setObservacionExcedido(text.toUpperCase());
                    }
                }
                detalles.add(detalleNotaVenta);
            }
        }
        return detalles;
    }
}
