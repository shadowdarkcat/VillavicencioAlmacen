<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <page:applyDecorator name="villavicencio-theme" encoding="utf-8">
        <head>
            <jsp:include page="../../decorators/principal/principal.jsp"/>
            <title>Devoluciones</title>
        </head>
        <body>            
            <c:choose>
                <c:when test="${id == 0}">
                    <jsp:include page="alta/alta.jsp" />                    
                </c:when>                
            </c:choose>
        </body>    
    </page:applyDecorator>
</html>