$(document).ready(function () {
    $('#tblInventario').DataTable({
        language: {
            url: contextoGlobal + '/resource/es_ES.json'
        }
    });

$('.solo-numero').keyup(function () {
        this.value = (this.value + '').replace(/[^0-9]/g, '');
    });
    
    if ($('#divInventarioNoSelected').length > 0) {
        $('#divInventarioNoSelected').dialog({
            resizable: false
            , width: 300
            , height: 250
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

    if ($('#divModificarInventario').length > 0) {
        $('#divModificarInventario').dialog({
            resizable: false
            , width: 360
            , height: 200
            , modal: true
            , autoOpen: false
            , buttons: {
                Aceptar: function () {
                    if ($('#frmModificaInventario').validate().form()) {
                        $('#frmModificaInventario').submit();
                    } 
                }
                , Cerrar: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

    var table = $('#tblInventario').DataTable();
    $('#tblInventario tbody').on('click', 'tr', function () {
           $(this).removeClass('selected');
           table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
    });

    $('#btnModificarInventario').on('click', function () {
        $('#divModificarInventario').find('#lblNombre').empty();
        var idInventario = $('.selected').find('#txtIdInventario').val();
        var nombreProducto = $('.selected').find('#txtNombreProducto').val();
        if ((idInventario != null) && (idInventario != undefined)) {
            if ((nombreProducto != null) && (nombreProducto != undefined)) {
                $('#divModificarInventario').find('#txtIdInventario').val(idInventario);
                $('#divModificarInventario').find('#lblNombre').append(nombreProducto);
                $('#divModificarInventario').find('#txtNombreProducto').val(nombreProducto);
                $('#divModificarInventario').dialog('open');
            }
        } else {
            $('#divInventarioNoSelected').dialog('open');
        }
    });
});