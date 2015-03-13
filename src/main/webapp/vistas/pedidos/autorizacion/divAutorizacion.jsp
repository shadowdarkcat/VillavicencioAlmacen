<div id="divAutorizarPedido" title="Autorización">
    <table>
        <tr>
            <td class="text-muted">Para autorizar ingrese credenciales de administrador</td>
        </tr>
        <tr>
            <td class="text-muted">Nombre de usuario :</td>
            <td><input type="text" id="txtUser" name="txtUser" /></td>
        </tr>
        <tr>
            <td class="text-muted">Contrase&ntilde;a :</td>
            <td><input type="password" id="txtPass" name="txtPass" /></td>
        </tr>
        <tr>
            <td class="text-muted">Nota: Si usted no tiene acceso, avise al administrador</td>
        </tr>
        <tr>
            <td class="text-muted"><label id="lblCausa"></label></td>
        </tr>
    </table>
</div>

<div id="divError" title="Error de credenciales">
    <label class="text-muted">usuario o contrase&ntilde;a no valido</label>
</div>

<div id="divTipoAutorizacionPedido" title="Tipo autorización">
    <table>
        <tr>
            <td class="text-muted"><input type="radio" id="estadoAlmacen " name="estadoAlmacen" value="Peso fuera de rango">Peso fuera de rango</td>
            <td class="text-muted"><input type="radio" id="estadoAlmacen " name="estadoAlmacen" value="Crédito contra nota">Cr&eacute;dito contra nota</td>
            <td class="text-muted"><input type="radio" id="estadoAlmacen " name="estadoAlmacen" value="Crédito excedido">Cr&eacute;dito excedido</td>
            <td class="text-muted"><input type="radio" id="estadoAlmacen " name="estadoAlmacen" value="Crédito plazo vencido">Cr&eacute;dito plazo vencido </td>
            <td class="text-muted"><input type="radio" id="estadoAlmacen " name="estadoAlmacen" value="Inventario excedido">Inventario excedido</td>
            <td class="text-muted"><input type="hidden" id="txtUser" name="txtUser" /></td>
        </tr>
    </table>
</div>

<div id="divErrorRadio" title="Error">
    <label class="text-muted">Error debe de seleccionar un tipo de autorizaci&oacute;n</label>
</div>