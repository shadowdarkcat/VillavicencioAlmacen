package mx.com.villavicencio.almacen.utils;

import java.math.BigDecimal;
import java.util.Objects;
import mx.com.villavicencio.almacen.system.cliente.dto.DtoCliente;
import mx.com.villavicencio.almacen.system.inventario.bo.InventarioBo;
import mx.com.villavicencio.almacen.system.inventario.dto.DtoInventario;
import mx.com.villavicencio.almacen.system.inventario.factory.InventarioFactory;
import mx.com.villavicencio.almacen.system.pedidos.detalle.dto.DtoDetallePedido;
import mx.com.villavicencio.almacen.system.pedidos.pedido.dto.DtoPedido;
import mx.com.villavicencio.almacen.system.usuario.dto.DtoUsuario;
import mx.com.villavicencio.almacen.system.vendedor.dto.DtoVendedor;
import mx.com.villavicencio.almacen.system.venta.nota.detalle.dto.DtoDetalleNotaVenta;
import mx.com.villavicencio.almacen.system.venta.nota.nota.dto.DtoNotaVenta;

/**
 *
 * @author Gabriel J
 */
public class TableUtils {

    public static String createTableNotaPedido(String context, Integer idAction, DtoPedido pedido, DtoUsuario user, InventarioBo inventarioBo) {
        Integer index = 0;
        StringBuilder sb = new StringBuilder();
        String nombre = "";
        String noInterior = "";
        String delegacion = "";
        String municipio = "";
        String ciudad = "";
        String direccion = "";
        String rfc = "";
        String idCliente = "";
        String idVendedor = "";
        Integer idCredito = null;
        if (!StringUtils.isReallyEmptyOrNull(pedido.getCliente().getEmpresa())) {
            DtoCliente cliente = pedido.getCliente();
            if (cliente.getCredito() != null) {
                if (cliente.getCredito().getIdCredito() != null) {
                    if (cliente.getCredito().getIdCredito() != 0) {
                        idCredito = cliente.getCredito().getIdCredito();
                    }
                }

            }
            idCliente = StringUtils.numberToString(cliente.getIdCliente());
            nombre = cliente.getEmpresa() + " " + cliente.getRazonSocial();
            rfc = (!StringUtils.isReallyEmptyOrNull(cliente.getRfc()) ? cliente.getRfc() : "NO APLICA");
            noInterior = (!StringUtils.isReallyEmptyOrNull(cliente.getNoInterior()) ? "INT. " + cliente.getNoInterior() : "");
            delegacion = (!StringUtils.isReallyEmptyOrNull(cliente.getDelegacion()) ? cliente.getDelegacion() : " ");
            municipio = (!StringUtils.isReallyEmptyOrNull(cliente.getMunicipio()) ? cliente.getMunicipio() : " ");
            ciudad = (!StringUtils.isReallyEmptyOrNull(cliente.getCiudad()) ? cliente.getCiudad() : "");
            direccion = cliente.getCalle() + " " + cliente.getNoExterior() + " " + noInterior + " " + cliente.getColonia() + " " + cliente.getCodigoPostal() + " "
                    + delegacion + municipio + " " + cliente.getEstado() + " " + ciudad;

        } else if (!StringUtils.isReallyEmptyOrNull(pedido.getVendedor().getNombre())) {
            DtoVendedor vendedor = pedido.getVendedor();
            if (vendedor.getCredito() != null) {
                if (vendedor.getCredito().getIdCredito() != null) {
                    if (vendedor.getCredito().getIdCredito() != 0) {
                        idCredito = vendedor.getCredito().getIdCredito();
                    }
                }

            }
            idVendedor = StringUtils.numberToString(vendedor.getIdVendedor());
            nombre = vendedor.getNombre() + " " + vendedor.getApellidoPaterno() + " " + vendedor.getApellidoMaterno();
            rfc = (!StringUtils.isReallyEmptyOrNull(vendedor.getRfc()) ? vendedor.getRfc() : "NO APLICA");
            noInterior = (!StringUtils.isReallyEmptyOrNull(vendedor.getNoInterior()) ? "INT. " + vendedor.getNoInterior() : "");
            delegacion = (!StringUtils.isReallyEmptyOrNull(vendedor.getDelegacion()) ? vendedor.getDelegacion() : " ");
            municipio = (!StringUtils.isReallyEmptyOrNull(vendedor.getMunicipio()) ? vendedor.getMunicipio() : " ");
            ciudad = (!StringUtils.isReallyEmptyOrNull(vendedor.getCiudad()) ? vendedor.getCiudad() : "");
            direccion = vendedor.getCalle() + " " + vendedor.getNoExterior() + " " + noInterior + " " + vendedor.getColonia() + " " + vendedor.getCp() + " "
                    + delegacion + municipio + " " + vendedor.getEstado() + " " + ciudad;
        }

        sb.append("<form id='frmSurtePedido'>")
                .append("<img src='").append(context).append("/image/logo.jpg' style='width: 75px; height: 75px; position: absolute; margin-top: 5px;' />")
                .append("<label style='margin: 0px 0px 0px 635px;' class='text-muted'>NOTA PEDIDO </label>")
                .append("<br /><br /><br /><br />")
                .append("<input type='hidden' id='txtIdAction' name='txtIdAction' value='").append(idAction).append("' />")
                .append("<input type='hidden' id='txtIdCredito' name='txtIdCredito' value='").append(idCredito).append("' />")
                .append("<input type='hidden' id='txtLimiteCredito' name='txtLimiteCredito' />")
                .append("<input type='hidden' id='txtTipoAutorizacion' name='txtTipoAutorizacion' />")
                .append("<input type='hidden' id='txtFechaPago' name='txtFechaPago' />")
                .append("<input type='hidden' id='txtUser' name='txtUser' />")
                .append("<input type='hidden' id='txtFueraRango' name='txtFueraRango' value='false' />  ")
                .append("<input type='hidden' id='txtIdPedido' name='txtIdPedido' value='").append(pedido.getIdPedido()).append("' />")
                .append("<input type='hidden' id='txtIdCliente' name='txtIdCliente' value='").append(idCliente).append("' readonly='readOnly' />")
                .append("<input type='hidden' id='txtIdVendedor' name='txtIdVendedor' value='").append(idVendedor).append("' readonly='readOnly' />")
                .append("<input type='hidden' id='txtStatus' name='txtStatus' readonly='readOnly' readonly='readOnly' value = 'S'/>")
                .append("<table><thead><tr><th style='text-align: center;' class='text-muted'>FECHA</th><th style='text-align: center;' class='text-muted'>FOLIO</th></tr></thead>")
                .append("<tbody><tr><td style='text-align: center;'><input type='text' id='txtFecha' name='txtFecha' value='").append(DateUtils.dateToString(DateUtils.dateNow())).append("'readonly='readOnly' /></td>")
                .append("<td style='text-align: center;'><input type='text' id='txtFolio' name='txtFolio' value='").append(pedido.getFolio()).append("' readonly='readOnly' /></td>")
                .append("</tr></tbody></table><br/>")
                .append("<table style='width: 1236px;'><thead><tr><th style='text-align: center;' class='text-muted'>CLIENTE</th></tr></thead>")
                .append("<tbody><tr><td><strong><label id='lblNombre' class='text-muted'>NOMBRE: </label></strong><input type='text' id='txtNombreCliente' name='txtNombreCliente' value='").append(nombre).append("' readOnly = 'readOnly' style='width: 1126px; margin: 0px 0px 0px 29px;' /></td></tr>")
                .append("<tr><td><strong><label id='lblDireccion' class='text-muted'>DIRECCI&Oacute;N :</label></strong> <input type='text' id='txtDireccion' name='txtDireccion' value='").append(direccion).append("' readOnly='readOnly' style='width: 1126px;' /> </td></tr>")
                .append("<tr><td><strong><label id='lblRfc' class='text-muted'><strong>R.F.C. : </label></strong><input type='text' id='txtRfc' name='txtRfc' value='").append(rfc).append("' readOnly='readOnly' style='margin: 0px 0px 0px 43px; width: 351px;' /></td></tr>")
                .append("</tbody></table>")
                .append("<table id='tblListNotaPedido' class='table table-bordered table-hover'><thead><tr>")
                .append("<th style='text-align: center;' class='text-muted'>CANTIDAD</th><th style='text-align: center;' class='text-muted'>DESCRIPCION</th>")
                .append("<th style='text-align: center;' class='text-muted'>KGS.</th><th style='text-align: center; display: none;' class='text-muted'>PRECIO U.</th><th style='text-align: center;' class='text-muted'>INVENTARIO</th>")
                .append("<th style='text-align: center;' class='text-muted'>EXTRAS</th><th style='text-align: center;' class='text-muted'>ELIMINAR</th>")
                .append("</tr></thead> ")
                .append("<tbody>");

        for (DtoDetallePedido detalles : pedido.getDetalles()) {
            DtoInventario inventario = InventarioFactory.newInstance();
            inventario.setNombreProducto(detalles.getNombreProducto());
            BigDecimal minimo = detalles.getPesoMinimo().multiply(NumberUtils.integerToBigDecimal(detalles.getCantidadPiezas()));
            BigDecimal maximo = detalles.getPesoMaximo().multiply(NumberUtils.integerToBigDecimal(detalles.getCantidadPiezas()));
            inventario = inventarioBo.getExistencia(user, inventario);
            BigDecimal existenciaInventario = NumberUtils.integerToBigDecimal(inventario.getCantidadExistencia());
            BigDecimal cantidadPiezas = NumberUtils.integerToBigDecimal(detalles.getCantidadPiezas());
            BigDecimal excedido;
            excedido = existenciaInventario.subtract(cantidadPiezas);

            sb.append("<tr class='table table-bordered table-hover' id='trNotaPedido").append(index).append("'>");
            if (excedido.compareTo(BigDecimal.ZERO) < 0) {
                excedido = excedido.multiply(new BigDecimal(-1));
                sb.append("<td style='text-align: center; width:25px;' ><input type='text' id='txtArrayCantidad").append(index).append("' name ='txtArrayCantidad").append(index).append("' value='").append(detalles.getCantidadPiezas()).append("' readOnly ='readOnly' />")
                        .append("<label id='lblErrorCantidad").append(index).append("' title='Existencia en inventario ").append(inventario.getCantidadExistencia()).append(" pzas.'>Excede inventario</label><input type='hidden' id='txtErrorInventario' name='txtErrortxtErrorInventario' value='true' />")
                        .append("<input type='hidden' id='txtArrayExcedido").append(index).append("' name='txtArrayExcedido").append(index).append("' value ='").append(excedido).append("'readonly='readOnly'/></td>");
            } else {
                sb.append("<td style='text-align: center; width:25px;' ><input type='text' id='txtArrayCantidad").append(index).append("' name ='txtArrayCantidad").append(index).append("' value='").append(detalles.getCantidadPiezas()).append("' readOnly ='readOnly' /></td>");
            }
            sb.append("<td style='text-align: center; width:25px;'><input type='text' id='txtArrayNombreProducto").append(index).append("' name='txtArrayNombreProducto").append(index).append("' value='").append(detalles.getNombreProducto()).append("' readOnly='redOnly' /></td>");
            if (Objects.equals(detalles.getIsMuestra(), Boolean.TRUE)) {
                sb.append("<td style='text-align: center; width:25px;'>* <input type='text' id='txtArrayKilos").append(index).append("' name='txtArrayKilos").append(index).append("' class='required' onBlur='isVacio(this,").append(index).append(");' onKeyUp='cuenta(this,").append(minimo).append(",").append(maximo).append(",").append(index).append(");' /><label id='lblError").append(index).append("' style='display: none;'>Peso fuera del rango</label><label id='lblRequeridoPeso").append(index).append("' style='display: none;'>Campo requerido</label></td>");
                sb.append("<td style='text-align: center; width:25px; display:none;'><input type='text' id='txtArrayCosto").append(index).append("' name='txtArrayCosto").append(index).append("' value='$0.00' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPeso").append(index).append("' name='txtArrayPeso").append(index).append("' value='").append(detalles.getPeso()).append("' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPesoMinimo").append(index).append("' name='txtArrayPesoMinimo").append(index).append("' value='").append(minimo).append("' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPesoMaximo").append(index).append("' name='txtArrayPesoMaximo").append(index).append("' value='").append(maximo).append("' readonly='readOnly'/></td>");
            } else {
                sb.append("<td style='text-align: center; width:25px;'>* <input type='text' id='txtArrayKilos").append(index).append("' name='txtArrayKilos").append(index).append("' class='required' onBlur='isVacio(this,").append(index).append(");' onKeyUp='cuenta(this,").append(minimo).append(",").append(maximo).append(",").append(index).append(");' /><label id='lblError").append(index).append("' style='display: none;'>Peso fuera del rango</label><label id='lblRequeridoPeso").append(index).append("' style='display: none;'>Campo requerido</label></td>");
                sb.append("<td style='text-align: center; width:25px; display:none;'><input type='text' id='txtArrayCosto").append(index).append("' name='txtArrayCosto").append(index).append("' value='").append(NumberUtils.formatMoney(detalles.getCostoUnitario())).append("' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPeso").append(index).append("' name='txtArrayPeso").append(index).append("' value='").append(detalles.getPeso()).append("' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPesoMinimo").append(index).append("' name='txtArrayPesoMinimo").append(index).append("' value='").append(minimo).append("' readonly='readOnly'/>")
                        .append("<input type='hidden' id='txtArrayPesoMaximo").append(index).append("' name='txtArrayPesoMaximo").append(index).append("' value='").append(maximo).append("' readonly='readOnly'/></td>");
            }
            if (Objects.equals(detalles.getIsMuestra(), Boolean.TRUE)) {
                sb.append("<td style='text-align: center; width:25px;'><input type='checkbox' id='existencia").append(index).append("' name='existencia").append(index).append("' value ='1' onClick='agotado(this,").append("txtArrayKilos").append(index).append(",").append("txtArrayCosto").append(index).append(",\"").append(NumberUtils.formatMoney(detalles.getCostoUnitario())).append("\",").append(index).append(");' > Agotado </td>")
                        .append("<td style='text-align: center; width:25px;'> <input type='checkbox' id='muestra").append(index).append("' name='muestra").append(index).append("' value='2' onClick='return false' checked='checked'> Muestra")
                        .append("<input type='hidden' id='txtArrayComisiones").append(index).append("' name='txtArrayComisiones").append(index).append("' readOnly='readOnly' value ='").append(detalles.getComision()).append("'/>")
                        .append("</td>");
            } else {
                sb.append("<td style='text-align: center; width:25px;'><input type='checkbox' id='existencia").append(index).append("' name='existencia").append(index).append("' value ='1' onClick='agotado(this,").append("txtArrayKilos").append(index).append(",").append("txtArrayCosto").append(index).append(",\"").append(NumberUtils.formatMoney(detalles.getCostoUnitario())).append("\",").append(index).append(");' > Agotado </td>")
                        .append("<td style='text-align: center; width:25px;'> <input type='checkbox' id='muestra").append(index).append("' name='muestra").append(index).append("' value='2' onClick='return false' > Muestra")
                        .append("<input type='hidden' id='txtArrayComisiones").append(index).append("' name='txtArrayComisiones").append(index).append("' readOnly='readOnly' value ='").append(detalles.getComision()).append("'/>")
                        .append("</td>");
            }
            sb.append("<td style='text-align: center; width:25px;'><img id='del' src='").append(context).append("/image/remove_cart.png' width='24' height='24' onClick='removeData(").append(pedido.getIdPedido()).append(",").append(detalles.getIdDetallePedido()).append(",\"").append(detalles.getNombreProducto()).append("\",\"").append(pedido.getFolio()).append("\");'")
                    .append("title='Elimina el producto ").append(detalles.getNombreProducto()).append(" de la nota con folio \"").append(pedido.getFolio()).append("\"' />")
                    .append("</td></tr>");
            index += 1;
        }
        sb.append("</tbody></table></form>");
        sb.append("<table id='tblExtras' class='table table-bordered table-hover'><tr><td id='tdCombo'  style='text-align: center; width:25px;'></td><td style='text-align: center; width:25px;'>Cantidad : <input type='text' id='txtCantidad' name='txtCantidad' onKeyUp='soloNumero(this);'/><label id='lblRequerido' style='display: none;' class='text-muted'>Campo requerido</label></td>")
                .append("<td style='text-align: center; width:25px;'><input type='checkbox' id='chkMuestra' name='chkMuestra' value='1'> Muestra</td><td style='text-align: center; width:25px;'><input type='button' id='btnAgregar' name='btnAgregar' value='Registrar' onClick='sendData();'/></td>")
                .append("</tr></table><label id='lblAgotado' style='display: none;' class='text-muted'>El producto se encuentra agotado, no se podr&aacute; agregar a la nota</label>");
        return sb.toString();
    }

