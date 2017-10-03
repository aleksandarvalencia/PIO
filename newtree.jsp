<%@page    language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib  uri="http://www.springframework.org/tags/form"  prefix="form" %>
<%@taglib  uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@taglib  uri="http://www.springframework.org/tags"  prefix="spring" %>
<%@taglib  uri="http://java.sun.com/jstl/fmt" prefix="fmt"  %>
<%@taglib  uri="http://java.sun.com/jstl/core" prefix="cor"  %>
<%@taglib prefix="util" tagdir="/WEB-INF/tags/util" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Title</title>
                <!--<sec:csrfMetaTags/>-->
		<meta name="viewport" content="width=device-width" />
                <script type="text/javascript" src="/PIO/resources/jquery-2.1.4.js"></script>
                <link type="text/css" rel="stylesheet" href="/PIO/resources/css/style.min.css" />
                <script type="text/javascript" src="/PIO/resources/js/jstree/jquery.min.js"></script>
                <script type="text/javascript" src="/PIO/resources/js/jstree/jstree.min.js"></script>
                <script type="text/javascript" src="/PIO/resources/moment.js"></script>
                <script type="text/javascript" src="/PIO/resources/js/hello.js"></script>
                <script type="text/javascript" src="/PIO/resources/js/jstree/bootstrap.min.js"> </script> 
