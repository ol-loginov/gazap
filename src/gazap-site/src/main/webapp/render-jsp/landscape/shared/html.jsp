<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--@elvariable id="eCdn" type="gazap.site.web.views.CdnExtension"--%>
<c:set scope="request" var="cp" value="${eCdn.contextPath}"/>
<c:set scope="request" var="au" value="${eCdn.server}${cp}"/>
<c:set scope="request" var="libRoot" value="${cp}/static/landscape/lib"/>
<c:set scope="request" var="scriptsRoot" value="${cp}/static/landscape/scripts"/>
<c:set scope="request" var="themeRoot" value="${cp}/static/landscape"/>
