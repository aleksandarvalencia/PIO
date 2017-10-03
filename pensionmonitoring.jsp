<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="util" tagdir="/WEB-INF/tags/util" %>

<!DOCTYPE html>
<html lang="en">

<head>
        <script type="text/javascript" src="/PIO/resources/spin.js"></script>
        <script type="text/javascript" src="/PIO/resources/spin.min.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>MONITORING PIO ::</title>
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
                
                     <% 
                                    
                                    HttpSession session1=request.getSession();
                                    Integer mon_filijala=0;

                                    if (session1.getAttribute("filijalamon")!=null)
                                    {
                                        mon_filijala= (Integer)session1.getAttribute("filijalamon");
                                    }
                                    
                                   
                       %>

                           if (<%=mon_filijala %>!==null)
                           {
                                document.getElementById("filijale").value=<%=mon_filijala %>;
                           }
                          
                           $(window).resize(function () 
                                   {
                                       var h = $(window).height();
                                       //var h = Math.max($(window).height(), 450);
                                       $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                               }).resize();
                               
                //document.getElementById("bookDate").value=localStorage.getItem("DatumMonitoring");
                //document.getElementById("filijale").value=localStorage.getItem("StavkeMonitoring");
                   var opts = {
                                    lines: 13 // The number of lines to draw
                                  , length: 34 // The length of each line
                                  , width: 21 // The line thickness
                                  , radius: 44 // The radius of the inner circle
                                  , scale: 1.25 // Scales overall size of the spinner
                                  , corners: 1 // Corner roundness (0..1)
                                  , color: '#000' // #rgb or #rrggbb or array of colors
                                  , opacity: 0.5 // Opacity of the lines
                                  , rotate: 0 // The rotation offset
                                  , direction: 1 // 1: clockwise, -1: counterclockwise
                                  , speed: 1.3 // Rounds per second
                                  , trail: 60 // Afterglow percentage
                                  , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
                                  , zIndex: 2e9 // The z-index (defaults to 2000000000)
                                  , className: 'spinner' // The CSS class to assign to the spinner
                                  , top: '350px' // Top position relative to parent
                                  , left: '600px' // Left position relative to parent
                                  , shadow: true // Whether to render a shadow
                                  , hwaccel: false // Whether to use hardware acceleration
                                  , position: 'absolute' // Element positioning
                                  };
                                  var target = document.getElementById('loading');
                                  var spinner = new Spinner(opts).spin(target);
                                  document.getElementById("menu_items").style.width = "0";
                                  document.getElementById("menu_items").style.visibility = "hidden";
                                  document.getElementById("menu_id").value="0";
                                  HideWaitingProcess();
                          
                        
            } ;
            function LogOut()
            {
                      
                      localStorage.clear();
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
                         localStorage.clear();
                         var form = document.createElement("form");
                         var method ="post"; 
                         var aa="/PIO/closeEvent";
                         form.setAttribute("method", method);
                         form.setAttribute("action", aa);
                         document.body.appendChild(form);
                         form.submit();
                   }
            } ;
            function SakriDiv()
            {
               document.getElementById('Poruka').style.visibility='hidden';

             }
             function HideWaitingProcess()
             {
                document.getElementById('WaitingProcess').style.visibility='hidden';
                document.getElementById('loading').style.visibility='hidden';

             }
             function ShowWaitingProcess()
             {
                document.getElementById('WaitingProcess').style.visibility='visible';
                document.getElementById('loading').style.visibility='visible';

             }
            function GetHeader()
            {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var datum=document.getElementById("bookDate").value;
                      var tip=0;
                      //var opis = $('#filijale option:selected').text(); 
                      tip= $( "#filijale" ).val();
                      //localStorage.setItem("DatumMonitoring", datum);
                      //localStorage.setItem("StavkeMonitoring", tip);
                      var aa="/PIO/pmonitoring/" + datum + "/" + tip;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
            }
            function GetFullInformation(idk)
            {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var datum=document.getElementById("bookDate").value;
                      var tip=0;
                      //var opis = $('#filijale option:selected').text(); 
                      tip= $( "#filijale" ).val();
                      //localStorage.setItem("DatumMonitoring", datum);
                      //localStorage.setItem("StavkeMonitoring", tip);
                      var aa="/PIO/pmonitoring/" + datum + "/" + tip + "/" + idk;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
            }
           
                 
        </script>
 </head>
 