<!--                <link type="text/css" rel="stylesheet" href="/PIO/resources/css/bootstrap-combined.min.css">-->
                <link type="text/css" rel="stylesheet" media="screen" href="/PIO/resources/css/bootstrap-datetimepicker.min.css"> 
                <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.min.js"></script>
                <script type="text/javascript" src="/PIO/resources/js/datepicker/bootstrap-datetimepicker.pt-BR.js"></script>
                <link type="text/css" rel="stylesheet" href="/PIO/include/hello.css"/>
                <!--<link type="text/css" rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jstree/3.0.9/themes/default/style.min.css" />--><!--skinuto -->
                <!--<script src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js"></script>--> <!--skinuto -->
                <!--<script type="text/javascript" src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js"></script>--> <!--skinuto -->
                <!--<script type="text/javascript" src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"> </script>--> <!--skinuto -->
                <!--<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jstree/3.0.9/jstree.min.js"></script>--><!--skinuto -->
                <!-- <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>--><!--skinuto -->
                <!-- <link type="text/css" rel="stylesheet" media="screen" href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">--> <!--skinuto -->
                <!--<link type="text/css" rel="stylesheet" href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css">-->
                <style>
		html, body { background:#ebebeb; font-size:10px; font-family:Verdana; margin:0; padding:0; }
		//#container { min-width:320px; margin:0px auto 0 auto; background:white; border-radius:0px; padding:0px; overflow:hidden; }
                #container {max-width:300px; margin:0px auto 0 auto; background:white;background-color: gray; border-radius:0px; padding:0px; overflow:hidden;border-right: 2px solid silver;height:500px;  }
                //#headcontainer { min-width:320px; margin:0px auto 0 auto; background:white; border-bottom:1px solid silver; border-radius:0px; padding:0px; overflow:hidden; }
                #headcontainer { max-width:300px; margin:0px auto 0 auto; background:white;background-color: gray; border-bottom:1px solid silver; border-radius:0px; padding:0px; overflow:hidden; }
		//#tree { float:left; min-width:250px; border-right:1px solid silver; overflow:auto; padding:0px 0; }
                #tree { float:left; max-width:300px;width:250px; border-right:2px solid silver;  border-left:2px solid silver; border-bottom:2px solid silver;height:600px; overflow:auto; padding:0px 0;background-color:gray;color:mintcream;vertical-align:top;font-weight:normal;}
		//#data { margin-left:320px; }
                #data { margin-left:0px; }
		#data textarea { margin:0; padding:0; height:100%; width:100%; border:0; background:white; display:block; line-height:18px; }
		#data, #code { font: normal normal normal 12px/18px 'Consolas', monospace !important; }
		</style>
	</head>
	<body>

            <div id="headcontainer" style="vertical-align:top;background-color:mintcream;border-top: 2px solid silver;border-left: 2px solid silver;border-right: 2px solid silver;">
<!--                <button name="button_open" id="open" onclick="OpenTree();" style="height:23px;width:100px; font-size:12px;text-align:center;background-color:beige">open</button>
                <input name="button_close" id="button_close" type="button" value="close" onclick="CloseTree();" style="height:23px;width:100px; font-size:12px;text-align:center;background-color:azure;vertical-align:central;">-->
<table style="width:250px;text-align:center;background-color:gray">
                    <tr>
                        <td style="width:125px"> &nbsp;</td>
                        <td style="width:125px">&nbsp;</td>
                    </tr>
                    <tr>
                        <td style="width:125px;border-right:1px solid silver;text-align:center;">
                                <a id="open_image" href="#"><img border='0' src="/PIO/include/img/folder-open50.png" alt="Open" style="margin-left:auto;margin-right:auto;" onClick="OpenTree();"/></a>
                        </td>
                        <td style="width:125px;text-align:center;">
                                <a id="close_image" href="#"><img border='0' src="/PIO/include/img/folder-closed50.png" alt="Close" style="margin-left:auto;margin-right:auto;" onClick="CloseTree();"/></a>
                         </td>
                    </tr>
                </table>
            </div>
            <div id="container" >
			<div id="tree"></div>
			<div id="data">
				<div  style="display:none;"><textarea id="code" readonly="readonly"></textarea></div>
				<div  style="display:none;"></div>
				<div  style="display:none; position:relative;"><img src="" alt="" style="display:block; position:absolute; left:50%; top:50%; padding:0; max-height:90%; max-width:90%;" /></div>
				<div  style="text-align:center;"></div>
			</div>
		</div>
		<script type="text/javascript">
		$(document).ready
                ( $(function() {
			/*$(window).resize(function () {
				var h = Math.max($(window).height()+800, 450);
                                //var h = Math.max($(window).height(), 450);
                                var head = Math.max($(window).height(), 450);
				$('#container, #data, #tree, #data .content').height(h).filter('.default').css('lineHeight', h + 'px');
                                $('#headcontainer .content').height(head).filter('.default').css('lineHeight', head + 'px');
			}).resize();*/
                        /*$(window).resize(function () 
                        {
                            var h = $(window).height();
                            //var h = Math.max($(window).height(), 450);
                            $('#container, #data, #tree, #data .content').height(h).filter('.default').css('lineHeight', h + 'px');
                        }).resize();*/
			$('#tree')
				.jstree({
					'core' : {
						'data' : {
							'url' : '<spring:url value="/newtree.json"/>',
                                                        'dataType' : 'json',
                                                        'type' : 'GET',
                                                        'async': false,
							'data' : function (node) {
								return { 'id' : node.id};
                                                                //return { id : node.id ? node.id : "aaa" }; 
                                                                //return {'id' : "Child node 1"};
							},
                                                        error:function(result) {alert("greska");}
						},
						'check_callback' : true,
						"themes" : {
							'responsive' : false
						}
                                                
                                                },
                                                /*"types" : {
                                                        "default" : {
                                                          "icon" : "glyphicon glyphicon-flash"
                                                        },
                                                        "demo" : {
                                                          "icon" : "glyphicon glyphicon-ok"
                                                        }
                                                      },*/

					'force_text' : true,
					'plugins' : ['themes','hotkeys','json_data','types','contextmenu','ui','search','sort']
				});
                                
                                $("#tree").bind("select_node.jstree", function(e, data)
                                {                  
                                           var pagelink="/PIO/" + data.node.data.text;
                                           //alert(pagelink);
					   window.onbeforeunload = null;
                                           window.location.href=pagelink;
                                                /*$.ajax({dataType : 'json',
                                                  url : pagelink,
                                                  type : 'GET',
                                                  enctype: 'multipart/form-data',
                                                  data: function(result) {
                                                       $('#data').html(result.responseText).show();
                                                  },
                                                  success : function(result) {
                                                  if (result.readyState === 4 && result.status === 200)
                                                     {
                                                         //alert(JSON.stringify(result));
                                                        $('#data').html(result.responseText).show();
                                                     }
                                                     else
                                                     {
                                                         
                                                         alert(JSON.stringify(result));
                                                     }
                                                  },
                                                  error : function(result){
                                                     if (result.readyState === 4 && result.status === 200)
                                                     {
                                                        //alert(JSON.stringify(result));
                                                        $('#data').html(result.responseText).show();
                                                     }
                                                     else
                                                     {
                                                         
                                                         alert(JSON.stringify(result));
                                                     }

                                                  }
                                              });*/
        
        
        
                                           // }odavde ide sto treab stvarno da bude zakomentarisano
                                          /* if (data.node.data.text==="beforeupdate")
                                           {
                                                $.ajax({dataType : 'json',
                                                  url : '<spring:url value="/beforeupdate.json"/>',
                                                  type : 'GET',
                                                  enctype: 'multipart/form-data',
                                                  //processData: true, 
                                                  //contentType: false,
                                                  data: function(result) {
                                                       $('#data').html(result.responseText).show();
                                                  },
                                                  success : function(result) {
                                                  if (result.readyState === 4 && result.status === 200)
                                                     {
                                                         //alert(JSON.stringify(result));
                                                        $('#data').html(result.responseText).show();
                                                     }
                                                     else
                                                     {
                                                         
                                                         alert(JSON.stringify(result));
                                                     }
                                                  },
                                                  error : function(result){
                                                     if (result.readyState === 4 && result.status === 200)
                                                     {
                                                        //alert(JSON.stringify(result));
                                                        $('#data').html(result.responseText).show();
                                                     }
                                                     else
                                                     {
                                                         
                                                         alert(JSON.stringify(result));
                                                     }

                                                  }
                                              });
                                            }*/
                                });
                              
                                /*.on('select_node.jstree', function(e,data) { 
                                        var link = $('#' + data.selected).find('a');
                                        if (link.attr("href") !== "#" && link.attr("href") !== "javascript:;" && link.attr("href") !== "") {
                                            if (link.attr("target") === "_blank") {
                                                link.attr("href").target = "_blank";
                                            }
                                            document.location.href = link.attr("href");
                                            return false;
                                        }
                                      });*/
                       
                                
                       
                    })
                        
                      
                );
                $("#tree").on("ready.jstree", function(event, data)
                                { 
                                            
                                            var instance = $('#tree').jstree(true);
                                            instance.open_all();
        
                                            /*alert(data.instance._model.data.childnode2.data.fdata);
                                            var pagelink="/TradeService/" + data.instance._model.data.childnode2.data.fdata + ".json";
                                                     $.ajax({dataType : 'json',
                                                       url : pagelink,
                                                       type : 'GET',
                                                       enctype: 'multipart/form-data',
                                                       //processData: true, 
                                                       //contentType: false,
                                                       data: function(result) {
                                                           
                                                            $('#data').html(result.responseText).show();
                                                       },
                                                       success : function(result) {
                                                       if (result.readyState === 4 && result.status === 200)
                                                          {
                                                            
                                                             $('#data').html(result.responseText).show();
                                                          }
                                                          else
                                                          {

                                                              alert(JSON.stringify(result));
                                                          }
                                                       },
                                                       error : function(result){
                                                          if (result.readyState === 4 && result.status === 200)
                                                          {
                                                               
                                                                $('#data').html(result.responseText).show();
                                                          }
                                                          else
                                                          {

                                                              alert(JSON.stringify(result));
                                                          }

                                                       }
                                                   });*/
                                                 
                                });
                   
                    function OpenTree() 
                    {
                        var instance = $('#tree').jstree(true);
                        instance.open_all();
                        //instance.select_node('1');
                        //$("#tree").jstree("open_all", -1);
                      };
                     function CloseTree() 
                     {
                        var instance = $('#tree').jstree(true);
                        instance.close_all();
                        //instance.select_node('1');
                      };
                      /*$(function () {
                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        $(document).ajaxSend(function(e, xhr, options) {
                                xhr.setRequestHeader(header, token);
                        });
                        });*/
                </script>
	</body>
</html>