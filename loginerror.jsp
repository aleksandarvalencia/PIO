<%@page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <jsp:include page="/WEB-INF/jsp/fragments/loginheader.jsp" />
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <title>LOGIN ERROR</title>
    </head>
    <body>
        <div class="container">
            <label class="caption"><b>THERE WAS AN ERROR IN APPLICATION LOGIN !</b></label>
        </div>
        
        <div class="container">
            <label class="caption">Message Description :
                    <c:if test="${not empty message}">
                         ${message}
                   </c:if>
            </label>
        </div>
        <div  class="container">
            <spring:url value="/login" var="userUrl" />
            <button class="btn-info" onclick="location.href='${userUrl}'">Back to login</button>
        </div>
    </body>
    
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
    
</html>