    public static String createTableNotaPedido(DtoNotaVenta nota, String context, String action) {

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal subtotal = BigDecimal.ZERO;
        StringBuilder form = new StringBuilder();
        StringBuilder table = new StringBuilder();
        StringBuilder content = new StringBuilder();
        String strNota = null;

        if (!StringUtils.isReallyEmptyOrNull(nota.getObservaciones())) {
            strNota = nota.getObservaciones();
        }

        form.append("<form id='frmNotaVenta' method='post' action='").append(context).append("/controller/notaVentaController?method=3'>")
                .append("<img src='").append(context).append("/image/logo.jpg' style='width: 75px; height: 75px; position: absolute; margin-top: 5px;' />")
                .append("<label style='margin: 0px 0px 0px 320px;' class='text-muted'>NOTA VENTA </label>")
                .append("<br /><br /><br /><br /><br /><br />")
                .append("<input type='hidden' id='txtIdAction' name='txtIdAction' value='").append(action).append("' />")
                .append("<input type='hidden' id='txtIdCredito' name='txtIdCredito' value='").append(nota.getIdCredito()).append("' />")
                .append("<input type='hidden' id='txtAutorizado' name='txtAutorizado' value='")
                .append(strNota).append("' />")
                .append("<input type='hidden' id='txtIdPedido' name='txtIdPedido' value='").append(nota.getPedido().getIdPedido()).append("' />")
                .append("<input type='hidden' id='txtIdCliente' name='txtIdCliente' value='")
                .append(nota.getPedido().getCliente().getIdCliente()).append("' readonly='readOnly' />")
                .append("<input type='hidden' id='txtIdVendedor' name='txtIdVendedor' readonly='readOnly' value='")
                .append(nota.getPedido().getVendedor().getIdVendedor()).append("' />")
                .append("<input type='hidden' id='txtStatus' name='txtStatus' readonly='readOnly' value = 'E' readonly='readOnly' />")
                .append("<input type='hidden' id='txtStatusNota' name='txtStatusNota' readonly='readOnly' value = 'A' readonly='readOnly' />")
                .append("<table><thead><tr><th>FECHA</th><th>FOLIO</th></tr></thead>")
                .append("<tbody><td><input type='text' id='txtFecha' name='txtFecha' readonly='readOnly' value ='")
                .append(DateUtils.dateToString(DateUtils.dateNow())).append("' /></td><td>")
                .append("<input type='text' id='txtFolio' name='txtFolio' readonly='readOnly' value='")
                .append(nota.getFolio()).append("' /></td></tbody></table>")
                .append("<table class='listado tablesorter'><thead><tr><th style='text-align: center; width:25px;' >CLIENTE</th></tr></thead>")
                .append("<tbody><tr><td><strong>NOMBRE :</strong> <input type='text' id='txtNombreCliente' name='txtNombreCliente' value='")
                .append(nota.getNombreCliente()).append("' size='").append((nota.getNombreCliente().length() + 2)).append("' readOnly='readOnly' />")
                .append("</td></tr>").append("<tr><td><strong>DIRECCI&Oacute;N :</strong> <input type='text' id='txtDireccion' name='txtDireccion' value='")
                .append(nota.getDireccion()).append("' size='150' readOnly='readOnly' /></td></tr><tr><td>")
                .append("<strong>RFC :</strong> <input type='text' id='txtRfc' name='txtRfc' value='");
        if (!StringUtils.isReallyEmptyOrNull(nota.getRfc())) {
            form.append(nota.getRfc()).append("' size='").append((nota.getRfc().length() + 4)).append("' readOnly='readOnly' />");
        } else {
            form.append("NO APLICA ' size='").append((10 + 4)).append("' readOnly='readOnly' />");
        }

        form.append("</td></tr></tbody></table>");

        table.append("<br/> <table id='tblListNota' align='center' class='table table-bordered table-hover'  style='height: 70px;'>")
                .append(" <thead><tr><th style='text-align: center; width:25px;' >CANTIDAD</th><th style='text-align: center; width:25px;' >DESCRIPCION</th><th style='text-align: center; width:25px;' >KGS.</th><th style='text-align: center; width:25px; display:none;' >PRECIO U.</th><th style='text-align: center; width:25px;' >TOTAL</th><th style='text-align: center; width:25px;' >OBSERVACIONES</th></tr></thead> <tbody>");

        for (DtoDetalleNotaVenta detalles : nota.getDetalles()) {
            String desc = detalles.getNombreProducto();
            Integer maxDesc = desc.length();
            if (detalles.getIsAgotado() != false) {
                detalles.setSubTotal(BigDecimal.ZERO);
                detalles.setCostoUnitario(BigDecimal.ZERO);
                detalles.setCantidadKilos(BigDecimal.ZERO);
                desc += " AGOTADO ";
                maxDesc = desc.length();
            } else if (detalles.getIsMuestra() != false) {
                detalles.setSubTotal(BigDecimal.ZERO);
                detalles.setCostoUnitario(BigDecimal.ZERO);
                detalles.setCantidadKilos(BigDecimal.ZERO);
                desc += " MUESTRA ";
                maxDesc = desc.length();
            }
            subtotal = detalles.getCantidadKilos().multiply(
                    detalles.getCostoUnitario()
            ).setScale(2, BigDecimal.ROUND_DOWN);

            content.append("<tr id='trNotaPedido' class='table table-bordered table-hover'><td style='text-align: center; width:25px;' >")
                    .append("<input type='text' id='txtArrayPiezas[]' name='txtArrayPiezas[]' value='")
                    .append(detalles.getCantidadPiezas()).append("' readonly='readOnly'/></td>")
                    .append("<td>")
                    .append("<input type='text' id='txtArrayNombreProducto[]' name='txtArrayNombreProducto[]' value='")
                    .append(desc).append("' readonly='readOnly' size='").append((maxDesc + 2)).append("' /></td>")
                    .append("<td>")
                    .append("<input type='text' id='txtArrayKilos[]' name='txtArrayKilos[]' class='required' value='")
                    .append(detalles.getCantidadKilos()).append("' readOnly='readOnly' /></td>")
                    .append("<td style='display: none;'>")
                    .append("<input type='text' id='txtArrayCosto[]' name='txtArrayCosto[]' value='")
                    .append(NumberUtils.formatMoney(detalles.getCostoUnitario())).append("' readonly='readOnly' /></td>")
                    .append("<td>")
                    .append("<input type='text' id='txtArraySubTotal[]' name='txtArraySubTotal[]' value='")
                    .append(NumberUtils.formatMoney(subtotal)).append("' readonly='readOnly'/></td>")
                    .append("<td>")
                    .append("<input type='text' id='txtObservacionExcedido[]' name='txtObservacionExcedido[]' value='")
                    .append(!StringUtils.isReallyEmptyOrNull(detalles.getObservacionExcedido()) ? detalles.getObservacionExcedido() : "").append("' readonly='readOnly' />")
                    .append("</td></tr>");
            total = total.add(subtotal);
        }
        table.append(content);
        table.append("</tbody></table>");
        form.append(table);
        if (!StringUtils.isReallyEmptyOrNull(nota.getObservaciones())) {
            form.append("<br/>OBSERVACIONES:<br/>").append(nota.getObservaciones());
        }
        form.append("<br/><table class='table table-bordered table-hover' border='1' style='width: 1042px;'><tr>")
                .append("<td><label class='text-muted'>CANTIDAD CON LETRA :<br/>")
                .append(TraductorUtils.traducir(total)).append("</label></td>")
                .append("<td><table class='table table-bordered table-hover' border='1'><tr>")
                .append("<td class='text-muted'> SUBTOTAL </td><td>").append("0.00").append("</td></tr>")
                .append("<tr><td class='text-muted'>IVA</td><td> ").append("0.00").append("</td></tr>")
                .append("<tr><td class='text-muted'>TOTAL </td><td><input type='text' id='txtTotal' name='txtTotal' value='").append(NumberUtils.formatMoney(total)).append("' readOnly='readOnly' data-thousands=',' data-decimal='.'/></td></tr></table></td></tr></table><br/>")
                .append("<table class='table table-bordered table-hover' style='width: 775px;'>").append(nota.getLeyenda())
                .append("<td><table class='table table-bordered table-hover'><tr><td> _____________<br/><center>")
                .append("<label style='font-size:9px' class='text-muted'>FIRMA</label></center></td></tr></table></td></tr></table>");
        form.append("</form>");
        return form.toString();
    }
}
