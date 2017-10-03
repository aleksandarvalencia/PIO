<%@page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>


<!DOCTYPE html>
<html lang="en">


 <head>
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>FORMA ZA IZMENU LOZINKE ::</title>
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
            

            window.onload = function() 
            {
                  
                           /*$(window).resize(function () 
                                   {
                                       var h = $(window).height();
                                       //var h = Math.max($(window).height(), 450);
                                       $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                               }).resize();*/
                            document.getElementById("menu_items").style.width = "0";
                            document.getElementById("menu_items").style.visibility = "hidden";
                            document.getElementById("menu_id").value="0";
             }
          
        </script>
 </head>
 <body >
   <input type="hidden" id="menu_id" name="menu_id" value="1"/>
    <table id="body" style="vertical-align:top;width:100%">
         <tr  style="width:100%;border-bottom:2px solid whitesmoke ">
                                    <td style="text-align:right;">
                                        <a href="#"><img border='0' src="/PIO/include/img/header_logo_SG_1.gif" alt="Close" style="margin-left:auto;margin-right:auto;"/></a>
                                        SOCIETE GENERALE BANKA SRBIJA
                                    </td>
                                    <td style="text-align:center;color:whitesmoke;font-size:20px;font-family:Arial">
                                        APLIKACIJA OBUSTAVA ::
                                    </td>
                                     <td>
                                        <a  href="#"><img border='0' src="/PIO/include/img/Close.png" alt="Close" style="margin-left:auto;margin-right:auto;" onclick="LogOut();"/></a>
                                   </td>
        </tr>
        <tr  style="width:100%;">
                <td > <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span></td>
                <td></td>
                <td></td>
        </tr>
       <tr style="width:100%"> 

           <td style="width:250px;vertical-align:top;">
                    <div id="menu_items" class="sidenavig">
                        <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
                    </div>
           </td>
            <td>
                 <h1 style="width:100%;color:whitesmoke;font-size:20px;">IZMENA LOZINKE ::</h1>
                 <hr style="width:100%;background-color:darkslategray;height:2px"/>   
                <br />
                <br />
                <spring:url value="/passwordChange" var="userActionUrl" />
                <form:form class="form-horizontal" method="post" modelAttribute="passwordForm" action="${userActionUrl}" style="height:1300px">
                
                        <form:hidden path="idUser" />

                        <spring:bind path="password">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <label style="color:whitesmoke"><b>Lozinka::</b></label>
                                        <div>
                                                <form:password path="password" class="form-control" id="password" placeholder="password" />
                                                <form:errors path="password" class="control-label" />
                                        </div>
                                </div>
                        </spring:bind>
                        <br/>
                         <spring:bind path="confirmPassword">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <label style="color:whitesmoke"><b>Potvrdi Lozinku::</b></label>
                                        <div>
                                                <form:password path="confirmPassword" class="form-control" id="confirmPassword" placeholder="confirm password" />
                                                <form:errors path="confirmPassword" class="control-label" />
                                                
                                        </div>
                                </div>
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <div>
                                        <br/>
                                           <form:button type="submit" class="btn-lg btn-primary pull-left" >Izmeni Lozinku</form:button>
                                    </div>
                            </div>
                        </spring:bind>

                </form:form>
            </td>
            <td></td>
        </tr>
    </table>

<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />

</body>

</html>