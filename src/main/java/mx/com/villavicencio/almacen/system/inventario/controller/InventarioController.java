package mx.com.villavicencio.almacen.system.inventario.controller;

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
import mx.com.villavicencio.almacen.generics.variables.Variables;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.security.SecurityUtils;
import mx.com.villavicencio.almacen.system.inventario.bo.InventarioBo;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.inventario.factory.InventarioFactory;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import mx.com.villavicencio.almacen.utils.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class InventarioController extends HttpServlet {

    private DtoUsuario user;
    private RequestDispatcher dispatcher;
    private InventarioBo inventarioBo;
    private Gson gson;

    private static final int INIT_INVENTARIO = 0;
    private static final int GET_INVENTARIO = 1;
    private static final int UPDATE_INVENTARIO = 2;
    private static final int GET_EXISTENCIA = 3;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        inventarioBo = (InventarioBo) context.getBean(Service.INVENTARIO_SERVICE);
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
        DtoInventario inventario;
        PrintWriter out = null;
        try {
            switch (method) {
                case INIT_INVENTARIO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.INVENTARIO);
                    request.setAttribute(Variables.INVENTARIOS, inventarioBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Variables.ID));
                    dispatcher.forward(request, response);
                    break;
                case GET_INVENTARIO:
                    inventario = inventarioFromRequest(request);
                    out = response.getWriter();
                    out.print(gson.toJson(this.inventarioBo.isAgotado(user, inventario)));
                    break;
                case UPDATE_INVENTARIO:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.INVENTARIO);
                    inventario = inventarioFromRequest(request);
                    this.inventarioBo.modificar(user, inventario);
                    request.setAttribute(Variables.INVENTARIOS, inventarioBo.findAll(user));
                    request.setAttribute(Variables.ID, request.getParameter(Text.ID_ACTION));
                    dispatcher.forward(request, response);
                    break;
                case GET_EXISTENCIA:
                    inventario = inventarioFromRequest(request);
                    inventario = this.inventarioBo.getExistencia(user, inventario);
                    out = response.getWriter();
                    out.print(gson.toJson(inventario));
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

    private DtoInventario inventarioFromRequest(HttpServletRequest request) {
        DtoInventario inventario = InventarioFactory.newInstance();

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.ID_INVENTARIO))) {
            inventario.setIdInventario(NumberUtils.stringToNumber(request.getParameter(Text.ID_INVENTARIO)));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.NOMBRE_PRODUCTO))) {
            inventario.setNombreProducto(request.getParameter(Text.NOMBRE_PRODUCTO));
        }

        if (!StringUtils.isReallyEmptyOrNull(request.getParameter(Text.CANTIDAD_EXISTENCIA))) {
            inventario.setCantidadExistencia(NumberUtils.stringToNumber(request.getParameter(Text.CANTIDAD_EXISTENCIA)));
        }
        return inventario;
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
