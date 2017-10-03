<%@page session="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>


<!DOCTYPE html>
<html lang="en">


 <head>
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>PENSION FORM ::</title>
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
                 function GetStatusRacuna()
                {

                         $.getJSON('<spring:url value="/statusracuna.json"/>',
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
                                $('#status').html(html1);
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
          <tr>
            <td>
                <spring:url value="/penzijeKnjizenje/${penzije.rbr}/details" var="pioActionUrl" />
                       
                <br />
                 <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">DETALJI O KORISNIKU::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                 </table>
             
                <form:form  class="form-horizontal" method="post" modelAttribute="penzije" action="${pioActionUrl}" style="height:1300px">

                      <form:hidden path="rbr" />
                      <table style="width:100%">
                          <tr style="width:100%">
                              <td style="width:10%">&nbsp;</td>
                              <td style="width:20%">
                                    <spring:bind path="filijala">
                                            <div>
                                                <label class="btn-primary"> <b>FILIJALA:</b></label>
                                                    <div>
                                                            <form:input path="filijala" type="text" class="form-control " id="filijala" placeholder="Filijala" style="width:95%" />
                                                    </div>
                                            </div>
                                    </spring:bind>
                              </td>
                              <td style="width:15%">&nbsp</td>
                              <td style="width:20%">
                                    <spring:bind path="partija">
                                            <div>
                                                    <label class="btn-primary"><b>PARTIJA:</b></label>
                                                    <div style="width:100%">
                                                            <form:input path="partija" class="form-control" id="partija" placeholder="PARTIJA" style="width:95%" />
                                                    </div>
                                            </div>
                                    </spring:bind>
                                </td>  
                                <td style="width:30%">&nbsp</td>
                        </tr>
                        <br />
                        <tr>
                           <td style="width:10%">&nbsp;</td>
                            <td style="width:20%">
                                    <spring:bind path="ime">
                                           <div>
                                                   <label class="btn-primary"><b>IME</b></label>
                                                   <div style="width:100%">
                                                           <form:input path="ime" class="form-control" id="ime" placeholder="IME" style="width:95%" />
                                                   </div>
                                           </div>
                                   </spring:bind>
                            </td>
                        <td style="width:15%">&nbsp</td>
                        <td style="width:20%">
                                    <spring:bind path="jmbg">
                                            <div>
                                                    <label class="btn-primary"><b>JMBG:</b></label>
                                                    <div>
                                                   <div style="width:100%">
                                                            <form:input path="jmbg" class="form-control" id="jmbg" placeholder="JMBG" style="width:95%" />
                                                   </div>
                                            </div>
                                    </spring:bind>  
                        </td>
                        <td style="width:30%">&nbsp</td>
                        </tr>
                        <br/>
                        <tr>
                        <td style="width:10%">&nbsp;</td>   
                        <td style="width:20%">
                                <spring:bind path="iznos_rate">
                                        <div>
                                                <label class="btn-primary"><b>IZNOS:</b></label>
                                                <div>
                                               <div >
                                                        <form:input path="iznos_rate" class="form-control" id="iznos_rate" placeholder="IZNOS" style="width:95%" />
                                               </div>
                                        </div>
                                </spring:bind>   
                        </td>
                        <td style="width:15%">&nbsp;</td>
                        <td style="width:20%">    
                                <spring:bind path="status_racuna">
                                <div>
                                    <label class="btn-primary"><b>STATUS RACUNA</b></label>
                                                <div>
                                                    <form:select  class="form-control" id="status" path="status_racuna" style="width:99%">
                                                     <form:options items="${statusiracuna}" itemValue="value" itemLabel="label"></form:options>
                                                    </form:select>
                                                </div>
                                </div>
                        </spring:bind>  
                        </td>
                        <td style="width:30%">&nbsp</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td style="width:10%">&nbsp;</td>
                           <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td style="width:50px">&nbsp;</td>
                            <td style="width:20%">
                                 <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">

                                     <button type="submit" class="btn-lg btn-primary pull-left" style="width:150px;vertical-align: bottom">Izmeni Podatke </button>

                                     </div>
                            </td>
                            <td style="width:10%">&nbsp;</td>
                            
                        </tr>
                        </table>
                        <br></br>
                        <div style="vertical-align: bottom">
                         <button class="btn-primary" style="width:100%;text-align:left;height:5px"></button> 
                         <p><b> &copy; PIO APPLICATION</b></p>  
                        </div>
                </form:form>
            </td>
        </tr>
    </table>
</body>
<div style="vertical-align: bottom">
    <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
</div>
</html>