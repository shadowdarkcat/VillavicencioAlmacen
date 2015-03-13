<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib uri="/WEB-INF/images.tld" prefix="images"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inventario</title>
    </head>
    <body>
        <div id="divContainer" class="container-fluid">
            <div class="row">
                <div id="divLateral" class="col-sm-3 col-md-2 sidebar">
                    <ul id="ulListProductos" class="nav nav-sidebar">
                        <li class="active"><a href="#">Acci&oacute;n <span class="sr-only">(current)</span></a></li>
                        <br/>

                        <li id="liCambios">                            
                            <input type="button" id="btnModificarInventario" name="btnModificarInventario" value="Actualizar Inventario" />                                
                        <li>
                    </ul>
                </div>
                <div id="divDatos" class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
                            <fieldset>
                                <legend class="pager">LISTA DE INVENTARIO</legend>
                                <table id="tblInventario" align="center" class="table table-bordered table-hover" style="width: 900px;">
                                    <thead>
                                        <tr>
                                            <th style="text-align: center"># Inventario</th>
                                            <th style="text-align: center">Nombre Producto</th>
                                            <th style="text-align: center">Cantidad Existente</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${inventarios}" var="inventarios" varStatus="status">
                                            <tr> 
                                                <td style="text-align: center">
                                                    <input type="hidden" id="txtIdInventario" name="txtIdInventario" value="${inventarios.idInventario}" readonly="readOnly"/>                                                        
                                                    ${inventarios.idInventario}
                                                </td>
                                                <td style="text-align: center">
                                                    <input type="hidden" id="txtNombreProducto" name="txtNombreProducto" value="${inventarios.nombreProducto}" readonly="readOnly"/>
                                                    ${inventarios.nombreProducto}
                                                </td>
                                                <td style="text-align: center">                                                    
                                                    ${inventarios.cantidadExistencia}
                                                </td>                                               
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </fieldset>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <div id="divInventarioNoSelected" title="Error">
        <table>
            <tr>
                <td style="text-align: center;">
                    <p><span class="text-muted" >Por favor, seleccione un producto del inventario.</span></p>
                </td>
            </tr>
        </table>
    </div>
</html>