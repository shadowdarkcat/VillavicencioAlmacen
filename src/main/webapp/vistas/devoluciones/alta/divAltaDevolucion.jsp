<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div id="divAltaDevolucion" title="Registro Devolución">   
    <img src="${pageContext.request.contextPath}/image/logo.jpg" style="width: 75px; height: 75px; position: absolute; margin-top: 5px;" />
    <label style="margin: 0px 0px 0px 320px;" class="text-muted">NOTA VENTA </label>
    <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
    <br /><br /><br /><br /><br /><br /><br />
    <input type="hidden" id="txtIdNota" name="txtIdNota" />
    <input type="hidden" id="txtFueraRango" name="txtFueraRango" />    
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
                    <strong><label class="text-muted">RFC :</label></strong>
                    <label id="lblRfc" class="text-muted"></label> 
                </td>
            </tr>
        </tbody>
    </table>       

    <div class="contenido" id="divTablaTodos">
        <table id="tblDevolucion" class="table table-bordered table-hover" >
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
                    <th rowspan="2" style=" text-align: center;" class="text-muted">DEVOLUCI&Oacute;N</th>
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
    <strong> <label class="text-muted">OBSERVACIONES :</label></strong>    
    <label id="lblObservaciones" class="text-muted"></label>    
</div>