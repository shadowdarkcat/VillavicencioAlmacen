<div id="divModificarInventario" title="Modificar Inventario">
    <form id="frmModificaInventario" name="frmModificaInventario" method="post" action="${pageContext.request.contextPath}/controller/inventarioController?method=2">
        <input type="hidden" id="txtIdAction" name="txtIdAction" value="${id}" />
        <table>
            <tr>
                <td>
                    <input type="hidden" id="txtIdInventario" name="txtIdInventario" readonly="readOnly" />
                    <input type="hidden" id="txtNombreProducto" name="txtNombreProducto" readonly="readOnly" />
                    <span class="text-muted">Nombre Producto: </span></td>
                    <td><label id="lblNombre" class="text-muted"></label></td>
            </tr>
            <tr>
                <td><span class="text-muted">Cantidad Existencia: </span></td>
                <td><input type="text" id="txtCantidadExistencia" name="txtCantidadExistencia" class="solo-numero required" /></td>
            </tr>
        </table>    
    </form>
</div>
