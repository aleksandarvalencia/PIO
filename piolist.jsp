<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE html>
<html lang="en">

<head>
        <script type="text/javascript" src="/PIO/resources/spin.js"></script>
        <script type="text/javascript" src="/PIO/resources/spin.min.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
<!--    <link type="text/css" rel="stylesheet" media="screen" href="/PIO/resources/css/bootstrap-datetimepicker.min.css"> 
        <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.pt-BR.js"></script>-->
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>PIO LIST ::</title>
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
                            $(window).resize(function () 
                            {
                                    var h = $(window).height();
                                    //var h = Math.max($(window).height(), 450);
                                    $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                            }).resize();
                            
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
               function PIOBeograd()
                   {
                      var form = document.createElement("form");
                      var fil=document.getElementById("tipfajla").value;
                      var method ="post"; 
                      var aa="/PIO/pioImport/1/" + fil;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
                   }
                  function PIONoviSad()
                   {
                      var form = document.createElement("form");
                      var fil=document.getElementById("tipfajla").value;
                      var method ="post"; 
                      var aa="/PIO/pioImport/2/" + fil;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
                   }
                     function SakriDiv()
                    {
                     document.getElementById('Poruka').style.visibility='hidden';

                    }
                    window.onbeforeunload = function(event) 
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
                         } ;
            
        </script>
 </head>
 
<body id="body">
    
<div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
</div>
 <input type="hidden" id="menu_id" name="menu_id" value="1"/>
<div id="WaitingProcess"  class="transparent" style="border:#000000 solid 1px;position: absolute; top: 40%; left: 35%; width: 500px; height:140px; background-color: white; color:darkslategray;filter:alpha(opacity=80);opacity:0.8;">
                                        <table style="width:500px; height:140px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;">
                                                <tr style="height:15px">
                                                        <td style="height:15px;width:495px">
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                    <img src="/PIO/include/img/pescani_sat.gif" id="loading" alt="Waiting..." style="position: absolute; top: 20%; left: 40%;filter:alpha(opacity=80);opacity:0.8;">
                                                    <td>
                                                </tr>
                                                <tr style="height:10px;width:495px">
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:10pt;font-family: Arial,Tahoma;height:10px;">
                                                                        OPERACIJA U TOKU ! SACEKAJTE DOK SE PROCES NE ZAVRSI!
                                                                </td>
                                                </tr>
                                        </table>
</div>
  <c:if test="${informacija=='1'}">
        <div id="Poruka"  class="transparent" style="position: absolute; top: 40%; left: 35%; width: 450px; height:100px; background-color: white; color:darkslategray">
                                        <table style="border:#000000 solid 1px;width:450px; height:100px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;">
                                                <tr>
                                                        <td>
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>

                                                <tr>
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:14pt;font-family: Arial,Tahoma;">
                                                                        FAJL USPESNO IMPORTOVAN !
                                                                </td>
                                                </tr>
                                                <tr>
						<td> <a href="#"><img border='0' src="/PIO/include/img/OK.png" alt='Zatvori Prozor' onClick="SakriDiv();"/></a></td>
					</tr>
                                        </table>
        </div>
</c:if>
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
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">PIO IMPORT::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                                          </tr>
                                       </table>
                                        <br/>
                                        
                                        <table style="width:100%;">
                                                 <tr>
                                                     <td style="width:0.3%">&nbsp;</td> 
                                                     <td style="width:8%;color:whitesmoke;font-size:16px;font-weight:bold;vertical-align:top;"> TIP FAJLA:  
                                                         <select id="tipfajla" name="tipfajla" style="height:25px;background-color:lightgray;color:black;width:15%" >
                                                                    <c:forEach var="item" items="${pio_tip}">
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
                                                     <td style="width:1.5%">&nbsp;</td>
                                                     <td style="width:7%;color:whitesmoke;font-size:16px;font-weight:bold;vertical-align:top;">IZABERI PIO FOND :</td>
                                                     <td style="width:6%"><button class="btn-primary" style="height:25px;" onclick="window.onbeforeunload = null;PIOBeograd();">PIO BEOGRAD</button></td>
                                                     <td style="width:6%"><button class="btn-primary" style="height:25px" onclick="window.onbeforeunload = null;PIONoviSad();">PIO VOJVODINA</button></td>
                                                     <td style="width:10%;color:whitesmoke;font-size:16px;font-weight:bold;vertical-align:top;">UKUPAN IZNOS:</td>
                                                     <td style="width:10%"><label id="ukupan_iznos"  style="color:whitesmoke;font-size:16px;font-weight:bold;vertical-align:bottom;"><b>${ukupaniznos}</b></label></td>
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
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-left:2px solid gray;border-top:2px solid gray">FILIJALA</th>
                                                                            <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">ID PENZIJE</th>
                                                                            <th style="background-color:maroon;width:180px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">SIFRA OBUSTAVE</th>
                                                                            <th style="background-color:maroon;width:130px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">JMBG </th>
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">PRAVO</th>    
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IZNOS RATE</th>  
                                                                            <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">PARTIJA</th> 
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IZNOS DUGA</th> 
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">OSTATAK DUGA</th> 
                                                                            <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">OSTALO</th>                                                                 
            <!--                                                                <th style="background-color:maroon;width:60px;height:15px;font-size:12px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid #002a80;border-right:2px solid #002a80;border-top:2px solid #002a80;">Action</th>-->
                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="pio" items="${pioDetails}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid gray;">${pio.filijala}</td>
                                                                                <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.id_penzije}</td>
                                                                                <td style="background-color:white;width:180px;font-size: 9px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.sif_obustave}</td>
                                                                                <td style="background-color:white;width:130px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.jmbg}</td>  
                                                                                <td style="background-color:white;width:50px;font-size: 9px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.vrsta_prava}</td>                                                                        
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.iznos_rate}</td>     
                                                                                <td style="background-color:white;width:200px;font-size: 9px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.partija}</td>                                                                        
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.iznos_duga}</td>             
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:left;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.ostatak_duga}</td>                                                                        
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${pio.ostalo}</td>                                                                          
            <!--                                                                    <td style="background-color:white;width:60px;font-size: 10px;text-align:center;border-bottom:1px solid #002a80;border-right:1px solid #002a80;">
                                                                                        <spring:url value="/pio/${fil.id_penzije}/details" var="userUrl" />
                                                                                        <button class="btn-primary" style="height:25px" onclick="window.onbeforeunload = null;location.href='${userUrl}'">Details</button>
                                                                                </td>-->
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
                                      
	<!--</div>-->


<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
</body>
</html>