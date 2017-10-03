<%@page session="false" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="javax.servlet.jsp.PageContext" %>
<html>
    <head>

    <style>
            #body {
                background: url(include/img/slika1.jpg) no-repeat center center fixed; /* For IE 6 and 7 */
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }
        </style>
         <script type="text/javascript">
           
                    window.onbeforeunload =function(e) 
                    {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var aa="/HALKOM/closeEvent";
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();

                    };
        </script>
    </head>

    <body id="body">  
<!--      <img src="/include/slika1.jpg"  style="width:50px;height:50px;">-->

                <jsp:include page="/WEB-INF/jsp/fragments/loginheader.jsp" />

                <div class="container">

                         <core:if test="${not empty param.err}">
                                <div><core:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></div>
                            </core:if>
                            <core:if test="${not empty param.out}">
                                <div>You've logged out successfully.</div>
                            </core:if>
                            <core:if test="${not empty param.time}">
                                <div>You've been logged out due to inactivity.</div>
                            </core:if>



                <spring:url value="/login" var="loginUrl" />
                <form:form  method="POST" modelAttribute="loginForm" action="${loginUrl}">
                <form:hidden path="id" />
                <table>
                    <tr style="height:50px;width:1380px">
                        <td style="width:460px">
                        </td>
                        <td style="width:460px">
                        </td>
                         <td style="width:460px">
                        </td>
                    </tr>
                     <tr style="width:1380px">
                         <td style="width:460px">
                         </td>
                         <td style="width:460px">
                             <label style="color:lavender">User Name:</label><form:input path="username"/> 
                            <font color="red"><form:errors path="username"/></font><br/><br/>
                         </td>
                         <td style="width:460px">
                         </td>
                     </tr>
                      <tr style="width:1380px">
                         <td style="width:460px">
                         </td>
                         <td style="width:460px">
                            <label style="color:lavender">Password:</label><form:password path="password"/>
                            <font color="red"><form:errors path="password"/></font><br/><br/>
                            <!--<input type="hidden" name="${_csrf.parameterName}"   value="${_csrf.token}" />-->
                        </td>
                        <td style="width:460px">
                        </td>
                     </tr>
                      <tr style="width:1380px">
                         <td style="width:460px">
                         </td>
                         <td style="width:460px">
                            <input type="submit" value="Login"/>
                        </td>
                        <td style="width:460px">
                        </td>
                     </tr>
                      <tr style="height:50px;width:1440px">
                        <td style="width:460px">
                        </td>
                        <td style="width:460px">
                        </td>
                         <td style="width:460px">
                        </td>
                    </tr>
                </table>
                </form:form>


                <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />

                 </div>
</body>
</html>	
