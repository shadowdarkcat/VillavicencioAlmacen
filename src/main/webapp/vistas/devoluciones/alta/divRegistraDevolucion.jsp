<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="divAltaRegistro" title="Registro Devolución">   
    <form id="frmAltaDevolucion" name="frmAltaDevolucion" method="post" action="${pageContext.request.contextPath}/controller/devolucionesController?method=5">
        <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
        <label style="margin: 0px 0px 0px 320px;" class="text-muted">NOTA VENTA </label>
        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
        <br /><br /><br /><br /><br /><br /><br />
        <input type="hidden" id="txtIdNotaVenta" name="txtIdNotaVenta" />
        <input type="hidden" id="txtObservaciones" name="txtObservaciones" />
        <table>
            <thead>
                <tr>
                    <th class="text-muted">FECHA</th>
                    <th class="text-muted">FOLIO</th>
                </tr>
            </thead>
            <tbody>
            <td>
                <input type="text" id="txtFecha" name="txtFecha" readonly="readOnly" />
            </td>
            <td>
                <input type="text" id="txtFolio" name="txtFolio" readonly="readOnly" />
            </td>
            </tbody>
        </table>
        <table>
            <thead>
                <tr>
                    <th class="text-muted">CLIENTE</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <strong><label class="text-muted">NOMBRE :</label></strong> 
                        <label id="lblNombre" class="text-muted"></label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong><label class="text-muted">DIRECCI&Oacute;N :</label></strong>
                        <label id="lblDireccion" class="text-muted"></label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <strong><lbel class="text-muted">RFC :</lbel></strong>
                        <label id="lblRfc" class="text-muted"></label> 
                    </td>
                </tr>
            </tbody>
        </table>       

        <div class="contenido" id="divTablaTodos">
            <table id="tblDevolucionRegistro" class="table table-bordered table-hover" >
                <thead>
                    <tr>
                        <th colspan = "10" style = " text-align: center;" class = "text-muted" > REGISTRO DEVOLUCIONES </th>
                    </tr>
                    <tr align="center" valign="middle">
                        <th rowspan="2" style=" text-align: center;" class="text-muted">PRODUCTOS</th>
                        <th colspan="2" style=" text-align: center;" class="text-muted">SALIDA</th>
                        <th colspan="2" style=" text-align: center;" class="text-muted">DEVOLUCION</th>
                        <th rowspan="2" style=" text-align: center;" class="text-muted">TOTAL KGS.</th>
                        <th rowspan="2" style=" text-align: center;" class="text-muted">COSTO U.</th>
                        <th rowspan="2" style=" text-align: center;" class="text-muted">SUBTOTAL</th>
                    </tr>
                    <tr>
                        <th style=" text-align: center;" class="text-muted">PZAS</th>
                        <th style=" text-align: center;" class="text-muted">KGS</th>
                        <th style=" text-align: center;" class="text-muted">PZAS</th>
                        <th style=" text-align: center;" class="text-muted">KGS</th>	
                    </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>            
        <br/>   
        <table border="1" class="table table-bordered table-hover">
            <tr>
                <td><label class="text-muted">CANTIDAD CON LETRA :</label><br/>
                    <label id="lblCantidadLetra" class="text-muted"></label>
                </td>
                <td>
                    <table>
                        <tr>
                            <td class="text-muted"> SUBTOTAL </td><td class="text-muted"> 0.00</td>
                        </tr>
                        <tr>
                            <td class="text-muted">IVA</td><td class="text-muted" > 0.00</td>
                        </tr>
                        <tr>
                            <td class="text-muted">TOTAL $</td>
                            <td>
                                <input style="text-align: center;" type="text" id="txtTotal" name="txtTotal" readonly="readOnly" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <br/>
        <strong> <label class="text-muted">OBSERVACIONES :</label></strong>
        <br/>            
        <label id="lblObservaciones" class="text-muted"></label>    
    </form>
</div>