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
<!--    <link type="text/css" rel="stylesheet" media="screen" href="/PIO/resources/css/bootstrap-datetimepicker.min.css"> 
        <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.pt-BR.js"></script>-->
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>UPLATE IZ PIO ::</title>
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
                              document.getElementById("paymentDate").value=localStorage.getItem("PaymentDate");
                                $(window).resize(function () 
                                   {
                                       var h = $(window).height();
                                       //var h = Math.max($(window).height(), 450);
                                       $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                               }).resize();
                                 document.getElementById("menu_items").style.width = "0";
                                 document.getElementById("menu_items").style.visibility = "hidden";
                                 document.getElementById("menu_id").value="0";
                         } ;
            function LogOut()
                   {
                      
                      //localStorage.clear();
                      var form = document.createElement("form");
                      var method ="post"; 
                      var aa="/PIO/logOut";
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                       
                   }
                    window.onbeforeunload = function(event) 
                        {
                           if (event.keyCode!==116)
                            {
                                    //localStorage.clear();
                                    var form = document.createElement("form");
                                    var method ="post"; 
                                    var aa="/PIO/closeEvent";
                                    form.setAttribute("method", method);
                                    form.setAttribute("action", aa);
                                    document.body.appendChild(form);
                                    form.submit();
                              }
                         } ;
            function GetPayment()
            {
                var form = document.createElement("form");
                var method ="post"; 
                var datum=document.getElementById("paymentDate").value;
                localStorage.setItem("PaymentDate", datum);
                var aa="/PIO/getPayment/" + datum;
                form.setAttribute("method", method);
                form.setAttribute("action", aa);
                document.body.appendChild(form);
                form.submit();
                //ShowWaitingProcess();
            }
            
        </script>
 </head>
 
<body id="body" style="background-color:azure">
    
    <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
    </div>
    
    
    <input type="hidden" id="menu_id" name="menu_id" value="1"/>

                            <table id="body"  style="width:100%" >
                                 <tr style="width:100%;border-bottom:2px solid whitesmoke ">
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
                                  <tr style="width:100%;height:100%"> 
                                    <td id="body" style="background-color:azure;vertical-align:top;">
                                     <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">UPLATE IZ PIO::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                                       </table>
                                        <br/>
                                        <table style="font-size:10px;vertical-align:middle">
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td style="width:150px;color:whitesmoke;"><label  style="font-size:10px;background-color: maroon;height:25px;">DATUM PRILIVA::</label> </td>
                                            <td>
                                              
                                                    <div id="importedfilesdate" >

                                                            <div class="input-append date" >
                                                                <input type="text" id="paymentDate"  name="paymentDate"  style="width:150px;height:18px;background-color:lightgray;color:darkslategray"/>
                                                            <span class="add-on" style="height:18px">
                                                                      <i data-time-icon="icon-time" data-date-icon="icon-calendar" style="height:13px"></i>
                                                             </span>
                                                            </div>
                                                     </div>
                                               
                                            </td>
                                            <td style="width:20px">&nbsp;</td>
                                            <td style="width:150px"><button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;GetPayment();">PRONADJI UPLATE</button></td>
                                        </tr>
                                        </table>
                                        <table style="width:100%"> 
                                            <tr>
                                                <td style="width:50px">&nbsp;</td>
                                                <td>
                                                <hr style="color:whitesmoke"/>
                                                </td>
                                            </tr>
                                        </table>
                                        <br/>
                                        <br/>
                                        <table style="width:100%;">
                                           <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                    <table style="width:100%;vertical-align:top;">
                                                                <thead>
                                                                        <tr>
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-left:2px solid gray;border-top:2px solid gray;">ID</th>
                                                                            <th style="background-color:maroon;width:150px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">RACUN</th>
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">DATUM</th>
                                                                            <th style="background-color:maroon;width:100px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IZNOS </th>
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">RACUN</th>  
                                                                            <th style="background-color:maroon;width:250px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IME</th> 
                                                                            <th style="background-color:maroon;width:160px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">PBO</th> 
                                                                            <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">OPIS</th>                                                              
                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="piouplate" items="${uplate}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid gray;">${piouplate.ID}</td>
                                                                                <td style="background-color:white;width:150px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.racun}</td>
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.datum}</td>
                                                                                <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.iznos}</td>                                                                     
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.kontraracun}</td>     
                                                                                <td style="background-color:white;width:250px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.ime}</td>                                                                        
                                                                                <td style="background-color:white;width:160px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.pbo}</td>             
                                                                                <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.opis}</td>                                                                        
                                                                        </tr>
                                                                </c:forEach>

                                                        </table>
                                             </td>
                                           </tr>
                                       </table>
                                  </td>
                                  <td></td>
                               </tr>  
                            </table>
                                      
<script type="text/javascript">
                                $('#importedfilesdate').datetimepicker({
                                  format: 'yyyy-MM-dd',
                                  language: 'en-US'
                                });
</script> 


<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
</body>
</html>