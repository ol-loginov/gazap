<%--@elvariable id="content" type="gazap.site.web.views.user.UserProfilePage"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<c:import url="../shared/html.jsp"/>
<html>
<c:import url="../shared/html-head.jsp"/>
<body>
<div id="leftPane">
    <c:import url="../shared/html-bars-brand.jsp"/>
    <c:import url="../shared/html-bars-account.jsp"/>
</div>
<div id="navPane">
    <c:import url="../shared/html-bars-menu.jsp"/>
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

    <section id="accountGames">
        <h3>
            <lt:t key="UserProfilePage.accountGames.h"/>
            <span class="actions">
                <button id="createGame" class="btn btn-mini" data-ax-href="${cp}/game/create.ajax">создать</button>
            </span>
        </h3>
        <div id="accountGameForm" class="section-header-form hidden"></div>
        <script type="text/javascript">UserProfile.Games.init()</script>

        <p class="empty">
            <lt:t key="UserProfilePage.accountGames.empty"/>
        </p>
    </section>

    <section id="accountMaps">
        <h3>
            <lt:t key="UserProfilePage.accountMaps.h"/>
            <span class="actions">
                <button id="createMap" class="btn btn-mini">создать</button>
            </span>
        </h3>
        <div id="accountMapForm"></div>

        <p class="empty">
            <lt:t key="UserProfilePage.accountMaps.empty"/>
        </p>
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