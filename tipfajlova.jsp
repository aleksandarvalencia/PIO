<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html lang="en">


<head>
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>LISTA KORISNIKA ::</title>
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
                            $(window).resize(function () 
                                   {
                                       var h = $(window).height();
                                       //var h = Math.max($(window).height(), 450);
                                       $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                               }).resize();
                                  document.getElementById("menu_items").style.width = "0";
                                  document.getElementById("menu_items").style.visibility = "hidden";
                                  document.getElementById("menu_id").value="0";
                    };
           
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
                    function post(path) {
                    var method ="post"; 

                    var form = document.createElement("form");
                    form.setAttribute("method", method);
                    form.setAttribute("action", path);
                    document.body.appendChild(form);
                   /* var hiddenField = document.createElement("input");
                    hiddenField.setAttribute("type", "hidden");
                    hiddenField.setAttribute("name", "${_csrf.parameterName}");
                    hiddenField.setAttribute("value", "${_csrf.token}");
                    form.appendChild(hiddenField);*/
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
                    };
            </script>
 
 </head>
 
<body style="background-color:azure;">
<div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
</div>
<input type="hidden" id="menu_id" name="menu_id" value="1"/>
	<!--<div class="container">-->

                            <table id="body" style="width:100%">
                                <tr style="width:100%;border-bottom:2px solid whitesmoke ">
                                 <td>
                                        <table style="width:100%">
                                         <tr>
                                            <td style="text-align:left;">
                                                <a href="#"><img border='0' src="/PIO/include/img/header_logo_SG_1.gif" alt="Close" style="margin-left:auto;margin-right:auto;"/></a>
                                                SOCIETE GENERALE BANKA SRBIJA
                                            </td>
                                            <td style="text-align:left;color:whitesmoke;font-size:20px;font-family:Arial">
                                                 PIO APLIKACIJA::
                                            </td>
                                             <td>
                                                <a  href="#"><img border='0' src="/PIO/include/img/Close.png" alt="Close" style="margin-left:auto;margin-right:auto;" onclick="LogOut();"/></a>
                                            </td>
                                         </tr>
                                        </table>
                                  </td>
                                 </tr>
                                <tr style="width:100%;height:100%"> 
                                    <td style="vertical-align:top;">
                                        <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">TIPOVI FAJLOVA::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                                       </table>
                                       
                                        <table style="width:100%;">
                                           <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                    <table style="width:100%;vertical-align:top;">
                                                                <thead>
                                                                        <tr>

                                                                                <th style="background-color:maroon;width:50%;height:15px;font-size:12px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">VRSTA FAJLA</th>
                                                                                <th style="background-color:maroon;width:50%;height:15px;font-size:12px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">NAZIV FAJLA</th>

                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="pf" items="${penzije_fajlovi}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50%;font-size:  10px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid gray;">${pf.tip_fajla}</td>
                                                                                <td style="background-color:white;width:50%;font-size:  10px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid gray;">${pf.naziv_fajla}</td>
                                                                        </tr>
                                                                </c:forEach>
                                                        </table>
                                                  </td>
                                                </tr>
                                            </table>
                                            <br/>
                                          <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                         <p><b> &copy; PIO APPLICATION</b></p>   
                                  </td>
                                  <td></td>
                               </tr>  
                            </table>

	<!--</div>-->


<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
</body>
</html>