<body id="body" style="background-color:azure">
    
    <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
    </div>
    
 <input type="hidden" id="menu_id" name="menu_id" value="1"/>
 
<div id="WaitingProcess"  class="transparent" style="border:#000000 solid 1px;position: absolute; top: 350px; left: 400px; width: 500px; height:140px; background-color: white; color:darkslategray;filter:alpha(opacity=80);opacity:0.8;">
                                        <table style="width:500px; height:140px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;">
                                                <tr style="height:15px">
                                                        <td style="height:15px;width:495px">
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                    <img src="/PIO/include/img/pescani_sat.gif" id="loading" alt="Waiting..." style="position: absolute; top: 15%; left: 40%;filter:alpha(opacity=80);opacity:0.8;">
                                                    <td>
                                                </tr>
                                                <tr style="height:10px;width:495px">
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:10pt;font-family: Arial,Tahoma;height:10px;">
                                                                        OPERACIJA U TOKU ! SACEKAJTE DOK SE PROCES NE ZAVRSI!
                                                                </td>
                                                </tr>
                                        </table>
</div>
 <c:if test="${informacija!='' && informacija!='monitorning_full' && informacija!='header_only'}">
        <div id="Poruka"  class="transparent" style="position: absolute; top: 350px; left: 400px; width: 450px; height:100px; background-color: white; color:darkslategray">
                                        <table style="border:#000000 solid 1px;width:450px; height:100px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;">
                                                <tr>
                                                        <td>
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>

                                                <tr>
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:14pt;font-family: Arial,Tahoma;">
                                                                        ${informacija} !
                                                                </td>
                                                </tr>
                                                <tr>
                                                    <td> <a href="#"><img border='0' src="/PIO/include/img/OK.png" alt='Zatvori Prozor' onClick="SakriDiv();"/></a></td>
					</tr>
                                        </table>
        </div>
