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
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link  type="text/css" href="/PIO/resources/jquery-ui-1.11.4/jquery-ui.css" rel="stylesheet">
         <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>KNJIZENJE PENZIJA ::</title>
          <style>
            #body {
                background: url(/PIO/include/img/slika1.jpg) no-repeat center center fixed; /* For IE 6 and 7 */
                -webkit-background-size: cover;
                -moz-background-size: cover;
                -o-background-size: cover;
                background-size: cover;
            }
            #transparent
            {
               filter:alpha(opacity=80); 
               opacity: 0.8; 
            }
        </style>
        <script type="text/javascript">
            

                    window.onload = function() 
                        {
                                
                                $(window).resize(function () 
                                   {
                                       var h = $(window).height();
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
                                  
                                <% 
                                    String sel="";
                                    //String rad2="";  
                                    //String rad3="";
                                    HttpSession session1=request.getSession();

                                    Integer chkBoxvalue= (Integer)session1.getAttribute("zajednickipriliv");
                                    Integer filijalavalue= (Integer)session1.getAttribute("filijalavalue");
                                    
                                    if (chkBoxvalue!=null)
                                    {
                                        if(chkBoxvalue==1)
                                        {
                                            sel="checked";
                                            
                                        }
                                       
                                    }

                                %>
                                        
                                        document.getElementById('WaitingProcess').style.visibility='hidden';
                                        document.getElementById('loading').style.visibility='hidden';
                                        
                                        if (<%=filijalavalue %>!==null)
                                        {
                                            document.getElementById("filijale").value=<%=filijalavalue %>;
                                        }
                           
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
                   function PIOImport()
                   {
                      var form = document.createElement("form");
                      var tip=document.getElementById("filijale").value;
                      var chkb=0;
                      if (document.getElementById("chkBox").checked)
                      {
                          chkb=1;
                      }
                      
                      /*if (document.getElementById("piovojvodina").checked)
                      {
                          tip=2;
                      }
                      if (document.getElementById("piovojni").checked)
                      {
                          tip=3;
                      }*/
                      var method ="post"; 
                      if (tip===null || tip==="" || tip===" " || tip==="0")
                      {

                        alert("NISTE IZABRALI NIJEDNU FILIJALU ZA IMPORT!!!");
                      }
                      else
                      {
                        var aa="/PIO/penzijeKnjizenje/" + tip + "/" + chkb;
                        form.setAttribute("method", method);
                        form.setAttribute("action", aa);
                        document.body.appendChild(form);
                        form.submit();
                        ShowWaitingProcess();
                      }
                   }
            
                    function PIOBeograd()
                   {
                      var form = document.createElement("form");
                      var fil=document.getElementById("filijale").value;
                      var tip=1;
                      if (document.getElementById("piobeograd").checked)
                      {
                          tip=1;
                      }
                      if (document.getElementById("piovojvodina").checked)
                      {
                          tip=2;
                      }
                      if (document.getElementById("piovojni").checked)
                      {
                          tip=3;
                      }
                      var method ="post"; 
                      var duz=fil.length;
                      if (duz===1)
                      {
                         fil="0" + fil;
                      }
                      var aa="/PIO/penzijeKnjizenje/" + fil + "/" + tip;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                       
                   }
                    function PIONoviSad()
                   {
                      var form = document.createElement("form");
                      var fil=document.getElementById("filijale").value;
   
                      var method ="post"; 
                      var duz=fil.length;
                      if (duz===1)
                      {
                         fil="0" + fil;
                      }
                      var aa="/PIO/penzijeKnjizenje/" + fil + "/2";
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                       
                   }
                   function CekirajRacune()
                   {

                      var form = document.createElement("form");
                      var method ="post"; 
                      var tip=document.getElementById("filijale").value;
                      if (tip===null || tip==="" || tip===" " || tip==="0")
                      {

                        alert("NISTE IZABRALI NIJEDNU FILIJALU ZA PROVERU RACUNA!!!");
                      }
                      else
                      {
                        var aa="/PIO/paccountCheck";
                        form.setAttribute("method", method);
                        form.setAttribute("action", aa);
                        document.body.appendChild(form);
                        form.submit();
                        ShowWaitingProcess();
                      }
                   }
                     function GetHeaderInformation()
                   {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var iznos=document.getElementById("iznospriliva").value;
                      var datum=document.getElementById("fileDate").value;
                      var aa="/PIO/headerPInformation/" + datum + "/" + iznos;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
                   }
                   
                    function GetFilijalaDetails()
                    {
                
                      var form = document.createElement("form");
                      var fil=document.getElementById("filijale").value;

                      var method ="post"; 
                      var duz=fil.length;
                      var tip=0;
                      if (duz===1)
                      {
                         fil="0" + fil;
                      }
                      
                      if (document.getElementById("piobeograd").checked)
                      {
                          tip=1;
                      }
                      if (document.getElementById("piovojni").checked)
                      {
                          tip=2;
                      }
  
                      var aa="/PIO/filijalaDetails/" + fil + "/" + tip;
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();
                      ShowWaitingProcess();
                    }
                   
                    function KlikBeograd()
                    {

                         $('#piobeograd').prop('checked', 'checked');
                         $('#piovojni').prop('checked', false);
                         $('#piovojvodina').prop('checked', false);
                    }
                    function KlikVojvodina()
                    {

                         $('#piovojvodina').prop('checked', 'checked');
                         $('#piobeograd').prop('checked', false);
                         $('#piovojni').prop('checked', false);
                       
                    }
                    function KlikVojska()
                    {

                         $('#piovojni').prop('checked', 'checked');
                         $('#piobeograd').prop('checked', false);
                         $('#piovojvodina').prop('checked', false);
                       
                    }
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
                    function SendToBooking()
                    {

                              var form = document.createElement("form");
                              var id_k=document.getElementById("idk").value;
                              var tip=document.getElementById("filijale").value;
                              var method ="post";       
                              var aa="/PIO/sendPBooking/" + id_k + "/" + tip;
                              form.setAttribute("method", method);
                              form.setAttribute("action", aa);
                              document.body.appendChild(form);
                              form.submit();
                              ShowWaitingProcess();

                    }
                    function IzmeniSve()
                    {

                              var form = document.createElement("form");
                              var method ="post"; 

                              var aa="/PIO/changePAll/";
                              form.setAttribute("method", method);
                              form.setAttribute("action", aa);
                              document.body.appendChild(form);
                              form.submit();
                              ShowWaitingProcess();

                    }
                     function PIOExport()
                   {
                      var form = document.createElement("form");
                      var tip=document.getElementById("filijale").value;
                      var method ="post"; 
                      if (tip===null || tip==="" || tip===" " || tip==="0")
                      {

                        alert("NISTE IZABRALI NIJEDNU FILIJALU ZA EXPORT U FAJL!!!");
                      }
                      else
                      {
                        var aa="/PIO/penzijeExport/" + tip ;
                        form.setAttribute("method", method);
                        form.setAttribute("action", aa);
                        document.body.appendChild(form);
                        form.submit();
                      }
                       
                   }

        </script>
 </head>
 
<body id="body" style="background-color:azure">
    
  <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
  </div>
  
  <input type="hidden" id="menu_id" name="menu_id" value="1"/> 
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
 
 
 
 <c:if test="${informacija=='1'}">
        <div id="Poruka"  class="transparent" style="position: absolute; top: 40%; left: 35%; width: 450px; height:100px; background-color: white; color:darkslategray">
                                        <table style="border:#000000 solid 1px;width:450px; height:100px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;;filter:alpha(opacity=80);opacity:0.8;">
                                                <tr>
                                                        <td>
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>

                                                <tr>
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:14pt;font-family: Arial,Tahoma;">
                                                                        FAJL ZA KNJIZENJE USPESNO NAPRAVLJEN !
                                                                </td>
                                                </tr>
                                                <tr>
                                                    <td> <a href="#"><img border='0' src="/PIO/include/img/OK.png" alt='Zatvori Prozor' onClick="SakriDiv();"/></a></td>
					</tr>
                                        </table>
        </div>
</c:if>
<c:if test="${informacija=='3'}">
        <div id="Poruka"  class="transparent" style="position: absolute; top: 40%; left: 35%; width: 450px; height:100px; background-color: white; color:darkslategray">
                                        <table style="border:#000000 solid 1px;width:450px; height:100px;color:DarkSlateGray;text-align:center;font-weight:bold;font-size: 10pt;font-family: Arial,Tahoma;;filter:alpha(opacity=80);opacity:0.8;">
                                                <tr>
                                                        <td>
                                                                SOCIETE GENERALE BANKA - PORUKA O ZAVRSENOM PROCESU  
                                                        </td>
                                                </tr>

                                                <tr>
                                                                <td style="color:Brown;text-align:center;font-weight:bold;font-size:14pt;font-family: Arial,Tahoma;">
                                                                        STAVKE ZA KNJIZENJE USPESNO NAPRAVLJENE !
                                                                </td>
                                                </tr>
                                                <tr>
                                                    <td> <a href="#"><img border='0' src="/PIO/include/img/OK.png" alt='Zatvori Prozor' onClick="SakriDiv();"/></a></td>
					</tr>
                                        </table>
        </div>
</c:if>
<c:if test="${informacija!='3' && informacija!='1' && informacija!='' && informacija!='0'}">
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


                            <table id="body" style="width:100%" >
                                 <tr style="width:100%;border-bottom:2px solid lightgrey ">
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
                                                   <h1 style="color:whitesmoke;font-size:20px;text-align:left;">KNJIZENJE PENZIJA ::</h1>                                        
                                                   <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                               </td>
                                             </tr>
                                        </table>

                                        <table style="width:100%">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                   <h1 style="color:lightgrey;font-size:16px;">PRILIVI ::</h1>                                        
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
                                                                        <th style="background-color:maroon;width:100px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-left:2px solid lightgrey;border-top:2px solid lightgrey;">DATUM PRILIVA</th>
                                                                        <th style="background-color:maroon;width:130px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IZNOS PRILIVA </th>
                                                                        <th style="background-color:maroon;width:210px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">UPLATILAC</th>  
                                                                        <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">RACUN</th> 
                                                                        <th style="background-color:maroon;width:210px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">SGYB RACUN</th>                                                                 
                                                                        <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">PBO</th>  
                                                                        <th style="background-color:maroon;width:160px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">SIFRA</th>                                                                         
                                                                    </tr>
                                                            </thead>

                                                            <c:forEach var="pension" items="${pension_book}">
                                                                    <tr>
                                                                            <td style="background-color:white;width:100px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;border-left:1px solid lightgrey;">${pension.datum}</td>
                                                                            <td style="background-color:white;width:130px;font-size: 9px;text-align:right;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.iznos}</td>                                                                     
                                                                            <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.ime}</td>     
                                                                            <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.racun}</td>                                                                                                                                              
                                                                            <td style="background-color:white;width:210px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.kontraracun}</td> 
                                                                            <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.pbo}</td>                                                                                                                                              
                                                                            <td style="background-color:white;width:160px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pension.sifra}</td>                                                                               

                                                            </c:forEach>

                                                    </table>
                                                </td>
                                           </tr>
                                       </table>
                                        <br/>
                                        <table style="width:100%;">
                                            <tr>
                                                <td style="width:35px">&nbsp;</td>
                                                <td style="width:150px;color:whitesmoke"><label>UKUPAN IZNOS PRILIVA::</label></td>
                                                 <td style="width:815px;font-size:15px;vertical-align:top;color:whitesmoke">${ukupan_priliv}</td>
                                            </tr>
                                        </table>
                                               <input type="hidden" id="idk" name="idk" value="${idkg}"/> 
                                       <table style="width:100%;">
                                           <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>        
                                                    <table style="font-size:12px;border-bottom:2px solid lightgrey;border-right:2px solid lightgrey;border-left:2px solid lightgrey;border-top:2px solid lightgrey;width:100%">
                                                        <tr>
                                                             <td style="color:whitesmoke;background-color:maroon;vertical-align:middle;width:10%">IZABERI PIO FOND :</td>
                                                             <td style="width:2%"> &nbsp;</td>
                                                             <td style="width:25%;"> 
                                                                     <select id="filijale" name="filijale" style="height:25px;background-color:lightgray;color:black;width:300px;">
                                                                        <c:forEach var="item" items="${pio_filijale}">
                                                                          <option value="${item.value}">${item.label}</option>
                                                                        </c:forEach>
                                                                      </select>
                                                             </td>                         
                                                             <td style="width:10%;color:white;vertical-align:middle;font-size:14px;height:10px">UKUPAN IZNOS:</td>
                                                             <td style="width:10%;"><label id="ukupan_iznos"  style="color:lightgrey;vertical-align: middle;"><b>${ukupaniznos}</b></label></td>
                                                              <td style="width:10%;vertical-align:central;color:white;">ZAJEDNICKI PRILIV::</td>                                                            
                                                             <td style="width:2%;vertical-align:middle;"><input type="checkbox" name="chkBox" id="chkBox" <%=sel %>></td>

                                                             <td style="width:38%;"> &nbsp;</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                           </tr>
                                       </table>  
                                        <br/>

                                        <table style="width:100%;">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                    <table style="width:100%;font-size:12px;border-bottom:2px solid lightgrey;border-right:2px solid lightgrey;border-left:2px solid lightgrey;border-top:2px solid lightgrey;">
                                                            <tr>
                                                                <td style="width:10%"><button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;PIOImport();">IMPORT PENZIJA</button></td>
                                                                <td style="width:10.5%"> <button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;CekirajRacune();">PROVERI RACUNE</button></td>
                                                                <td style="width:12.5%"><button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;SendToBooking();">POSALJI NA KNJIZENJE</button></td>
                                                                <td style="width:10.5%"> <button  class="btn-primary" style="height:25px;color: whitesmoke" onclick="window.onbeforeunload = null;PIOExport();">EXPORT U FAJL</button></td>
                                                                <td style="width:56%"> &nbsp;</td>
                                                            </tr>
                                                    </table>
                                                </td>
                                           </tr>
                                       </table>  
                                        
                                        <br/>
  <c:if test="${informacija!='provera' && informacija!='EXPORT U FAJL USPESNO URADJEN'}">	
                                         <table style="width:100%">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                   <h1 style="color:lightgrey;font-size:16px;">UGASENI RACUNI ::</h1>                                        
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
                                                                        <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-left:2px solid lightgrey;border-top:2px solid lightgrey;">FILIJALA</th>
                                                                        <th style="background-color:maroon;width:130px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">JMBG </th>
                                                                        <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IZNOS UPLATE</th>  
                                                                        <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">PARTIJA</th> 
                                                                        <th style="background-color:maroon;width:260px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IME</th>                                                                 
                                                                       
                                                                    </tr>
                                                            </thead>

                                                            <c:forEach var="pio" items="${ugaseni}">
                                                                    <tr>
                                                                            <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;border-left:1px solid lightgrey;">${pio.filijala}</td>
                                                                            <td style="background-color:white;width:130px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.jmbg}</td>                                                                     
                                                                            <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.iznos_rate}</td>     
                                                                            <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.partija}</td>                                                                                                                                              
                                                                            <td style="background-color:white;width:260px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.ime}</td>                                                                          

                                                            </c:forEach>

                                                    </table>
                                                </td>
                                           </tr>
                                       </table>
      
                                        <br/>
                                       <table style="width:100%">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                   <h1 style="color:lightgrey;font-size:16px;">RACUNI ZA KNJIZENJE::</h1>                                        
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
                                                                        <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-left:2px solid lightgrey;border-top:2px solid lightgrey;">FILIJALA</th>
                                                                        <th style="background-color:maroon;width:130px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">JMBG </th>
                                                                        <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IZNOS UPLATE</th>  
                                                                        <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">PARTIJA</th> 
                                                                        <th style="background-color:maroon;width:260px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IME</th>                                                                 
                                                                       
                                                                    </tr>
                                                            </thead>

                                                            <c:forEach var="pio" items="${penzije}">
                                                                    <tr>
                                                                            <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;border-left:1px solid lightgrey;">${pio.filijala}</td>
                                                                            <td style="background-color:white;width:130px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.jmbg}</td>                                                                     
                                                                            <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.iznos_rate}</td>     
                                                                            <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.partija}</td>                                                                                                                                              
                                                                            <td style="background-color:white;width:260px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${pio.ime}</td>                                                                          
      
                                                                    </tr>
                                                            </c:forEach>

                                                    </table>
                                                </td>
                                           </tr>
                                       </table>
  </c:if>
                                      
                                        
  <c:if test="${informacija=='provera'}">	
                                     <table style="width:100%">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>
                                                   <h1 style="color:lightgrey;font-size:16px;">GRESKE U RACUNIMA ::</h1>                                        
                                               </td>
                                             </tr>
                                        </table>
                                    <table>
                                        <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td><button  style="height:25px;background-color: maroon;color: whitesmoke" onclick="window.onbeforeunload = null;IzmeniSve();">IZMENI SVE</button></td>
                                        </tr>
                                    </table>
                                   <table style="width:100%;">
                                            <tr>
                                               <td style="width:50px">&nbsp;</td>
                                               <td>     
                                                    <table style="width:100%;vertical-align:top;">
                                                                    <thead>
                                                                            <tr>
                                                                                <th style="background-color:maroon;width:50px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-left:2px solid lightgrey;border-top:2px solid lightgrey;">RACUN</th>
                                                                                <th style="background-color:maroon;width:200px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IME</th>
                                                                                <th style="background-color:maroon;width:180px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">IZNOS</th>
                                                                                <th style="background-color:maroon;width:130px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">JMBG </th>
                                                                                <th style="background-color:maroon;width:110px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">STATUS</th>  
                                                                                <th style="background-color:maroon;width:150px;height:15px;font-size:10px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">STATUS RACUNA</th>  
                                                                                <th style="background-color:maroon;width:60px;height:15px;font-size:12px;font-family:serif;color:lightgrey;text-align:center;border-bottom:2px solid lightgrey;border-right:2px solid currentColor;border-top:2px solid lightgrey;">AKCIJA</th>
                                                                            </tr>
                                                                    </thead>

                                                                    <c:forEach var="greske" items="${penzije}">
                                                                            <tr>
                                                                                    <td style="background-color:white;width:50px;font-size:  9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;border-left:1px solid lightgrey;">${greske.partija}</td>
                                                                                    <td style="background-color:white;width:200px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${greske.ime}</td>
                                                                                    <td style="background-color:white;width:180px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${greske.iznos_rate}</td>
                                                                                    <td style="background-color:white;width:130px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${greske.jmbg}</td>                                                                     
                                                                                    <td style="background-color:white;width:110px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${greske.status}</td>          
                                                                                    <td style="background-color:white;width:150px;font-size: 9px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">${greske.status_racuna}</td>                                                                          
                                                                                    <td style="background-color:white;width:60px;font-size: 10px;text-align:center;border-bottom:1px solid lightgrey;border-right:1px solid lightgrey;">
                                                                                            <spring:url value="/penzijeKnjizenje/${greske.rbr}/details" var="pioUrl" />
                                                                                            <button  style="height:25px;background-color: maroon;color: whitesmoke" onclick="window.onbeforeunload = null;location.href='${pioUrl}'">Details</button>
                                                                                    </td>
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