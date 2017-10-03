<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="/WEB-INF/jsp/fragments/header.jsp" />

<body>

	<div class="container">

		<h1>Error Page</h1>

		<p>${exception.message}</p>
		 Exception: ${exception.message}.
		  	<c:forEach items="${exception.stackTrace}" var="stackTrace"> 
				${stackTrace} 
			</c:forEach>
	  	
                <c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
                </c:if>

	</div>

	<jsp:include page="/WEB-INF/jsp/fragments/footer.jsp" />

</body>
</html>