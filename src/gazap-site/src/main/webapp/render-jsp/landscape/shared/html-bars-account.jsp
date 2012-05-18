<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--@elvariable id="au" type="java.lang.String"--%>
<%--@elvariable id="cp" type="java.lang.String"--%>
<%--@elvariable id="eVisitor" type="gazap.site.web.extensions.VisitorExtension"--%>
<c:set scope="page" var="m" value="${eVisitor}"/>
<section id="accountBar" class="logged-${m.logged}">
    <c:choose>
        <c:when test="${m.logged}">
            <div class="account-name">
                <a class="username" href="${cp}${m.user.route}">
                    <img src="http://www.gravatar.com/avatar/${m.user.gravatar}?s=16" alt="" width="16"
                         height="16"/><span><c:choose>
                    <c:when test="${fn:length(m.user.name) gt 0}">
                        <c:out value="${m.user.name}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="tb.menu.welcome.anonymous.name"/>
                    </c:otherwise>
                </c:choose></span>
                </a>
            </div>
            <div class="account-menu">
                <div class="opener">
                    <a class="toggler pull-right" href="#">
                        <i class="glyphicon-resize-small collapse"></i>
                        <i class="glyphicon-resize-full expand"></i>
                    </a>
                    <ul class="notification-icons unstyled">
                        <li>
                            <span class="value">15</span>
                            <span class="label">друзей</span>
                        </li>
                        <li>
                            <span class="value">0</span>
                            <span class="label">писем</span>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </div>
                <ul class="unstyled menu-column">
                    <li>
                        <a href="${cp}/settings">
                            <i class="glyphicon-cog"></i>
                            <lt:t key="accountBar.accountMenu.settings"/>
                        </a>
                    </li>
                    <li>
                        <a href="${cp}/auth/logout">
                            <i class="glyphicon-plane"></i>
                            <lt:t key="accountBar.accountMenu.logout"/>
                        </a>
                    </li>
                </ul>
                <ul class="unstyled menu-column">
                    <c:if test="${m.user.summary.worldsCreated gt 0}">
                        <li>
                            <a href="${cp}${m.user.route}/worlds">
                                <i class="glyphicon-info-sign"></i>
                                <lt:t key="accountBar.accountMenu.worlds"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${m.user.summary.mapsCreated gt 0}">
                        <li>
                            <a href="${cp}${m.user.route}/maps">
                                <i class="glyphicon-flag"></i>
                                <lt:t key="accountBar.accountMenu.maps"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${m.user.summary.playersCreated gt 0}">
                        <li>
                            <a href="${cp}${m.user.route}/avatars">
                                <i class="glyphicon-info-sign"></i>
                                <lt:t key="accountBar.accountMenu.avatars"/>
                            </a>
                        </li>
                    </c:if>
                </ul>
                <div class="clear"></div>
            </div>
            <script type="text/javascript">
                $('#accountBar .account-menu .opener .toggler').click(function () {
                    $('#accountBar .account-menu').toggleClass('expanded');
                    return false;
                });
            </script>
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