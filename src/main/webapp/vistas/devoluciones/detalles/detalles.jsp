<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div id="divContainer" class="container-fluid">
    <div class="row">
        <div id="divLateral" class="col-sm-3 col-md-2 sidebar">
            <ul id="ulListNotas" class="nav nav-sidebar">
                <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                <br/>
                <c:if test="${id == 2}">
                    <li id="liAlta">                            
                        <input type="button" id="btnAltaDevolucion" name="btnAltaDevolucion" value="Registrar Devolución" />                                
                    <li>
                    </c:if>
            </ul>
        </div>
        <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
            <div class="row placeholders">
                <div class="col-xs-6 col-sm-9 placeholder">
                    <c:if test="${empty notaVenta}">
                        <label class="text-muted">NO HAY NOTAS DE VENTA QUE MOSTRAR</label>
                    </c:if>
                    <c:if test="${not empty notaVenta}">
                        <fieldset>
                            <legend>
                                <span class="text-muted">LISTADO DE VENTAS</span>
                            </legend>
                            <div class="contenido" id="divTablaTodos">
                                <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                                <table id="tblNotas" align="center" class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                        <tr>
                                            <th># Nota Venta</th>
                                            <th>Folio</th>
                                            <th>Cliente</th>
                                            <th>Fecha</th>
                                            <th>Detalle</th>
                                        </tr>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${notaVenta}" var="notaVenta" varStatus="status">
                                            <tr id="trNotas"> 
                                                <td style="text-align: center">
                                                    <input type="hidden" name="txtIdNota" id="txtIdNota" value="${notaVenta.idNotaVenta}" />
                                                    ${notaVenta.idNotaVenta}
                                                </td>
                                                <td style="text-align: center">
                                                    ${notaVenta.folio}
                                                </td>
                                                <td style="text-align: center">                                
                                                    ${notaVenta.nombreCliente}
                                                </td>                            
                                                <td>
                                                    <fmt:formatDate pattern="dd-MM-yyyy" value="${notaVenta.fecha}"/>
                                                </td>
                                                <td>
                                                    <a id="detalleNota" href="#" >
                                                        <img  src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                              title="Ver Detalle" name="detalleNota" style="image-orientation: left;" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>   
                    </c:if>
                        <br/><br/>
                    <c:if test="${empty devoluciones}">
                        <label class="text-muted">NO HAY DEVOLUCIONES QUE MOSTRAR</label>
                    </c:if>
                    <c:if test="${not empty devoluciones}">
                        <fieldset>
                            <legend>
                                <span class="text-muted">LISTADO DEVOLUCIONES</span>
                            </legend>
                            <div class="contenido" id="divTablaTodos">
                                <table id="tblDetalleDevolucion" align="center" class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                        <tr>
                                            <th># Devolucion</th>
                                            <th>Folio</th>
                                            <th>Cliente</th>
                                            <th>Fecha</th>
                                            <th>Detalle</th>
                                        </tr>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${devoluciones}" var="devoluciones" varStatus="status">
                                            <tr id="trNotas"> 
                                                <td style="text-align: center">
                                                    <input type="hidden" name="txtIdDevolucion" id="txtIdDevolucion" value="${devoluciones.idDevoluciones}" />
                                                    ${devoluciones.idDevoluciones}
                                                </td>
                                                <td style="text-align: center">
                                                    ${devoluciones.nota.folio}
                                                </td>
                                                <td style="text-align: center">                                
                                                    ${devoluciones.nota.nombreCliente}
                                                </td>                            
                                                <td>
                                                    <fmt:formatDate pattern="dd-MM-yyyy" value="${devoluciones.fechaDevolucion}"/>
                                                </td>
                                                <td>
                                                    <a id="detalleNota" href="#" >
                                                        <img  src="${pageContext.request.contextPath}/image/report.png" width="32" height="32"
                                                              title="Ver Detalle" name="detalleNota" style="image-orientation: left;" />
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </fieldset>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="divNotaNoSelected" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p class="text-muted">Por favor, seleccione una nota de venta.</p>
            </td>
        </tr>
    </table>
</div>

<div id="divErrorDevolucion" title="Error">
    <table>
        <tr>
            <td style="text-align: center;">
                <p class="text-muted">La nota de venta ya tiene una devoluci&oacute;n registrada</p>
            </td>
        </tr>
    </table>
</div>