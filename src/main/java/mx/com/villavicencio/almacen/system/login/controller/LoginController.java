package mx.com.villavicencio.almacen.system.login.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mx.com.villavicencio.almacen.commons.exception.ApplicationException;
import mx.com.villavicencio.almacen.commons.exception.MessageException;
import mx.com.villavicencio.almacen.commons.messages.ApplicationMessages;
import mx.com.villavicencio.almacen.commons.response.Response;
import mx.com.villavicencio.almacen.generics.content.Content;
import mx.com.villavicencio.almacen.generics.dispatcher.Dispatcher;
import mx.com.villavicencio.almacen.generics.service.Service;
import mx.com.villavicencio.almacen.generics.text.Text;
import mx.com.villavicencio.almacen.generics.variables.Variables;
import mx.com.villavicencio.almacen.properties.PropertiesBean;
import mx.com.villavicencio.almacen.properties.Property;
import mx.com.villavicencio.almacen.security.SecurityUtils;
import mx.com.villavicencio.almacen.system.usuario.bo.UsuarioBo;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.usuario.factory.UsuarioFactory;
import mx.com.villavicencio.almacen.utils.NumberUtils;
import org.apache.log4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Gabriel J
 *
 */
public class LoginController extends HttpServlet {

    private static final int INIT_LOGIN = 0;
    private static final int VALIDATE_LOGIN = 1;
    private static final int REDIRECT_USER = 2;
    private static final int LOGOUT = 3;
    
    private RequestDispatcher dispatcher;
    private DtoUsuario user;
    private UsuarioBo usersBo;

    @Override
    public void init() throws ServletException {
        super.init();
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        usersBo = (UsuarioBo) context.getBean(Service.USUARIO_SERVICE);      
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response = Response.getHttpServletResponse(response);
            request.setCharacterEncoding(Content.UTF8);
            if (SecurityUtils.isValidUrl(request)) {
                method(NumberUtils.stringToNumber(request.getParameter(Content.METHOD)), request, response);
            } else {
                ApplicationMessages.errorMessage(PropertiesBean.getErrorFile().getProperty(Property.ACCESO_DENEGADO));
            }
        } catch (ApplicationException | IOException | NumberFormatException | ServletException ex) {
            dispatcher = getServletContext().getRequestDispatcher(Dispatcher.HANDLER_EXCEPTION);
            request.setAttribute(Variables.EXCEPTION, ex);
            dispatcher.forward(request, response);
        }
    }

    private void method(Integer method, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            switch (method) {
                 case INIT_LOGIN:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.LOGIN);
                    dispatcher.forward(request, response);
                    break;
                case VALIDATE_LOGIN:
                    DtoUsuario validUser = usersBo.validar(getUserFromRequest(request));
                    if ((validUser != null) && (validUser.getPuesto().equalsIgnoreCase(Variables.PUESTO))) {
                        dispatcher = getServletContext().getRequestDispatcher(Dispatcher.PRINCIPAL);
                        request.getSession().setAttribute(Variables.MENU_USUARIO, usersBo.getMenu(validUser));
                        request.getSession().setAttribute(Variables.USER, validUser);
                        MDC.put(Variables.USER_ID, validUser.getIdUsuario());
                        ApplicationMessages.infoMessage(PropertiesBean.getInfoFile().getProperty(Property.INFO_SESSION));
                        dispatcher.forward(request, response);
                    } else {
                        response.sendRedirect(request.getContextPath() + Dispatcher.LOGIN_CONTROLLER);
                    }
                    break;
                case REDIRECT_USER:
                    request.setAttribute(Variables.LEYENDA, PropertiesBean.getErrorFile().getProperty(Property.ERROR_LOGIN));
                    dispatcher.forward(request, response);
                    break;
                case LOGOUT:
                    dispatcher = getServletContext().getRequestDispatcher(Dispatcher.LOGIN);
                    HttpSession session = request.getSession(false);
                    if (session != null && session.getAttribute(Variables.USER) != null) {
                        DtoUsuario user = (DtoUsuario) session.getAttribute(Variables.USER);
                        MDC.put("userId", user.getIdUsuario());
                        ApplicationMessages.infoMessage(PropertiesBean.getInfoFile().getProperty(Property.INFO_SESSION_TERMINADA));
                        session.invalidate();
                        dispatcher.forward(request, response);
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
        }
    }

    private DtoUsuario getUserFromRequest(HttpServletRequest request) {
        DtoUsuario usuario = UsuarioFactory.newInstance();
        usuario.setNombreUsuario(request.getParameter(Text.USER).toUpperCase());
        usuario.setPassword(request.getParameter(Text.PASSWORD).toUpperCase());
        return usuario;
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
