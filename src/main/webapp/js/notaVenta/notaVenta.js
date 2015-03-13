$(document).ready(function () {
    if ($('#divImpresionTicket').length > 0) {
        $('#divImpresionTicket').dialog({
            resizable: false
            , width: 850
            , height: 200
            , autoOpen: false
            , cache: false
            , modal: true
            , buttons: {
                Reintentar: function () {
                    var id = $('#divImpresionTicket').find('#txtIdNotaVenta').val();
                    imprimir(id);
                }
            }
        });
    }

    $(function () {
        var error = $('#divImpresionTicket').find('#errorTicket').val();
        if (error == 'false') {
            $('#divImpresionTicket').dialog('open');
        }
    });
});

function imprimir(id) {
    $.ajax({
        url: contextoGlobal + '/controller/notaVentaController'
        , type: 'post'
        , dataType: 'json'
        , cache: false
        , data: {
            method: 4
            , txtIdNotaVenta: id
            , ajax: true
        }
        , success: function (response) {
            if (response) {
                $(this).dialog('close');
            }
        }
        , error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR L: " + textStatus + " (" + errorThrown + ")");
        }
    });
}