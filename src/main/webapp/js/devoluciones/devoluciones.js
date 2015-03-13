var arrayProducto = [];
var arraryCantidadPiezasSalida = [];
var arrayKilosSalida = [];
var arrayCantidadPiezas = [];
var arrayCantidadKilos = [];
var arrayCostoUnitario = [];
var arraySubtotal = [];
var exists;
var obs = '';
$(document).ready(function () {
    $('#tblNotas').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    $('#tblDetalleDevolucion').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

    if (('#divErrorDevolucion').length > 0) {
        $('#divErrorDevolucion').dialog({
            resizable: false
            , width: 300
            , height: 200
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

    if (('#divNotaNoSelected').length > 0) {
        $('#divNotaNoSelected').dialog({
            resizable: false
            , width: 300
            , height: 200
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

    if (('#divAltaRegistro').length > 0) {
        $('#divAltaRegistro').dialog({
            resizable: false
            , width: 1280
            , height: 900
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    $('#frmAltaDevolucion').submit();
                }
                , Cerrar: function () {
                    limpiar();
                    $(this).dialog('close');
                }
            }
        });
    }

    if (('#divAltaDevolucion').length > 0) {
        $('#divAltaDevolucion').dialog({
            resizable: false
            , width: 1280
            , height: 900
            , modal: true
            , cache: false
            , autoOpen: false
            , closeOnEscape: false
            , dialogClass: 'no-close'
            , buttons: {
                Aceptar: function () {
                    var flag = eval($('#divAltaDevolucion').find('#txtFueraRango').val());
                    if (flag == false) {
                        openDialog();
                    } else {
                        $('#divAutorizar').dialog('open');
                    }

                }
                , Cerrar: function () {
                    $('#divLateral').show();
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

    if (('#divAutorizar').length > 0) {
        $('#divAutorizar').dialog({
            resizable: false
            , width: 550
            , height: 350
            , modal: true
            , cache: false
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    $.ajax({
                        url: contextoGlobal + '/controller/devolucionesController'
                        , type: 'post'
                        , dataType: 'json'
                        , context: contextoGlobal
                        , cache: false
                        , data: {
                            method: 4
                            , txtUser: $('#divAutorizar').find('#txtUser').val()
                            , txtPass: $('#divAutorizar').find('#txtPass').val()
                            , ajax: true
                        }
                        , success: function (response) {
                            var arr = response.split(',');
                            if (arr[0] == 'true') {
                                obs = ' AUTORIZO ' + arr[1] + ', MOTIVO: DEVOLUCIÓN CON PESO FUERA DE RANGO';
                                $('#divAltaRegistro').find('#txtObservaciones').val(obs);
                                openDialog();
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
                    $(this).dialog('close');
                }
            }
        });
    }
    var table = $('#tblNotas').DataTable();

    $('#tblNotas tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        table.$('tr.selected').removeClass('selected');
        $(this).addClass('selected');
    });


    $('#tblDevolucion tbody').on('click', 'tr', function () {
        $(this).removeClass('selected');
        $(this).addClass('selected');
    });

    $('#btnAltaDevolucion').on('click', function () {
        var idNota = $('.selected').find('#txtIdNota').val();
        if ((idNota != null) && (idNota != undefined)) {
            findExistDevolucion(idNota);
            if ((exists != true) && (exists != undefined)) {
                getDataNotaVenta(idNota);
            } else {
                $('#divErrorDevolucion').dialog('open');
            }
        } else {
            $('#divNotaNoSelected').dialog('open');
        }
    });
});

function findExistDevolucion(idNota) {
    $.ajax({
        url: contextoGlobal + '/controller/devolucionesController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , async: false
        , data: {
            method: 1
            , txtIdNotaVenta: idNota
            , ajax: true
        }
        , success: function (response) {
            exists = response;
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function getDataNotaVenta(idNota) {
    $.ajax({
        url: contextoGlobal + '/controller/devolucionesController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 2
            , txtIdNotaVenta: idNota
            , ajax: true
        }
        , success: function (response) {
            var pesoKilos = [];
            $('#divAltaDevolucion').find('#lblNombre').empty();
            $('#divAltaDevolucion').find('#lblDireccion').empty();
            $('#divAltaDevolucion').find('#lblRfc').empty();
            $('#divAltaDevolucion').find('#tblListNota > tbody').empty();
            $('#divAltaDevolucion').find('#tblDetalleTotal').empty();
            $('#divAltaDevolucion').find('#tblListDevolucion > tbody').empty();

            $('#divAltaDevolucion').find('#txtIdNota').val(response.idNotaVenta);
            $('#divAltaDevolucion').find('#txtFecha').val(response.fecha);
            $('#divAltaDevolucion').find('#txtFolio').val(response.folio);
            $('#divAltaDevolucion').find('#lblNombre').text(response.nombreCliente);
            $('#divAltaDevolucion').find('#lblDireccion').text(response.direccion);
            $('#divAltaDevolucion').find('#lblRfc').text(response.rfc != null ? response.rfc : 'NO APLICA');
            $('#divAltaDevolucion').find('#tblDevolucion >tbody').empty();

            $('#divAltaRegistro').find('#lblNombre').empty();
            $('#divAltaRegistro').find('#lblDireccion').empty();
            $('#divAltaRegistro').find('#lblRfc').empty();
            $('#divAltaRegistro').find('#tblListNota > tbody').empty();
            $('#divAltaRegistro').find('#tblDetalleTotal').empty();
            $('#divAltaRegistro').find('#tblListDevolucion > tbody').empty();

            $('#divAltaRegistro').find('#txtIdNotaVenta').val(response.idNotaVenta);
            $('#divAltaRegistro').find('#txtFecha').val(response.fecha);
            $('#divAltaRegistro').find('#txtFolio').val(response.folio);
            $('#divAltaRegistro').find('#lblNombre').text(response.nombreCliente);
            $('#divAltaRegistro').find('#lblDireccion').text(response.direccion);
            $('#divAltaRegistro').find('#lblRfc').text(response.rfc != null ? response.rfc : 'NO APLICA');
            $('#divAltaRegistro').find('#tblDevolucionRegistro >tbody').empty();

            $.each(response.detalles, function (index, item) {
                pesoKilos.push(item.cantidadKilos);
            });

            var data = '';
            $.each(response.pedido.detalles, function (index, item) {
                data += ('<tr>'
                        + '<td><input type="text" id="txtProducto' + index + '" name="txtProducto' + index + '" value="' + item.nombreProducto + '" disabled="disabled" size="' + (item.nombreProducto.length + 2) + '" /></td>'
                        + '<td><center><label id="lblCantidadPiezasSalida' + index + '">' + item.cantidadPiezas + '</label></center></td>'
                        + '<td><center><label id="lblKilosSalida' + index + '">' + pesoKilos[index] + '<label></center></td>'
                        + '<td><input type="text" id="txtArrayCantidadPiezas' + index + '" name="txtArrayCantidadPiezas' + index + '" disabled="disabled" onKeyUp="showError(this,' + index + ');" />'
                        + '<label id="lblRequerido' + index + '" class="text-muted" style="display:none;">campo requerido</label></td>'
                        + '<td><input type="text" id="txtArrayPesoKilos' + index + '" name="txtArrayPesoKilos' + index + '" disabled="disabled" onKeyUp="cuentas(this,' + item.pesoMinimo + ',' + item.pesoMaximo + ',' + index + ');"/>'
                        + '<label id="lblRequeridoPeso' + index + '" class="text-muted" style="display:none;">Campo requerido</label>'
                        + '<label id="lblPesoFueraRango' + index + '" class="text-muted" style="display:none;">Peso fuera de rango</label></td>'
                        + '<td><input type="text" id="txtArrayTotalKilos' + index + '" name="txtArrayTotalKilos' + index + '" readOnly="readOnly" /></td>'
                        + '<td><input type="text" id="txtArrayCosto' + index + '" name="txtArrayCosto' + index + '" value="' + item.costoUnitario + '" size="' + (String(item.costoUnitario).length) + '" disabled="disabled" /></td>'
                        + '<td><input type="text" id="txtArraySubTotal' + index + '" name="txtArraySubTotal' + index + '" readOnly="readOnly" /></td>'
                        + '<td><input type="checkbox" id="chkDevolucion' + index + '" name="chkDevolucion' + index + '" onclick="activarDesactivar(this,' + index + ');" >'
                        + '<label id="lblDevolucion' + index + '" class="text-muted">NO</label></td>'
                        + '</tr>'
                        );
            });
            $('#divAltaDevolucion').find('#tblDevolucion >tbody').append(data);
            $('#divAltaDevolucion').find('#lblObservaciones').text(response.observaciones);
            $('#divLateral').hide();
            $('#divAltaDevolucion').dialog('open');
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
}

function activarDesactivar(check, index) {
    if (check.checked == true) {
        $('#divAltaDevolucion').find('#txtArrayCantidadPiezas' + index).removeProp('disabled');
        $('#divAltaDevolucion').find('#txtArrayPesoKilos' + index).removeProp('disabled');
        $('#divAltaDevolucion').find('#lblDevolucion' + index).text('SI');
    }
    if (check.checked == false) {
        $('#divAltaDevolucion').find('#txtArrayCantidadPiezas' + index).prop('disabled', true);
        $('#divAltaDevolucion').find('#txtArrayPesoKilos' + index).prop('disabled', true);
        $('#divAltaDevolucion').find('#txtArraySubTotal' + index).val('');
        $('#divAltaDevolucion').find('#txtArrayPesoKilos' + index).val('');
        $('#divAltaDevolucion').find('#txtArrayTotalKilos' + index).val('');
        $('#divAltaDevolucion').find('#txtArrayCantidadPiezas' + index).val('');
        $('#divAltaDevolucion').find('#lblDevolucion' + index).text('NO');
        $('#divAltaDevolucion').find('#lblRequerido' + index).hide();
        $('#divAltaDevolucion').find('#lblRequeridoPeso' + index).hide();
        $('#divAltaDevolucion').find('#lblPesoFueraRango' + index).hide();
        $('#divAltaDevolucion').find('#txtFueraRango').val('false');
    }
}

function showError(campo, index) {
    soloNumero(campo);
    var txtCantidad = campo.value;
    if (txtCantidad.length == 0 || /^\s+$/.test(txtCantidad)) {
        $('#divAltaDevolucion').find('#lblRequerido' + index).show();
    } else {
        $('#divAltaDevolucion').find('#lblRequerido' + index).hide();
    }
    cantidad = txtCantidad;
}

function cuentas(campo, minimo, maximo, index) {
    soloNumero(campo);
    var kgs = campo.value;
    var cantidad = $('#divAltaDevolucion').find('#txtArrayCantidadPiezas' + index).val();
    if (kgs.length == 0 || /^\s+$/.test(kgs)) {
        $('#divAltaDevolucion').find('#lblRequeridoPeso' + index).show();
        $('#divAltaDevolucion').find('#lblPesoFueraRango' + index).hide();
        $('#divAltaDevolucion').find('#txtArraySubTotal' + index).val('');
        $('#divAltaDevolucion').find('#txtArrayTotalKilos' + index).val('');
        return;
    } else {
        $('#divAltaDevolucion').find('#lblRequeridoPeso' + index).hide();
    }

    var pesoMinimo = (Number(minimo) * Number(cantidad)).toFixed(2);
    var pesoMaximo = (Number(maximo) * Number(cantidad)).toFixed(2);

    if ((kgs < Number(pesoMinimo)) || (kgs > Number(pesoMaximo))) {
        $('#divAltaDevolucion').find('#lblPesoFueraRango' + index).show();
        $('#divAltaDevolucion').find('#txtFueraRango').val('true');
    } else {
        $('#divAltaDevolucion').find('#lblPesoFueraRango' + index).hide();
        $('#divAltaDevolucion').find('#txtFueraRango').val('false');
    }
    $('#divAltaDevolucion').find('#txtArrayTotalKilos' + index).val(kgs);
    var costoUnitario = $('#divAltaDevolucion').find('#txtArrayCosto' + index).val();
    var total = costoUnitario * kgs;
    $('#divAltaDevolucion').find('#txtArraySubTotal' + index).val(total);
}


function llenarArray() {
    var indice = 0;

    var producto = '';
    var piezasSalida = '';
    var kilosSalida = '';
    var cantidadPiezas = '';
    var cantidadKilos = '';
    var costoUnitario = '';
    var subtotal = '';
    var checked = '';
    limpiar();
    $("#tblDevolucion tbody tr").each(function (index) {
        $(this).children("td").each(function (index2) {
            if ($(this).find('#txtProducto' + indice).val() != undefined) {
                producto = $(this).find('#txtProducto' + indice).val();
            }
            if ($(this).find('#lblCantidadPiezasSalida' + indice).val() != undefined) {
                piezasSalida = $(this).find('#lblCantidadPiezasSalida' + indice).text();
            }
            if ($(this).find('#lblKilosSalida' + indice).val() != undefined) {
                kilosSalida = $(this).find('#lblKilosSalida' + indice).text();
            }
            if ($(this).find('#txtArrayCantidadPiezas' + indice).val() != undefined) {
                cantidadPiezas = $(this).find('#txtArrayCantidadPiezas' + indice).val();
            }
            if ($(this).find('#txtArrayPesoKilos' + indice).val() != undefined) {
                cantidadKilos = $(this).find('#txtArrayPesoKilos' + indice).val();
            }
            if ($(this).find('#txtArrayCosto' + indice).val() != undefined) {
                costoUnitario = $(this).find('#txtArrayCosto' + indice).val();
            }
            if ($(this).find('#txtArraySubTotal' + indice).val() != undefined) {
                subtotal = $(this).find('#txtArraySubTotal' + indice).val();
            }
            checked = $(this).find('#chkDevolucion' + indice).is(':checked');
            if (checked == true) {
                arrayProducto.push(producto);
                arraryCantidadPiezasSalida.push(piezasSalida);
                arrayKilosSalida.push(kilosSalida);
                arrayCantidadPiezas.push(cantidadPiezas);
                arrayCantidadKilos.push(cantidadKilos);
                arrayCostoUnitario.push(costoUnitario);
                arraySubtotal.push(subtotal);
            }
        });
        indice += 1;
    });
}

function openDialog() {
    $('#divAltaRegistro').find('#tblDevolucionRegistro >tbody').empty();
    llenarArray();
    var data = '';
    var total = 0;
    var subtotal=0.00;
    for (var index = 0; index < arrayProducto.length; index++) {
        data += ('<tr>'
                + '<td><input type="text" id="txtArrayNombreProducto[]" name="txtArrayNombreProducto[]" value="' + arrayProducto[index] + '" readOnly ="readOnly" size="' + (arrayProducto[index].length + 2) + '" /></td>'
                + '<td><center>' + arraryCantidadPiezasSalida[index] + '</center></td>'
                + '<td><center>' + arrayKilosSalida[index] + '</center></td>'
                + '<td><input type="text" id="txtArrayPiezas[]" name="txtArrayPiezas[]" readOnly="readOnly" value="' + arrayCantidadPiezas[index] + '" /></td>'
                + '<td><input type="text" id="txtArrayKilos[]" name="txtArrayKilos[]" readOnly="readOnly" value="' + arrayCantidadKilos[index] + '"/></td>'
                + '<td><input type="text" id="txtArrayTotalKilos[]" name="txtArrayTotalKilos[]" readOnly="readOnly" value="' + arrayCantidadKilos[index] + '"/></td>'
                + '<td><input type="text" id="txtArrayCosto[]" name="txtArrayCosto[]" value="' + arrayCostoUnitario[index] + '" size="' + (String(arrayCostoUnitario[index]).length) + '" readOnly="readOnly" /></td>'
                + '<td><input type="text" id="txtArraySubTotal[]" name="txtArraySubTotal[]" readOnly="readOnly" value="' + arraySubtotal[index] + '" /></td>'
                + '</tr>'
                );
        subtotal += parseFloat(arraySubtotal[index]);
    }
    total = subtotal;
    var observaciones = $('#divAltaDevolucion').find('#lblObservaciones').text();
    observaciones = observaciones + ' ' + obs;
    $('#divAltaRegistro').find('#txtTotal').val(total);
    $('#divAltaRegistro').find('#tblDevolucionRegistro >tbody').append(data);
    $('#divAltaRegistro').find('#lblObservaciones').text(observaciones);
    $('#divAutorizar').dialog('close');
    $.ajax({
        url: contextoGlobal + '/controller/devolucionesController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 3
            , txtTotal: $('#divAltaRegistro').find('#txtTotal').val()
            , ajax: true
        }
        , success: function (response) {
            $('#divAltaRegistro').find('#lblCantidadLetra').text(response);
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log('ERROR L: ' + textStatus + ' (' + errorThrown + ')');
        }
    });
    $('#divAltaRegistro').dialog('open');
}

function limpiar() {
    arrayProducto.length = 0;
    arraryCantidadPiezasSalida.length = 0;
    arrayKilosSalida.length = 0;
    arrayCantidadPiezas.length = 0;
    arrayCantidadKilos.length = 0;
    arrayCostoUnitario.length = 0;
    arraySubtotal.length = 0;
}