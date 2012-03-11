<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
        </ul>
      </div>
    </c:when>
    <c:otherwise>
      <h3>
        <fmt:message key="accountBar.anonymous.welcome"/>
      </h3>

      <p>
        <fmt:message key="accountBar.anonymous.welcome.p">
          <fmt:param value="${au}"/>
        </fmt:message>
      </p>
    </c:otherwise>
  </c:choose>
</section>