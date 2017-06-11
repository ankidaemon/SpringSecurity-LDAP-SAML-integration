<%@ page import="org.springframework.security.saml.metadata.MetadataManager"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="java.util.Set"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<body>
	<%
       WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletConfig().getServletContext());
       MetadataManager mm = context.getBean("metadata", MetadataManager.class);
       Set<String> idpSet = mm.getIDPEntityNames();
       pageContext.setAttribute("idp", idpSet);
     %>
     <h2>Select Identity Provider to authenticate with.</h2>
     <br>
     <br>
	<form action="<c:url value="${requestScope.idpDiscoReturnURL}"/>" method="GET">
		<c:forEach var="idpItem" items="${idp}">
			<input type="radio" name="${requestScope.idpDiscoReturnParam}" 
				id="idp_<c:out value="${idpItem}"/>"
				value="<c:out value="${idpItem}"/>" />
			<label for="idp_<c:out value="${idpItem}"/>"><c:out
					value="${idpItem}" /></label>
			<br />
		</c:forEach>
		<input type="submit" value="Single Sign-On" />
	</form>
</body>
</html>