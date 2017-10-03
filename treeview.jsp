<%@ page session="false"%>
<%@page  language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>jstree</title>
	<style>
	html { margin:0; padding:0; font-size:62.5%; }
	body { max-width:300px; min-width:100px; margin:0 auto; padding:20px 10px; font-size:14px; font-size:1.4em; }
	h1 { font-size:1.8em; }
	.demo { overflow:auto; border:1px solid silver; min-height:700px; }
	</style>
	<!--<link type="text/css" rel="stylesheet" href="/include/style.min.css" />-->
</head>
<body>
    
	<div id="frmt" class="demo"></div>
	<script type="text/javascript" src="/resources/jquery.min.js"></script>
	<script type="text/javascript" src="/resources/jstree.min.js"></script>
        <!--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jstree/3.0.9/jstree.min.js"></script>-->
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jstree/3.0.9/themes/default/style.min.css" />
	<script type="text/javascript">
              $(function() {
                $('#frmt').jstree
                ({
                        'core' : {
                                'data' : [
                                        {
                                                "text" : "Trade Service",
                                                "state" : { "opened" : true },
                                                "children" : [
                                                        {
                                                                "text" : "Child node 1",
                                                                "state" : { "selected" : true },
                                                                "icon" : "jstree-file"
                                                        },
                                                        { "text" : "Child node 2", "state" : { "disabled" : true } }
                                                ]
                                        }
                                ]
                        }
                });
              });
        </script>
</body>
</html>