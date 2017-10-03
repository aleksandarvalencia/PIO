<%@page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<%@page import="java.util.Calendar"%>


<!DOCTYPE html>
<html lang="en">


 <head>
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
         <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>FORMA ZA ADMINISTRACIJU KORISNIKA ::</title>
         <style>
            #body {
                background: url(/PIO/include/img/slika1.jpg) no-repeat center center fixed; /* For IE 6 and 7 */
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
          
           function LogOut()
                   {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var aa="/PIO/logOut";
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                       
                   }
                window.onbeforeunload =function() 
                    {
                        
                       if (event.keyCode!==116)
                       {
                            var form = document.createElement("form");
                            var method ="post"; 
                            var aa="/PIO/closeEvent";
                            form.setAttribute("method", method);
                            form.setAttribute("action", aa);
                            document.body.appendChild(form);
                            form.submit();
                        }
                        localStorage.clear();
                    };
            function GetAuthList()
            {
                     $.getJSON('<spring:url value="/interauto.json"/>',
                        {
                          ajax : 'true'
                        },function(data)
                        {
                            var html1='<option value=""> --- Please select one -----</option>';

                            var len=data.length;
                            for(var i=0; i< len; i++)
                            {
                                html1 += '<option value="' + data[i].value + '">' + data[i].label + '</option>';
                            }
                            html1+='</option>';
                            $('#auth').html(html1);
                        }
                            );
            }

         </script>
        
 </head>
 <body id="body" style="background-color:azure;">
 <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
</div>
 <input type="hidden" id="menu_id" name="menu_id" value="1"/>
    <table style="vertical-align:top;width:100%">
         <tr id="body" style="width:100%;border-bottom:2px solid whitesmoke ">
                                 <td>
                                        <table style="width:100%">
                                         <tr>
                                            <td style="text-align:left;">
                                                <a href="#"><img border='0' src="/PIO/include/img/header_logo_SG_1.gif" alt="Close" style="margin-left:auto;margin-right:auto;"/></a>
                                                SOCIETE GENERALE BANKA SRBIJA
                                            </td>
                                            <td style="text-align:left;color:whitesmoke;font-size:20px;font-family:Arial">
                                                 APLIKACIJA OBUSTAVA ::
                                            </td>
                                             <td>
                                                <a  href="#"><img border='0' src="/PIO/include/img/Close.png" alt="Close" style="margin-left:auto;margin-right:auto;" onclick="LogOut();"/></a>
                                            </td>
                                         </tr>
                                        </table>
                                  </td>
                                 </tr>
        <tr>
       <tr style="width:100%"> 


            <td >
                <c:choose>
                        <c:when test="${empty userForm.idUser}"> 
                             <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">DODAJ KORISNIKA::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                              </table>
                             <spring:url value="/users/insert" var="userActionUrl" />

                        </c:when>
                        <c:otherwise>
                                <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">IZMENI KORISNIKA::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                               </table>
                                 <spring:url value="/users/${userForm.idUser}/update" var="userActionUrl" />
                        </c:otherwise>
                </c:choose>
                                

                <table style="width:100%"> 
                    <tr>
                        <td style="width:15%">&nbsp;</td>
                        <td style="width:85%">
                        <h1 style="color:whitesmoke;font-size:20px;">DETALJI O KORISNIKU ::</h1>
                        </td>
                    </tr>
                </table>
                
                <br />

               <table style="width:100%;">
                <tr>
                    <td style="width:15%">&nbsp;</td>
                    <td style="width:85%">
                                <form:form  class="form-horizontal" method="post" modelAttribute="userForm" action="${userActionUrl}" style="height:100%">

                                        <form:hidden path="idUser" />

                                        <form:hidden path="id_Userold" />

                                        <spring:bind path="username">
                                                <div class="form-group ${status.error ? 'has-error' : ''}" style="width:100%">
                                                    <label style="color: whitesmoke"> <b>Korisnik:</b></label>
                                                        <div>
                                                                <form:input path="username" style="width:40%" type="text" class="form-control " id="username" placeholder="User Name" onchange="javascript:GetAuthList();"/>
                                                                <form:errors path="username" class="control-label" />
                                                        </div>
                                                </div>
                                        </spring:bind>
                                        <br />
                                        <spring:bind path="firstname">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                        <label style="color: whitesmoke"><b>Ime:</b></label>
                                                        <div>
                                                                <form:input path="firstname" style="width:40%"  class="form-control" id="firstname" placeholder="First Name" />
                                                                <form:errors path="firstname" class="control-label" />
                                                        </div>
                                                </div>
                                        </spring:bind>
                                        <br />
                                         <spring:bind path="secondname">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                        <label style="color: whitesmoke"><b>Prezime:</b></label>
                                                        <div>
                                                                <form:input path="secondname" style="width:40%"  class="form-control" id="secondname" placeholder="Second Name" />
                                                                <form:errors path="secondname" class="control-label" />
                                                        </div>
                                                </div>
                                        </spring:bind>
                                        <br />
                                        <spring:bind path="password">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                        <label style="color: whitesmoke"><b>Lozinka:</b></label>
                                                        <div>
                                                                <form:password path="password" style="width:40%"  class="form-control" id="password" placeholder="password" />
                                                                <form:errors path="password" class="control-label" />
                                                        </div>
                                                </div>
                                        </spring:bind>
                                        <br />
                                         <spring:bind path="confirmPassword">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                        <label style="color: whitesmoke"><b>Potvrdi Lozinku:</b></label>
                                                        <div>
                                                                <form:password path="confirmPassword" style="width:40%"  class="form-control" id="confirmPassword" placeholder="confirm password" />
                                                                <form:errors path="confirmPassword" class="control-label" />
                                                        </div>
                                                </div>
                                        </spring:bind>
                                    <br />
                                        <spring:bind path="idAuth">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                                <label style="color: whitesmoke"><b>Autorizacija:</b></label>
                                                                <div style="width:200px;">

                                                                    <form:select class="form-control" id="auth" path="idAuth" style="width:200px;">
                                                                     <form:options items="${authorization}" itemValue="value" itemLabel="label"></form:options>
                                                                    </form:select>
                                                                </div>
                                                </div>
                                        </spring:bind>    

                                        <br/>
                                        <div class="form-group">
                                                <div class="col-sm-offset-2 col-sm-10">
                                                        <c:choose>
                                                                <c:when test="${empty userForm.idUser}">
                                                                    <button type="submit" class="btn-lg btn-primary pull-left" style="width:180px" >Dodaj Korisnika</button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                        <button type="submit" class="btn-lg btn-primary pull-left" style="width:180px">Izmeni Korisnika</button>
                                                                </c:otherwise>
                                                        </c:choose>
                                                </div>
                                        </div>
                                </form:form>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <br></br>
</body>

  <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />

</html>