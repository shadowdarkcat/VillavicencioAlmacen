<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../../decorators/scripts/scripts.jsp"/>
        <title>Menu Principal</title>
    </head>
    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        ${user.nombreCompleto}</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <a  href="${pageContext.request.contextPath}/decorators/principal/principal.jsp" target="_self">
                        <img src="${pageContext.request.contextPath}/image/logo.jpg" height="60px" width="60px" class="required"
                             title="Regresa a pagina principal"/>
                    </a>
                    <ul class="nav navbar-nav">                                                
                        ${menuUsuario}
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>
        <div class="container">
            <div class="row">
                <div id="divMensaje"class="col-sm-9 col-sm-offset-3 col-md-9 col-md-offset-2 main">
                    <div class="row placeholders">
                        <div class="col-xs-6 col-sm-9 placeholder">
                            <h4 class="text-muted">BIENVENIDO AL SISTEMA DE CONTROL DE ALMACEN</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div><!-- /.container -->
    </body>
</html>
