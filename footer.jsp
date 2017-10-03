<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div>
	<hr>
	<footer>
            <p>&copy; <b>PIO APPLICATION</b></p>
	</footer>
</div>

<script type="text/javascript" src="jquery-2.1.4.js"></script>

<spring:url value="/resources/js/hello.js" var="coreJs" />
<spring:url value="/resources/js/bootstrap.min.js"
	var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>


