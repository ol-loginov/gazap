<%--@elvariable id="cp" type="java.lang.String"--%>
<%--@elvariable id="content" type="gazap.site.web.views.user.UserProfilePage"--%>
<%--@elvariable id="eVisitor" type="gazap.site.web.extensions.VisitorExtension"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<c:import url="../shared/html.jsp"/>
<html>
<c:import url="../shared/html-head.jsp"/>
<body class="brand-with-menu">
<div id="leftPane">
    <c:import url="../shared/html-bars-brand.jsp"/>
    <c:import url="../shared/html-bars-account.jsp"/>
</div>
<div id="mainColumn" class="profile-page">
    <h1 class="accountName">
        <c:choose>
            <c:when test="${fn:length(content.user.name) eq 0}">
                <span class="noname">
                <lt:t key="UserProfilePage.user.noName"/>
                </span>
            </c:when>
            <c:otherwise>
                <c:out value="${content.user.name}"/>
            </c:otherwise>
        </c:choose>
    </h1>
    <hr class="clear"/>

    <section id="accountMaps">
        <h3>
            <lt:t key="UserProfilePage.accountMaps.h"/>
            <span class="actions">
                <lt:secured-action visitorNeeded="${content.user.id}" action="map.create">
                    <a id="createMap" class=" btn-mini" href="${cp}/map/create">создать карту</a>
                </lt:secured-action>
            </span>
        </h3>

        <c:choose>
            <c:when test="${fn:length(content.maps) gt 0}">
                <ul class="list">
                    <c:forEach items="${content.maps}" var="map">
                        <li>
                            <c:import url="../shared/partial-map-title.jsp">
                                <lt:import-param name="map" value="${map}"/>
                                <lt:import-param name="mapRoles" value="${content.mapRoles[map.id]}"/>
                            </c:import>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p class="empty">
                    <lt:t key="UserProfilePage.accountMaps.empty"/>
                </p>
            </c:otherwise>
        </c:choose>
    </section>

    <section id="accountGames">
        <h3>
            <lt:t key="UserProfilePage.accountGames.h"/>
        </h3>

        <c:choose>
            <c:when test="${fn:length(content.games) gt 0}">
                <ul class="list">
                    <c:forEach items="${content.games}" var="e">
                        <li>
                            <div class="game-title">
                                <h4>
                                    <a href="${cp}${e.route}">
                                        <span class="breakword"><c:out value="${e.title}"/></span>
                                    </a>
                                </h4>
                                <span class="game-flags">
                                        <c:forEach items="${content.gameRoles}" var="gr">
                                            <c:if test="${gr.game eq e.id}">
                                                <span class="game-role ${gr.role}">
                                                    <i class="gameroleicon"></i>
                                                    <lt:t key="UserGameRoles.${gr.role}.flag"/>
                                                </span>
                                            </c:if>
                                        </c:forEach>
                                </span>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <p class="empty">
                    <lt:t key="UserProfilePage.accountGames.empty"/>
                </p>
            </c:otherwise>
        </c:choose>
    </section>

    <section id="accountAvatars">
        <h3>
            <lt:t key="UserProfilePage.accountAvatars.h"/>
        </h3>

        <p class="empty">
            <lt:t key="UserProfilePage.accountAvatars.empty"/>
        </p>
    </section>

    <div class="clear"></div>
</div>
</body>
</html>