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
                                  document.getElementById('WaitingProcess').style.visibility='hidden';
                                  document.getElementById('loading').style.visibility='hidden';

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
            function GetPayment()
            {
                var form = document.createElement("form");
                var method ="post"; 
                var datum=document.getElementById("paymentDate").value;
                if (datum===null || datum==="" || datum===" ")
                {
                    
                    alert("NISTE UNELI DATUM!!!");
                }
                else
                {
                    localStorage.setItem("PaymentDate", datum);
                    var aa="/PIO/getPPayment/" + datum;
                    form.setAttribute("method", method);
                    form.setAttribute("action", aa);
                    document.body.appendChild(form);
                    form.submit();
                }
                //ShowWaitingProcess();
            }
            function ImportRacuna()
            {
                var form = document.createElement("form");
                var method ="post"; 
                var aa="/PIO/ImportRacuna";
                form.setAttribute("method", method);
                form.setAttribute("action", aa);
                document.body.appendChild(form);
                form.submit();
                ShowWaitingProcess();
            }
            function SetPaymentArray()
            {
                var cntchk=document.getElementById("p_chkcnt").value;
                assignFieldsValueForSplitPaymentArray(cntchk);
                var form = document.createElement("form");
                var method ="post"; 
                var lista =document.getElementById("p_chkarraylist").value;
                if (lista===null || lista==="" || lista===" ")
                {
                    
                    alert("NIJEDNU UPLATU NISTE IZABRALI IZ LISTE!!!");
                }
                else
                {
                    var aa="/PIO/getPPaymentTable/" + lista;
                    form.setAttribute("method", method);
                    form.setAttribute("action", aa);
                    document.body.appendChild(form);
                    form.submit();
                }
            }
            function assignFieldsValueForSplitPaymentArray(cnt)
            {
		var SplitChkArray = "";
                
                
                
		for (var i = 1; i <= cnt; i++)
			{
                            if (document.getElementById("chkarray_" + i).checked)
                            {
                                SplitChkArray = SplitChkArray + document.getElementById("chkarray_" + i).value + ",";
                            }
			}		
			document.getElementById("p_chkarraylist").value=SplitChkArray;
            }        
            function SakriDiv()
            {
               document.getElementById('Poruka').style.visibility='hidden';

             }
	
            
        </script>
 </head>
 
<body id="body" style="background-color:azure">
    
    <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
    </div>
    
    
    <input type="hidden" id="menu_id" name="menu_id" value="1"/>
    <input type="hidden" name="p_chkarraylist" id="p_chkarraylist"/>
    <input type="hidden" name="p_chkcnt" id="p_chkcnt" value="${chkcount}"/>
    
    <div id="WaitingProcess"  class="transparent" style="border:#000000 solid 1px;position: absolute; top: 40%; left: 35%; width: 500px; height:140px; background-color: white; color:darkslategray;filter:alpha(opacity=80);opacity:0.8;">
                                          <table style="width:500px; height:145px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;">
                                                  <tr style="height:12px">
                                                          <td style="height:12px;width:495px">
                                                                  SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                          </td>
                                                  </tr>
                                                  <tr>
                                                      <td>
                                                      <img src="/PIO/include/img/pescani_sat.gif" id="loading" alt="Waiting..." style="position: absolute; top: 20%; left: 40%;filter:alpha(opacity=80);opacity:0.8;">
                                                      <td>
                                                  </tr>
                                                  <tr style="height:12px;width:495px">
                                                                  <td style="color:Brown;text-align:center;font-weight:bold;font-size:10pt;font-family: Arial,Tahoma;height:12px;">
                                                                          OPERACIJA U TOKU ! SACEKAJTE DOK SE PROCES NE ZAVRSI!
                                                                  </td>
                                                  </tr>
                                          </table>
  </div>
    <c:if test="${informacija!='0'}">
        <div id="Poruka"  class="transparent" style="position: absolute; top: 40%; left: 35%; width: 450px; height:100px; background-color: white; color:darkslategray">
                                        <table style="border:#000000 solid 1px;width:450px; height:100px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;;filter:alpha(opacity=80);opacity:0.8;">
                                                <tr>
                                                        <td>
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>

                                                <tr>
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:14pt;font-family: Arial,Tahoma;">
                                                                        ${informacija} !!!
                                                                </td>
                                                </tr>
                                                <tr>
                                                    <td> <a href="#"><img border='0' src="/PIO/include/img/OK.png" alt='Zatvori Prozor' onClick="SakriDiv();"/></a></td>
					</tr>
                                        </table>
        </div>
   </c:if>
    
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
                                                 PIO APLIKACIJA ::
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
                                            <td style="width:20px">&nbsp;</td>
                                            <td style="width:150px"><button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;SetPaymentArray();">PROSLEDI UPLATE</button></td>                                            
                                            <td style="width:20px">&nbsp;</td>
                                            <td style="width:150px"><button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;ImportRacuna();">IMPORT RACUNA</button></td>                                           
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
                                                                            <th style="background-color:maroon;width:170px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">IME</th> 
                                                                            <th style="background-color:maroon;width:160px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">PBO</th> 
                                                                            <th style="background-color:maroon;width:80px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">SIFRA</th> 
                                                                            <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">OPIS</th>                                                              
                                                                            <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:whitesmoke;text-align:center;border-bottom:2px solid gray;border-right:2px solid gray;border-top:2px solid gray;">AKCIJA</th>                                                                        
                                                                        </tr>
                                                                </thead>

                                                                <c:forEach var="piouplate" items="${uplate}">
                                                                        <tr>
                                                                                <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;border-left:1px solid gray;">${piouplate.ID}</td>
                                                                                <td style="background-color:white;width:150px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.racun}</td>
                                                                                <td style="background-color:white;width:120px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.datum}</td>
                                                                                <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.iznos}</td>                                                                     
                                                                                <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.kontraracun}</td>     
                                                                                <td style="background-color:white;width:170px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.ime}</td>                                                                        
                                                                                <td style="background-color:white;width:160px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.pbo}</td>
                                                                                <td style="background-color:white;width:80px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.sifra}</td>  
                                                                                <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;">${piouplate.opis}</td> 
                                                                                <td style="background-color:white;width:50px;font-size: 9px;text-align:center;border-bottom:1px solid gray;border-right:1px solid gray;"><form:checkbox path="uplate" value="${piouplate.ID}" id="chkarray_${piouplate.position}"  /></td>

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