<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
        <script type="text/javascript" src="/PIO/resources/jquery-ui-1.11.4/jquery.ui.min.js"></script>
        <script type="text/javascript" src="/PIO/resources/js/js_lib.js"></script>
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">
        <link type="text/css" rel="stylesheet" href="/PIO/resources/css/sidenav.css" >
        <title>PIO APPLICATION</title>

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
           
                    window.onload=function()
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
           
                    window.onbeforeunload =function(e) 
                    {
                      var form = document.createElement("form");
                      var method ="post"; 
                      var aa="/PIO/closeEvent";
                      form.setAttribute("method", method);
                      form.setAttribute("action", aa);
                      document.body.appendChild(form);
                      form.submit();

                    };
        </script>

    </head>


    <body id="body" >
    <input type="hidden" id="menu_id" name="menu_id" value="0"/> 
         <table id="body" style="background-color:azure;vertical-align:top;width:100%;height:100%">
        <tr id="body"  style="width:100%;border-bottom:2px solid whitesmoke ">
                                    <td style="text-align:right;">
                                        <a href="#"><img border='0' src="include/img/header_logo_SG_1.gif" alt="Close" style="margin-left:auto;margin-right:auto;"/></a>
                                        SOCIETE GENERALE BANKA SRBIJA
                                    </td>
                                    <td style="text-align:center;color:whitesmoke;font-size:20px;font-family:Arial">
                                        PIO APLIKACIJA ::
                                    </td>
        </tr>
         <tr  style="width:100%;">
                            <td > <span id="menu_click" style="font-size:20px;cursor:pointer" onclick="closeNavig();">&#9776; MENU</span></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td> </td>
                            <td></td>
                        </tr>
         <tr>
            <td style="width:250px;vertical-align: top"> 
              <div id="menu_items" class="sidenavig">
                  <jsp:include page="/WEB-INF/jsp/newtree.jsp" />
               </div>
            </td>
            <td id="body" style="background-color:azure;vertical-align:central;color:#a8e4ff; font-family:serif;font-size:25px;font-weight:bold">
                <div style="width:100%">
                    <table style="width:100%">
                        <tr>
                           <td style="width:20%">

                                            </td>
                           <td style="width:80%">
                                    <h1>WELCOME TO PIO APPLICATION !!!</h1>
                            </td>
                        </tr>
                    </table>
                    </div>
            </td>
        </tr>
         </table>
    </body>
</html>
