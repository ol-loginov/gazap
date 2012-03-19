<%--@elvariable id="cp" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<c:set var="map" scope="page" value="${param.map}"/>
<div class="map-title has-flags">
    <ul class="container-flags unstyled">
        <c:if test="${not param.visitorRoles eq null}">
            <c:forEach items="${param.visitorRoles}" var="gr">
                <li class="flag maproleflag ${gr.role}"></li>
            </c:forEach>
        </c:if>
    </ul>
    <h4>
        <a href="${cp}${map.route}">
            <span class="breakword"><c:out value="${map.title}"/></span>
        </a>
    </h4>
</div>

