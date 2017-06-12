<%@ page import="org.springframework.security.saml.SAMLCredential" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.opensaml.saml2.core.Attribute" %>
<%@ page import="org.springframework.security.saml.util.SAMLUtil" %>
<%@ page import="org.opensaml.xml.util.XMLHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring Security-SAML</title>
</head>
<body>
	<%
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SAMLCredential credential = (SAMLCredential) authentication.getCredentials();
        pageContext.setAttribute("authentication", authentication);
        pageContext.setAttribute("credential", credential);
     %>
	 <c:if test="${authentication.name != 'anonymousUser'}"> 
		<h2 style="display:inline-block;">
		 Welcome : <c:out value="${authentication.name}"/>
		</h2>	
		<div style="display:inline-block;">
		<form class="left" action="<c:url value="/saml/logout"/>" method="get">
              <input type="submit" value="Global Logout" class="button"/>
        </form>
        <form class="left" action="<c:url value="/saml/logout"/>" method="get">
              <input type="hidden" name="local" value="true"/>
              <input type="submit" value="Local Logout" class="button"/>
        </form>
		</div>			
 		
		<p>Your Session id is: "${pageContext.request.session.id}"</p>

	</c:if>

</body>
</html>