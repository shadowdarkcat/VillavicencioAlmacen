package mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import mx.com.villavicencio.almacen.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.dto.DtoDetalleDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.detalle.factory.DetalleDevolucionesFactory;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.bo.DevolucionesBo;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.dto.DtoDevoluciones;
import mx.com.villavicencio.almacen.system.venta.devoluciones.devoluciones.factory.DevolucionesFactory;
import mx.com.villavicencio.almacen.system.venta.nota.nota.bo.NotaVentaBo;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.factory.NotaVentaFactory;
import mx.com.villavicencio.almacen.utils.DateUtils;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;
import mx.com.villavicencio.almacen.utils.TraductorUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class DevolucionesController extends HttpServlet {
    
    private DtoUsuario user;
    private UsuarioBo usersBo;
    private RequestDispatcher dispatcher;
    private DevolucionesBo devolucionesBo;
    private NotaVentaBo notaVentaBo;
    private Gson gson;
    
    private static final int INIT_DEVOLUCIONES = 0;
    private static final int EXISTS_DEVOLUCION = 1;
    private static final int GET_NOTA_VENTA = 2;
    private static final int GET_CANTIDAD_LETRA = 3;
    private static final int GET_CREDENCIALES = 4;
    public static final int INSERT_DEVOLUCION = 5;
    
    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        devolucionesBo = (DevolucionesBo) context.getBean(Service.DEVOLUCIONES_SERVICE);
        notaVentaBo = (NotaVentaBo) context.getBean(Service.NOTA_VENTA_SERVICE);
        usersBo = (UsuarioBo) context.getBean(Service.USUARIO_SERVICE);
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
        DtoDevoluciones devoluciones;
        PrintWriter out = null;
        try {
            switch (method) {
                
                case INIT_DEVOLUCIONES:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.DEVOLUCIONES);
                    request.setAttribute(Variables.NOTA_VENTA, this.notaVentaBo.findAll(user));
                    request.setAttribute(Variables.DEVOLUCIONES, this.devolucionesBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                
                case EXISTS_DEVOLUCION:
                    notaVenta = getNotaVentaFromRequest(request);
                    out = response.getWriter();
                    Boolean verifyExistDevolucion = this.devolucionesBo.verifyExistDevolucion(user, notaVenta);
                    out.print(gson.toJson(verifyExistDevolucion));
                    break;
                case GET_NOTA_VENTA:
                    notaVenta = getNotaVentaFromRequest(request);
                    notaVenta = this.notaVentaBo.findById(user, notaVenta);
                    notaVenta.setFecha(DateUtils.dateNow());
                    out = response.getWriter();
                    out.print(gson.toJson(notaVenta));
                    break;
                
                case GET_CANTIDAD_LETRA:
                    if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.TOTAL_VENTA))) {
                        String traducir = TraductorUtils.traducir(NumberUtils.stringToBigDecimal(request.getParameter(Text.TOTAL_VENTA)));
                        out = response.getWriter();
                        out.print(gson.toJson(traducir));
                    }
                    break;
                
                case GET_CREDENCIALES:
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
                
                case INSERT_DEVOLUCION:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.DEVOLUCIONES);
                    devoluciones = getDevolucionFromRequest(request);
                    devoluciones.setOpcion(GenericTypes.INSERT);
                    this.devolucionesBo.ingresar(user, devoluciones);
                    request.setAttribute(Variables.NOTA_VENTA, this.notaVentaBo.findAll(user));
                    request.setAttribute(Variables.DEVOLUCIONES, this.devolucionesBo.findAll(user));
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
    
    private DtoNotaVenta getNotaVentaFromRequest(HttpServletRequest request) {
        DtoNotaVenta notaVenta = NotaVentaFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
            notaVenta.setIdNotaVenta(NumberUtils.stringToNumber(request.getParameter(Text.ID_NOTA_VENTA)));
        }
        return notaVenta;
    }
    
    private DtoUsuario getUserFromRequest(HttpServletRequest request) {
        DtoUsuario users = UsuarioFactory.newInstance();
        users.setNombreUsuario(request.getParameter(Text.USER).toUpperCase());
        users.setPassword(request.getParameter(Text.PASSWORD).toUpperCase());
        return users;
    }
    
    private DtoDevoluciones getDevolucionFromRequest(HttpServletRequest request) {
        DtoDevoluciones devoluciones = DevolucionesFactory.newInstance();
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_DEVOLUCION))) {
            devoluciones.setIdDevoluciones(NumberUtils.stringToNumber(request.getParameter(Text.ID_DEVOLUCION)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.FECHA))) {
            devoluciones.setFechaDevolucion(DateUtils.stringToDate(request.getParameter(Text.FECHA)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_NOTA_VENTA))) {
            devoluciones.setIdNotaVenta(NumberUtils.stringToNumber(request.getParameter(Text.ID_NOTA_VENTA)));
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.OBSERVACIONES))) {
            devoluciones.setObservaciones(request.getParameter(Text.OBSERVACIONES));
        }
        devoluciones.setDetalles(getDetalleDevolucionFromRequest(request));
        return devoluciones;
    }
    
    private Collection<DtoDetalleDevoluciones> getDetalleDevolucionFromRequest(HttpServletRequest request) {
        Collection<DtoDetalleDevoluciones> detalles = new ArrayList<>();
        String[] productos = null;
        String[] piezas = null;
        String[] kilos = null;
        String[] costoUnitario = null;
        String[] subtotal = null;
        
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO))) {
            productos = request.getParameterValues(Text.ARRAY_NOMBRE_PRODUCTO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_PIEZAS))) {
            piezas = request.getParameterValues(Text.ARRAY_PIEZAS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_KILOS))) {
            kilos = request.getParameterValues(Text.ARRAY_KILOS);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_COSTO))) {
            costoUnitario = request.getParameterValues(Text.ARRAY_COSTO);
        }
        if (!StringUtils.isReallyEmptyOrNull(request.getParameterValues(Text.ARRAY_SUBTOTAL))) {
            subtotal = request.getParameterValues(Text.ARRAY_SUBTOTAL);
        }
        
        for (Integer index = 0; index < productos.length; index++) {
            DtoDetalleDevoluciones devoluciones = DetalleDevolucionesFactory.newInstance();
            devoluciones.setNombreProducto(productos[index]);
            devoluciones.setCantidadPiezas(
                    NumberUtils.stringToNumber(piezas[index])
            );
            devoluciones.setCantidadKilos(
                    NumberUtils.stringToBigDecimal(kilos[index])
            );
            devoluciones.setCosto(
                    NumberUtils.stringToBigDecimal(costoUnitario[index])
            );
            devoluciones.setSubtotal(
                    NumberUtils.stringToBigDecimal(subtotal[index])
            );
            detalles.add(devoluciones);
        }
        return detalles;
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

}