</c:if>

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
                                    <td id="body" style="background-color:azure;vertical-align:top;">
                                        <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">MONITORING STAVKI ZA KNJIZENJE::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                                       </table>
                                        <br/>
                                        <table>
                                        <tr>
                                            <td style="width:50px">&nbsp;</td> 
                                            <td style="width:220px;color:whitesmoke;"><label>DATUM FORMIRANJA STAVKI::</label> </td>
                                            <td>
                                              
                                                    <div id="importedfilesdate" >

                                                            <div class="input-append date" >
                                                                <input type="text" id="bookDate"  name="bookDate" value="${monitoring_date}" style="width:100px;height:20px"/>
                                                            <span class="add-on">
                                                                      <i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
                                                             </span>
                                                            </div>
                                                     </div>
                                               
                                            </td>
                                            <td style="width:20px">&nbsp;</td>
                                            <td style="color:whitesmoke;font-size:16px;vertical-align:top">FILIJALA::</td>
                                            <td><select id="filijale" name="filijale" style="height:25px;background-color:lightgray;color:black" >
                                                            <c:forEach var="item" items="${pio_filijala}">
                                                              <option value="${item.value}">${item.label}</option>
                                                            </c:forEach>
                                                 </select>
                                            </td>
                                           
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
                                        <table style="width:100%;">
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td style="width:350px;color:whitesmoke;"><label>FORMIRANO STAVKI NA 0003191390000009::</label> </td>
                                            <td style="color:whitesmoke;font-size:15px;vertical-align:top">${iznosi_knjizenja.ukupan_iznos}</td>
                                            <td>&nbsp;</td>  
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td style="width:350px;color:whitesmoke;"><label>FORMIRANO NA 0003191390000020::</label> </td>
                                            <td style="color:whitesmoke;font-size:15px;vertical-align:top">${iznosi_knjizenja.iznos_filijala}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            
                                        </tr>
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td style="width:350px;color:whitesmoke;"><label>FORMIRANO NA RACUNE KLIJENATA::</label> </td>
                                            <td style="color:whitesmoke;font-size:15px;vertical-align:top">${iznosi_knjizenja.iznos_stavke}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td style="width:350px;color:whitesmoke;"><label>FORMIRANO UKUPNO::</label> </td>
                                            <td style="color:whitesmoke;font-size:15px;vertical-align:top">${iznosi_knjizenja.sum_ukupno}</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            
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
                                        <table>
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td><button  style="height:25px;background-color: maroon;color: whitesmoke" onclick="window.onbeforeunload = null;GetHeader();">ZBIRNA KNJIZENJA</button></td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
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
                                       <table style="width:100%;">
                                           <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                    <table style="width:100%;vertical-align:top;">
                                                                <thead>
                                                                        <tr>
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-left:2px solid gray;border-top:2px solid gray;">ID</th>
                                                                            <th style="background-color:maroon;width:270px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">NAZIV</th>
                                                                            <th style="background-color:maroon;width:100px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">DATUM OBRADE </th>
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">ACCOUNT</th>  
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IZNOS</th> 
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">TMS_ID</th> 
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">SIFRA</th>    
                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="monitoring" items="${monitoring_header}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid #002a80;">
                                                                                   <a style="cursor: pointer;" onclick="GetFullInformation('${monitoring.idk}')"> ${monitoring.pbo}</a>   
                                                                                </td>
                                                                                <td style="background-color:white;width:270px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.ime}</td>
                                                                                <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.datum}</td>                                                                     
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.racun}</td>                                                                         
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.iznos}</td>             
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.ID}</td>   
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${monitoring.sifra}</td>   
                                                                        </c:forEach>
                                                                        </tr>

                                                    </table>
                                                </td>
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
 <c:if test="${informacija=='monitorning_full'}">
                                        <table style="width:100%;">
                                           <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                    <table style="width:100%;vertical-align:top;">
                                                                <thead>
                                                                        <tr>
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-left:2px solid gray;border-top:2px solid gray;">ID</th>
                                                                            <th style="background-color:maroon;width:270px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">NAZIV</th>
                                                                            <th style="background-color:maroon;width:100px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">DATUM VALUTE</th>
                                                                            <th style="background-color:maroon;width:100px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">DATUM OBRADE </th>
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">DEBIT</th>  
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">CREDIT</th> 
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IZNOS</th> 
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">TMS_ID</th>      
                                                                            <th style="background-color:maroon;width:120px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">STATUS</th>                                                                  
                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="stavke" items="${stavke}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid #002a80;">${stavke.idk}</td>
                                                                                <td style="background-color:white;width:270px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.ime}</td>
                                                                                <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.datum_valute}</td>
                                                                                <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.datum_obrade}</td>                                                                     
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.debit_account}</td>     
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.credit_account}</td>                                                                        
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.iznos}</td>             
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.tms_id}</td>   
                                                                              <c:if test="${stavke.status=='NBY:Settled'}">  
                                                                                <td style="background-color:blue;color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if>  
                                                                              <c:if test="${stavke.status=='TMS:Accepted'}">  
                                                                                <td style="background-color:yellow;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if>  
                                                                              <c:if test="${stavke.status=='TMS:Verified'}">  
                                                                                <td style="background-color:lightskyblue;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if> 
                                                                              <c:if test="${stavke.status=='MIE:Rejected'}">  
                                                                                <td style="background-color:maroon;color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if> 
                                                                              <c:if test="${stavke.status=='MIE:Reload'}">  
                                                                                <td style="background-color:maroon;color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if> 
                                                                              <c:if test="${stavke.status=='MIE:Accepted'}">  
                                                                                <td style="background-color:lemonchiffon;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if>  
                                                                              <c:if test="${stavke.status!='MIE:Accepted' && stavke.status!='MIE:Rejected'&& stavke.status!='TMS:Verified' && stavke.status!='TMS:Accepted' && stavke.status!='NBY:Settled' && stavke.status!='MIE:Reload'}">  
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${stavke.status}</td>    
                                                                              </c:if> 
                                                                        </tr>
                                                                </c:forEach>

                                                        </table>
                                                </td>
                                           </tr>
                                       </table>
</c:if>  
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