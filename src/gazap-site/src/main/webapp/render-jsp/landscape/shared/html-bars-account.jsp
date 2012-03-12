<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page pageEncoding="UTF-8" %>
<c:set scope="page" var="visitor" value="${content.moduleVisitor}"/>
<section id="accountBar" class="logged-${visitor.logged}">
    <c:choose>
        <c:when test="${visitor.logged}">
            <div class="account-name">
                <a href="${cp}/profile">
                    <img src="http://www.gravatar.com/avatar/${visitor.gravatar}?s=16"/>
                <span>
                    <c:choose>
                        <c:when test="${fn:length(visitor.name) gt 0}">
                            <c:out value="${visitor.name}"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:message key="tb.menu.welcome.anonymous.name"/>
                        </c:otherwise>
                    </c:choose>
                </span>
                </a>
            </div>
            <div class="account-menu">
                <ul class="unstyled">
                    <li>
                        <a href="${cp}/user/${visitor.id}/maps">Карты</a>
                    </li>
                    <li>
                        <a href="${cp}/auth/logout">Выход</a>
                    </li>
                </ul>
            </div>
        </c:when>
        <c:otherwise>
            <h3>
                <lt:t key="accountBar.anonymous.welcome"/>
            </h3>

            <p>
                <lt:t key="accountBar.anonymous.welcome.p" arg1="${au}"/>
            </p>
        </c:otherwise>
    </c:choose>
</section>