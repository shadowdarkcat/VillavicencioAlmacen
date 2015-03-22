var ids = [];
var nombres = [];
var costos = [];
var comisiones = [];
var pesos = [];
var piezas = [];
var idsDetallePedido = [];
var pesoMinimo = [];
var pesoMaximo = [];
var pesoKilos = [];
var isAgotado = [];
var isMuestra = [];
var excedido = [];
var removeIdPedido;
var removeIdDetallePedido;
var idDelete;
var statusDelete;
var actionDelete;
var clienteDelete;
var vendedorDelete;
var dataResponse;
var lblStatus;
var tipo;
var disponible;
$(document).ready(function () {

    $('#tblPedidos').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });
    if ($('#divPedidoNoSelected').length > 0) {
        $('#divPedidoNoSelected').dialog({
            resizable: false
            , width: 300
            , height: 150
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divNotaPedidoNoVisible').length > 0) {
        $('#divNotaPedidoNoVisible').dialog({
            resizable: false
            , width: 300
            , height: 200
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divNotaPedidoErrorCredito').length > 0) {
        $('#divNotaPedidoErrorCredito').dialog({
            resizable: false
            , width: 300
            , height: 200
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divNotaPedidoErrorMonetario').length > 0) {
        $('#divNotaPedidoErrorMonetario').dialog({
            resizable: false
            , width: 300
            , height: 200
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divNotaPedido').length > 0) {
        $('#divNotaPedido').dialog({
            resizable: false
            , width: 1270
            , height: 800
            , modal: true
            , cache: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if (!validar()) {
                        getNotaVenta();
                    } else {
                        $('#divAutorizarPedido').dialog('open');
                    }
                }
                , Cerrar: function () {
                    changeSatus(idDelete, 'P', actionDelete, clienteDelete, vendedorDelete);
                    $(this).dialog('close');
                    $('#divLateral').show();
                }
            }
        });
    }

    if ($('#divDeleteProducto').length > 0) {
        $('#divDeleteProducto').dialog({
            resizable: false
            , width: 350
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    removeFromNota();
                    $(this).dialog('close');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if ($('#divAgregar').length > 0) {
        $('#divAgregar').dialog({
            resizable: false
            , width: 300
            , height: 170
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Aceptar: function () {
                    $(this).dialog('close');
                    add(dataResponse);
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divAutorizarPedido').length > 0) {
        $('#divAutorizarPedido').dialog({
            resizable: false
            , width: 550
            , height: 350
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $.ajax({
                        url: contextoGlobal + '/controller/notaVentaController'
                        , type: 'post'
                        , dataType: 'json'
                        , context: contextoGlobal
                        , cache: false
                        , data: {
                            method: 1
                            , txtUser: $('#divAutorizarPedido').find('#txtUser').val()
                            , txtPass: $('#divAutorizarPedido').find('#txtPass').val()
                            , ajax: true
                        }
                        , success: function (response) {
                            var arr = response.split(',');
                            if (arr[0] == 'true') {
                                $('#divTipoAutorizacionPedido').find('#txtUser').val(arr[1]);
                                var text = $('#divAutorizarPedido').find('#lblCausa').text();
                                $('#divTipoAutorizacionPedido').find('#lblAutorizaError').text(text);
                                $('#divTipoAutorizacionPedido').find('#txtCausa').val(text);
                                $('#divTipoAutorizacionPedido').dialog('open');
                            } else {
                                $('#divError').dialog('open');
                            }
                        }
                        , error: function (jqXHR, textStatus, errorThrown) {
                            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
                        }
                    });
                }
                , Cerrar: function () {
                    $('#divAutorizarPedido').find('#lblCausa').empty();
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divError').length > 0) {
        $('#divError').dialog({
            resizable: false
            , width: 350
            , height: 150
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divTipoAutorizacionPedido').length > 0) {
        $('#divTipoAutorizacionPedido').dialog({
            resizable: false
            , width: 850
            , height: 150
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    var us = $('#divTipoAutorizacionPedido').find('#txtUser').val();
                    $('#frmSurtePedido').find('#txtUser').val(us);
                    getNotaVenta();
                    $(this).dialog('close');
                    $('#divTipoAutorizacionPedido').dialog('close');
                    $('#divAutorizarPedido').dialog('close');
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divTipoAutorizacion').length > 0) {
        $('#divTipoAutorizacion').dialog({
            resizable: false
            , width: 550
            , height: 350
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $.ajax({
                        url: contextoGlobal + '/controller/notaVentaController'
                        , type: 'post'
                        , dataType: 'json'
                        , context: contextoGlobal
                        , cache: false
                        , data: {
                            method: 1
                            , txtUser: $('#divTipoAutorizacion').find('#txtUser').val()
                            , txtPass: $('#divTipoAutorizacion').find('#txtPass').val()
                            , ajax: true
                        }
                        , success: function (response) {
                            var arr = response.split(',');
                            if (arr[0] == 'true') {
                                $('#frmSurtePedido').find('#txtFechaPago').val('Fecha de pago vencido');
                                changeSatus(idDelete, 'S', actionDelete, clienteDelete, vendedorDelete);
                                $('#divLateral').hide();
                                $('#divTipoAutorizacion').find('#txtUser').val('');
                                $('#divTipoAutorizacion').find('#txtPass').val('');
                                $('#divNotaPedido').dialog('open');
                            } else {
                                $('#divError').dialog('open');
                            }
                        }
                        , error: function (jqXHR, textStatus, errorThrown) {
                            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
                        }
                    });
                }
                , Cerrar: function () {
                    changeSatus(idDelete, 'P', actionDelete, clienteDelete, vendedorDelete);
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divNotaVenta').length > 0) {
        $('#divNotaVenta').dialog({
            resizable: false
            , width: 1270
            , height: 1024
            , modal: true
            , cache: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $('#frmNotaVenta').submit();
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    var table = $('#tblPedidos').DataTable();
    $('#tblPedidos tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });
    $('#btnSurtirPedido').on('click', function () {
        var idPedido = $('.selected').find('#txtIdPedido').val();
        var status = $('.selected').find('#txtStatus' + idPedido).val();
        var action = $('#txtIdAction').val();
        var cliente = $('.selected').find('#txtIdCliente').val();
        var vendedor = $('.selected').find('#txtIdVendedor').val();
        lblStatus = 'lblStatus' + idPedido;
        idDelete = idPedido;
        statusDelete = status;
        actionDelete = action;
        clienteDelete = cliente;
        vendedorDelete = vendedor;
        if ((idPedido != null) && (idPedido != undefined)) {
            if ((status != 'C') && (status != undefined) && (status != 'E') && (status != 'S')) {
                if (validarClienteVendedor(cliente, vendedor)) {
                    status = 'S';
                    changeSatus(idPedido, status, action, cliente, vendedor);
                    $('#divLateral').hide();
                    $('#divNotaPedido').dialog('open');
                } else {
                    $('#divTipoAutorizacion').dialog('open');
                }
            } else {
                $('#divNotaPedidoNoVisible').dialog('open');
            }
        } else {
            $('#divPedidoNoSelected').dialog('open');
        }
    });
});
function validarClienteVendedor(cliente, vendedor) {
    if ((cliente != null) && (cliente != undefined)) {
        var fechaLimite = $('.selected').find('#txtFechaLimiteC').val();
        disponible = $('.selected').find('#txtDisponibleC').val();
        var contraNota = $('.selected').find('#txtContraNotaC').val();
        if ((Date.parse(new Date())) > Date.parse(fechaLimite)) {
            $('#divTipoAutorizacion').find('#lblCausa').text('Causa: Fecha de pago vencido');
            return false;
        } else if (disponible < 0) {
            $('#divNotaPedidoErrorMonetario').dialog('open');
            return;
        } else if ((contraNota != null) && (contraNota != undefined)) {
            $('#divNotaPedidoErrorCredito').dialog('open');
            return;
        }
    } else if ((vendedor != null) && (vendedor != undefined)) {
        var fechaLimite = $('.selected').find('#txtFechaLimiteV').val();
        disponible = $('.selected').find('#txtDisponibleV').val();
        var contraNota = $('.selected').find('#txtContraNotaV').val();
        if ((Date.parse(new Date())) > Date.parse(fechaLimite)) {
            $('#divTipoAutorizacion').find('#lblCausa').text('Causa: Fecha de pago vencido');
            return false;
        } else if (disponible < 0) {
            $('#divNotaPedidoErrorMonetario').dialog('open');
            return;
        } else if ((contraNota != null) && (contraNota != undefined)) {
            $('#divNotaPedidoErrorCredito').dialog('open');
            return;
        }
    }
    return true;
}

function changeSatus(id, status, action, cliente, vendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , context: contextoGlobal
        , cache: false
        , data: {
            method: 1
            , txtIdPedido: id
            , txtStatus: status
            , txtIdAction: action
            , txtIdCliente: cliente
            , txtIdVendedor: vendedor
            , context: contextoGlobal
            , ajax: true
        }
        , success: function (response) {
            var arrayKgsName = [];
            var arrayKgs = [];
            var minimo = [];
            var maximo = [];
            var indice = 0;
            $("#tblListNotaPedido tbody tr").each(function (index) {
                $(this).children("td").each(function (index2) {
                    if ($(this).find('#txtArrayKilos' + indice).val() != undefined) {
                        arrayKgsName.push($(this).find('#txtArrayKilos' + indice).attr("name"));
                        arrayKgs.push($(this).find('#txtArrayKilos' + indice).val());
                    }
                    if ($(this).find('#txtArrayPesoMinimo' + indice).val() != undefined) {
                        minimo.push($(this).find('#txtArrayPesoMinimo' + indice).val());
                    }
                    if ($(this).find('#txtArrayPesoMaximo' + indice).val() != undefined) {
                        maximo.push($(this).find('#txtArrayPesoMaximo' + indice).val());
                    }
                });
                indice += 1;
            });
            $('#divNotaPedido').empty();
            $("#tblPedidos tbody tr").each(function (index) {
                $(this).children("td").each(function (index2) {
                    var lblTabla = $(this).find('#lblStatus' + id).attr('id');
                    if (lblStatus == lblTabla) {
                        $(this).find('#lblStatus' + id).empty();
                        $(this).find('#lblStatus' + id).append(response.statusAlmacen);
                        $(this).find('#txtStatus' + id).val(response.statusAlmacen);
                    }
                });
            });
            $('#divNotaPedido').append(response.notaPedido);
            indice = 0;
            $.each(arrayKgsName, function (index0, item) {
                $("#tblListNotaPedido tbody tr").each(function (index) {
                    $(this).children("td").each(function (index2) {
                        if ($(this).find('#txtArrayKilos' + indice).attr("name") == arrayKgsName[index0]) {
                            $(this).find('#txtArrayKilos' + indice).val(arrayKgs[index0]);
                            if ($(this).find('#txtArrayKilos' + indice).length == 0 || /^\s+$/.test($(this).find('#txtArrayKilos' + indice).val())) {
                                cuenta(arrayKgs[index0], minimo[index0], maximo[index0], index);
                                return;
                            }
                        }
                    });
                });
                indice += 1;
            });
            $('#divNotaPedido').find('#txtLimiteCredito').val(disponible);
            getComboProducto(cliente, vendedor);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function getComboProducto(cliente, vendedor) {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 4
            , txtIdCliente: cliente
            , txtIdVendedor: vendedor
            , ajax: true
        }
        , success: function (response) {
            $('#tdCombo').empty();
            var tipoProducto;
            var cbo = 'Producto : <select id="cboProducto" name="cboProducto" class="required" onChange="getData();"><option value="">Seleccione ...</option>'
            var dataCbo = '';
            if ((response.establecidos != undefined) && (response.establecidos.length > 0)) {
                tipoProducto = 0;
                $.each(response.establecidos, function (index, item) {
                    dataCbo += '<option value="' + item.idProducto + '">' + item.nombreProducto + '</option>';
                });
                cbo += dataCbo + '</select>';
            } else if ((response.personalizados != undefined) && (response.personalizados.length > 0)) {
                tipoProducto = 1;
                $.each(response.personalizados, function (index, item) {
                    dataCbo += '<option value="' + item.idProducto + '">' + item.nombreProducto + '</option>';
                });
                cbo += dataCbo + '</select>';
            }
            cbo += '</select><input type="hidden" id="txtTipoProducto" name="txtTipoProducto" value = "' + tipoProducto + '" readOnly = "readOnly" />'
                    + '<input type="hidden" id="txtIdClienteNew" name="txtIdClienteNew" value = "' + (cliente != undefined ? cliente : '') + '" readOnly = "readOnly" />'
                    + '<input type="hidden" id="txtIdVendedorNew" name="txtIdVendedorNew" value = "' + (vendedor != undefined ? vendedor : '') + '" readOnly = "readOnly" />';
            $('#tdCombo').append(cbo);
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function agotado(check, txtKilos, txtCosto, costo, index) {
    if (check.checked) {
        $('#' + txtKilos.name).val('0.00');
        $('#' + txtKilos.name).prop('readOnly', 'readOnly');
        $('#' + txtCosto.name).val('$0.00');
        $('#' + txtCosto.name).prop('readOnly', 'readOnly');
        $('#lblError' + index).hide();
        $('#lblRequeridoPeso' + index).hide();
    } else {
        $('#' + txtKilos.name).val('');
        $('#' + txtKilos.name).removeProp('readOnly');
        $('#' + txtCosto.name).val(costo);
        $('#' + txtCosto.name).prop('readOnly', 'readOnly');
    }
}

function isVacio(campo, index) {
    var val = campo.value;
    var error = $('#divNotaPedido').find('#txtFueraRango').val();
    if (val.length == 0 || /^\s+$/.test(val)) {
        $('#divNotaPedido').find('#lblRequeridoPeso' + index).show();
        $('#divNotaPedido').find('#lblError' + index).hide();
        return;
    } else {
        if ((!error) && (error != undefined)) {
            $('#divNotaPedido').find('#lblError' + index).hide();
        }
    }
}

function sendData() {
    var txtCantidad = $('#txtCantidad').val();
    if (txtCantidad.length == 0 || /^\s+$/.test(txtCantidad)) {
        $('#lblRequerido').show();
        return;
    } else {
        $('#lblRequerido').hide();
        $('#divAgregar').dialog('open');
    }
}

function getData() {
    var idProducto = document.getElementById('cboProducto').value;
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 5
            , txtIdProducto: idProducto
            , txtTipoProducto: $('#tblExtras').find('#txtTipoProducto').val()
            , txtIdClienteNew: $('#tblExtras').find('#txtIdClienteNew').val()
            , txtIdVendedorNew: $('#tblExtras').find('#txtIdVendedorNew').val()
            , ajax: true
        }
        , success: function (response) {
            $('#lblAgotado').hide();
            $('#chkMuestra').prop('disabled', false);
            $('#txtCantidad').prop('disabled', false);
            $('#btnAgregar').prop('disabled', false);
            dataResponse = response;
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function add(responseAdd) {
    var idCliente = $('#divNotaPedido').find('#txtIdCliente').val();
    var idVendedor = $('#divNotaPedido').find('#txtIdVendedor').val();
    var idPedido = $('#divNotaPedido').find('#txtIdPedido').val();
    var action = $('#divNotaPedido').find('#txtIdAction').val();
    var status = $('#divNotaPedido').find('#txtStatus').val();
    var check = document.getElementById('chkMuestra').checked;
    var cantidadPiezas = $('#divNotaPedido').find('#txtCantidad').val();
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 6
            , txtIdCliente: idCliente
            , txtIdVendedor: idVendedor
            , txtIdPedido: idPedido
            , response: responseAdd
            , chkMuestra: check
            , txtCantidad: cantidadPiezas
            , ajax: true
        }
        , success: function (response) {
            changeSatus(idPedido, status, action, idCliente, idVendedor);
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function soloNumero(campo) {
    campo.value = (campo.value + '').replace(/[^.0-9]/g, '');
}

function cuenta(campo, minimo, maximo, index) {
    var kgs;
    if (campo.value != undefined) {
        kgs = campo.value;
    } else {
        kgs = campo;
    }
    soloNumero(campo);
    if (kgs.length == 0 || /^\s+$/.test(kgs)) {
        $('#divNotaPedido').find('#lblRequeridoPeso' + index).show();
        $('#divNotaPedido').find('#lblError' + index).hide();
        return;
    } else {
        $('#divNotaPedido').find('#lblRequeridoPeso' + index).hide();
        $('#divNotaPedido').find('#lblError' + index).hide();
    }

    if ((Number(kgs) < Number(minimo)) || (Number(kgs) > Number(maximo))) {
        $('#divNotaPedido').find('#lblError' + index).show();
        $('#divNotaPedido').find('#txtFueraRango').empty();
        $('#divNotaPedido').find('#txtFueraRango').val('true');
        $('#divAutorizarPedido').find('#lblCausa').text('Causa(s) : Peso fuera de rango');
    } else {
        $('#divNotaPedido').find('#lblError' + index).hide();
        $('#divNotaPedido').find('#txtFueraRango').empty();
        $('#divAutorizarPedido').find('#lblCausa').empty();
        $('#divNotaPedido').find('#txtFueraRango').val('false');
        if (pesoKilos.length > 0) {
            pesoKilos.splice(index - 1, 1);
        }
    }
    pesoKilos.push(kgs);
}

function removeData(idPedido, idDetallePedido, nombre, folio) {
    removeIdPedido = idPedido;
    removeIdDetallePedido = idDetallePedido;
    var tdProducto = '<span class="text-muted" >El producto ' + nombre + ' ser&aacute; eliminado </span>';
    var tdFolio = '<span class="text-muted" > de la nota de pedido con folio ' + folio + '</span>';
    $('#divDeleteProducto').find('#tdProducto').empty();
    $('#divDeleteProducto').find('#tdtFolio').empty();
    $('#divDeleteProducto').find('#tdProducto').append(tdProducto);
    $('#divDeleteProducto').find('#tdtFolio').append(tdFolio);
    $('#divDeleteProducto').dialog('open');
}

function removeFromNota() {
    $.ajax({
        url: contextoGlobal + '/controller/pedidosController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 3
            , txtIdPedido: removeIdPedido
            , txtIdDetalle: removeIdDetallePedido
            , ajax: true
        }
        , success: function (response) {
            if (response == true) {
                quitar(removeIdDetallePedido);
                changeSatus(idDelete, statusDelete, actionDelete, clienteDelete, vendedorDelete);
            }
        }, error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function quitar(indexRow) {
    $("#trNotaPedido" + indexRow).remove();
}

function validar() {
    var indice = 0;
    var fueraRango = $('#divNotaPedido').find('#txtFueraRango').val();
    var errorInventario = $('#divNotaPedido').find('#txtErrorInventario').val();
    var limite = disponible;
    var total = 0;
    var kilos = [];
    var cost = [];
    var text = $('#divAutorizarPedido').find('#lblCausa').text();
    $("#tblListNotaPedido tbody tr").each(function (index) {
        $(this).children("td").each(function (index2) {
            if ($('#divNotaPedido').find('#lblError' + index).is(':visible')) {
                $('#divNotaPedido').find('#txtFueraRango').empty();
                $('#divAutorizarPedido').find('#lblCausa').empty();
                $('#divNotaPedido').find('#txtFueraRango').val('true');
                $('#divAutorizarPedido').find('#lblCausa').text('Causa(s) : Peso fuera de rango');
            }
            if ($(this).find('#txtArrayKilos' + indice).val() != undefined) {
                kilos.push(Number($(this).find('#txtArrayKilos' + indice).val()));
            }
            if ($(this).find('#txtArrayCosto' + indice).val() != undefined) {
                cost.push(Number($(this).find('#txtArrayCosto' + indice).val().replace(/[^0-9\.]+/g, "")));
            }
        });
        indice += 1;
    });
    for (var index = 0; index < kilos.length; index++) {
        total += kilos[index] * cost[index];
    }
    limite = limite - total;

    if (errorInventario == undefined) {
        errorInventario = false;
    }

    if (!verifyEmpty()) {
        var fecha = $('#frmSurtePedido').find('#txtFechaPago').val();
        if ((!fueraRango) && (!errorInventario) && (limite > 0)) {
            return false;
        } else if ((fueraRango) && (errorInventario) && (limite < 0)) {
            if ((fecha != undefined) && (fecha != null)) {
                text = ((text != undefined && text != '') ? (text += ', fecha de pago vencido') : ('Causa(s) : Fecha de pago vencido'));
                $('#divAutorizarPedido').find('#lblCausa').text(text);
            }
            text = ((text != undefined && text != '') ? (text += ', excede piezas inventario, límite de crédito excedido') : ('Causa(s) : Excede piezas inventario, límite de crédito excedido'));
            $('#divAutorizarPedido').find('#lblCausa').text(text);
            return true;
        }
        else if ((errorInventario) && (limite < 0)) {
            if ((fecha != undefined) && (fecha != null)) {
                text = ((text != undefined && text != '') ? (text += ', fecha de pago vencido') : ('Causa(s) : Fecha de pago vencido'));
                $('#divAutorizarPedido').find('#lblCausa').text(text);
            }
            text = ((text != undefined && text != '') ? (text += ', excede piezas inventario, límite de crédito excedido') : ('Causa(s) : Excede piezas inventario, límite de crédito excedido'));
            $('#divAutorizarPedido').find('#lblCausa').text(text);
            return true;
        }
        else if (errorInventario) {
            if ((fecha != undefined) && (fecha != null)) {
                text = ((text != undefined && text != '') ? (text += ', fecha de pago vencido') : ('Causa(s) : Fecha de pago vencido'));
                $('#divAutorizarPedido').find('#lblCausa').text(text);
            }
            text = ((text != undefined && text != '') ? (text += ', excede piezas inventario') : ('Causa(s) : Excede piezas inventario'));
            $('#divAutorizarPedido').find('#lblCausa').text(text);
            return true;
        }
        else if (limite < 0) {
            if ((fecha != undefined) && (fecha != null)) {
                text = ((text != undefined && text != '') ? (text += ', fecha de pago vencido') : ('Causa(s) : Fecha de pago vencido'));
                $('#divAutorizarPedido').find('#lblCausa').text(text);
            }
            text = ((text != undefined && text != '') ? (text += ', límite de crédito excedido') : ('Causa(s) : Límite de crédito excedido'));
            $('#divAutorizarPedido').find('#lblCausa').text(text);
            return true;
        }


    }
    return false;
}

function verifyEmpty() {
    var indice = 0;
    var flag = false;
    $("#tblListNotaPedido tbody tr").each(function (index) {
        $(this).children("td").each(function (index2) {
            if ($(this).find('#txtArrayKilos' + indice).val() != undefined) {
                var value = $(this).find('#txtArrayKilos' + indice).val();
                if (value.length == 0 || /^\s+$/.test(value)) {
                    $('#divNotaPedido').find('#lblRequeridoPeso' + index).show();
                    $('#divNotaPedido').find('#lblError' + index).hide();
                    flag = true;
                }
            }
        });
        indice += 1;
    });
    return flag;
}

function getNotaVenta() {
    getDataIds();
    $.ajax({
        url: contextoGlobal + '/controller/notaVentaController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 2
            , txtFecha: $('#frmSurtePedido').find('#txtFecha').val()
            , txtFolio: $('#frmSurtePedido').find('#txtFolio').val()
            , txtNombreCliente: $('#frmSurtePedido').find('#txtNombreCliente').val()
            , txtDireccion: $('#frmSurtePedido').find('#txtDireccion').val()
            , txtRfc: $('#frmSurtePedido').find('#txtRfc').val()
            , txtArrayNombreProducto: nombres
            , txtArrayPiezas: piezas
            , txtArrayCosto: costos
            , txtArrayKilos: pesoKilos
            , txtIdPedido: $('#frmSurtePedido').find('#txtIdPedido').val()
            , txtIdCliente: $('#frmSurtePedido').find('#txtIdCliente').val()
            , txtIdVendedor: $('#frmSurtePedido').find('#txtIdVendedor').val()
            , txtCausa: $('#divTipoAutorizacionPedido').find('#txtCausa').val()
            , txtUser: $('#frmSurtePedido').find('#txtUser').val()
            , arrayChkAgotado: isAgotado
            , arrayChkMuestra: isMuestra
            , txtArrayExcedido: excedido
            , context: contextoGlobal
            , txtIdAction: $('#divNotaPedido').find('#txtIdAction').val()
            , txtIdCredito: $('#frmSurtePedido').find('#txtIdCredito').val()
            , ajax: true
        }
        , success: function (response) {
            $('#divNotaVenta').empty();
            $('#divNotaVenta').append(response);
            $('#divNotaVenta').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function getDataIds() {
    piezas.length = 0;
    nombres.length = 0;
    pesoKilos.length = 0;
    costos.length = 0;
    isAgotado.length = 0;
    isMuestra.length = 0;
    excedido.length = 0;
    var indice = 0;
    $("#tblListNotaPedido tbody tr").each(function (index) {
        $(this).children("td").each(function (index2) {
            if ($(this).find('#txtArrayCantidad' + indice).val() != undefined) {
                piezas.push($(this).find('#txtArrayCantidad' + indice).val());
            }
            if ($(this).find('#txtArrayNombreProducto' + indice).val() != undefined) {
                nombres.push($(this).find('#txtArrayNombreProducto' + indice).val());
            }
            if ($(this).find('#txtArrayKilos' + indice).val() != undefined) {
                pesoKilos.push($(this).find('#txtArrayKilos' + indice).val());
            }
            if ($(this).find('#txtArrayCosto' + indice).val() != undefined) {
                costos.push($(this).find('#txtArrayCosto' + indice).val());
            }
            if ($(this).find('#existencia' + indice).val() != undefined) {
                isAgotado.push($(this).find('#existencia' + indice).is(':checked'));
            }
            if ($(this).find('#muestra' + indice).val() != undefined) {
                isMuestra.push($(this).find('#muestra' + indice).is(':checked'));
            }
            if ($(this).find('#txtArrayExcedido' + indice).val() != undefined) {
                excedido[indice] = (parseInt($(this).find('#txtArrayExcedido' + indice).val()));
                excedido[(indice + 1) ] = 0;
            }
        });
        indice += 1;
    });
}