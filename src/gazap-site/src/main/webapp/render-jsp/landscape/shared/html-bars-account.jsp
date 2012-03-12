<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%--@elvariable id="au" type="java.lang.String"--%>
<%--@elvariable id="cp" type="java.lang.String"--%>
<%--@elvariable id="content" type="gazap.site.web.views.GazapPage"--%>
<c:set scope="page" var="m" value="${content.moduleVisitor}"/>
<section id="accountBar" class="logged-${m.logged}">
    <c:choose>
        <c:when test="${m.logged}">
            <div class="account-name">
                <span class="username">
                    <img src="http://www.gravatar.com/avatar/${m.user.gravatar}?s=16" alt=""/><span><c:choose>
                    <c:when test="${fn:length(m.user.name) gt 0}">
                        <c:out value="${m.user.name}"/>
                    </c:when>
                    <c:otherwise>
                        <fmt:message key="tb.menu.welcome.anonymous.name"/>
                    </c:otherwise>
                </c:choose></span>
                </span>
            </div>
            <div class="account-menu">
                <div class="opener">
                    <a class="toggler expand pull-right" href="#" title="<lt:t key="accountBar.opener.expand"/>">
                        <i class="glyphicon-resize-full"></i>
                    </a>
                    <a class="toggler collapse pull-right" href="#" title="<lt:t key="accountBar.opener.collapse"/>">
                        <i class="glyphicon-resize-small"></i>
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
                            <i class="glyphicon-home"></i>
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
                    <li>
                        <a href="${cp}${m.user.route}/maps">
                            <i class="glyphicon-flag"></i>
                            <lt:t key="accountBar.accountMenu.maps"/>
                        </a>
                    </li>
                    <li>
                        <a href="${cp}${m.user.route}/avatars">
                            <i class="glyphicon-info-sign"></i>
                            <lt:t key="accountBar.accountMenu.avatars"/>
                        </a>
                    </li>
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