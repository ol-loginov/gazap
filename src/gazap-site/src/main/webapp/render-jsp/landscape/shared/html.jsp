<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="page" var="moduleCdn" value="${content.moduleCdn}"/>
<c:set scope="request" var="cp" value="${moduleCdn.contextPath}"/>
<c:set scope="request" var="au" value="${moduleCdn.server}${cp}"/>
<c:set scope="request" var="libRoot" value="${cp}/static/landscape/lib"/>
<c:set scope="request" var="scriptsRoot" value="${cp}/static/landscape/scripts"/>
