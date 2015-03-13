<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib uri="/WEB-INF/images.tld" prefix="images"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Surtir Pedido</title>
    </head>
    <body>
        <jsp:include page="../detalles/detalles.jsp" />
        <jsp:include page="divNotaPedido.jsp" />
        <jsp:include page="../autorizacion/divAutorizacion.jsp" />
        <jsp:include page="../surtir/divNotaVenta.jsp" />
    </body>
</html>
