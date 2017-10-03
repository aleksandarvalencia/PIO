<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>DETALJI O KORISNIKU</title>
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
                  
                          /* (window).resize(function () 
                                   {
                                       var h = $(window).height();
                                       //var h = Math.max($(window).height(), 450);
                                       $('#body').height(h).filter('.default').css('lineHeight', h + 'px');
                               }).resize();*/
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
                    };
            
        </script>
    </head>
<body id="body">
 <div id="menu_items" class="sidenavig" style="position: absolute; top: 120px; left: 0px; width: 0px; height:577px;filter:alpha(opacity=90);opacity:0.9;">
         <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
</div>
    <input type="hidden" id="menu_id" name="menu_id" value="1"/>
    <table id="body" style="width:100%;height:100%" >
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

            <td style="vertical-align:top;">      
                   <table style="width:100%">
                                         <tr>
                                            <td style="width:50px">&nbsp;</td>
                                            <td>
                                                <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span>
                                                <h1 style="color:whitesmoke;font-size:20px;text-align:left;">PODACI O KORISNIKU::</h1>                                        
                                                <hr style="background-color:darkslategray;width:100%;height:2px"/>
                                            </td>
                   </table>
                 <br/>
                 <table style="width:100%;height:100%">
                        <tr>
                            <td style="width:4%">&nbsp;</td>
                            <td style="width:95%;text-align:left;border-bottom:1px solid whitesmoke;border-top:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color:whitesmoke"><b>ID ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.idUser}</label>
                            </td>
                        </tr>
                         <tr>
                             <td style="width:4%">&nbsp;</td>
                             <td style="width:95%;text-align:left;border-bottom:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color: whitesmoke"><b>Korisnicko Ime ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.username}</label>
                             </td>
                        </tr>
                        <tr>
                             <td style="width:4%">&nbsp;</td>
                            <td style="width:95%;text-align:left;border-bottom:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color: whitesmoke"><b>Ime ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.firstname}</label>
                            </td>
                        </tr>
                         <tr>
                              <td style="width:4%">&nbsp;</td>
                             <td style="width:95%;text-align:left;border-bottom:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color: whitesmoke"><b>Prezime ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.secondname}</label>
                             </td>
                        </tr>
                         <tr>
                              <td style="width:4%">&nbsp;</td>
                             <td style="width:95%px;text-align:left;border-bottom:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color: whitesmoke"><b>Nivo ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.idAuth}</label>
                             </td>
                        </tr>
                        <tr>
                            <td style="width:4%">&nbsp;</td>
                            <td style="width:95%;text-align:left;border-bottom:1px solid whitesmoke;">
                                <label class="btn-primary" style="width:20%;color: whitesmoke"><b>Status ::</b></label>
                                <label class="btn-primary" style="width:20%;color: #a8e4ff">${users.status}</label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                               &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>

                            <td style="width:1%">&nbsp;</td>
                            <td style="width:99%">
                            <hr style="background-color:darkslategray;width:100%;height:2px"/>
                            </td>
     
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                         <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                         <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                         <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                         <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                 </table>
            </td>
            <td></td>
       </tr>
     </table>
<div style="width:100%">
 <jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />
</div>
</body>
</html>