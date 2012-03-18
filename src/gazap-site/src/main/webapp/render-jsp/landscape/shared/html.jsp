<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--@elvariable id="eCdn" type="gazap.site.web.views.CdnExtension"--%>
<c:set scope="page" var="moduleCdn" value="${eCdn}"/>
<c:set scope="request" var="cp" value="${moduleCdn.contextPath}"/>
<c:set scope="request" var="au" value="${moduleCdn.server}${cp}"/>
<c:set scope="request" var="libRoot" value="${cp}/static/landscape/lib"/>
<c:set scope="request" var="scriptsRoot" value="${cp}/static/landscape/scripts"/>
