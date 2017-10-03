<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
<title>PIO Application</title>

<spring:url value="/include/hello.css" var="coreCss" />
<spring:url value="/include/bootstrap.min.css"
	var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>

<spring:url value="/backEvent" var="urlHome" />

	<div>
            <div class="navbar-header" align="center">
			<a style="font-size:15px; font-weight:bold;" class="navbar-brand" href="${urlHome}">PIO Service Forms</a>
		</div>
	</div>
