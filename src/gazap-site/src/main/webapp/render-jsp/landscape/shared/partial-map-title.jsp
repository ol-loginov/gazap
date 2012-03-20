<%--@elvariable id="cp" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--@elvariable id="map" type="gazap.site.model.viewer.MapTitle"--%>
<lt:import-param name="map" var="map"/>
<lt:import-param name="mapRoles" var="mapRoles"/>
<div class="map-title has-flags">
    <c:if test="${fn:length(param.mapRoles) gt 0}">
        <ul class="container-flags unstyled">
            <c:forEach items="${param.mapRoles}" var="gr">
                <li class="flag maproleflag ${gr}"></li>
            </c:forEach>
        </ul>
    </c:if>
    <h4>
        <a href="${cp}${map.route}">
            <span class="breakword"><c:out value="${map.title}"/></span>
        </a>
    </h4>
</div>

