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
<spring:url value="/backEvent" var="urlAddUser" />

<nav class="navbar-inverse">
	<div>
		<div class="navbar-header">
			<a class="navbar-brand" href="${urlHome}">PIO Form</a>
		</div>
		<div id="navbar">
			<ul class="nav navbar-nav navbar-right">
				<li class="active"><a href="${urlAddUser}">GO TO APLLICATION</a></li>
			</ul>
		</div>
	</div>
</nav>