<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="l" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:import url="../shared/html.jsp"/>
<div id="modalLogin" class="small-modal-dialog">
  <h1>
    <fmt:message key="Login.title"/>
    <span class="notice">
        <lt:t key="Login.title.registration" arg1="${au}"/>
    </span>
  </h1>

  <p class="notice alert-info">
    <lt:t key="Login.notice.forget" arg1="${au}"/>
  </p>

  <div class="by-form">
    <form id="loginForm" action="${au}/auth" data-ax-action="${au}/auth.ajax" method="post">
      <input type="hidden" name="_spring_security_remember_me" value="on"/>

      <div class="control-group">
        <div class="controls">
          <input type="email" name="j_username" placeholder="<lt:t key="Login.auth.form.username.placeholder"/>"/>
        </div>
      </div>
      <div class="control-group">
        <div class="controls">
          <input type="password" name="j_password" placeholder="<lt:t key="Login.auth.form.password.placeholder"/>"/>
        </div>
      </div>
      <div class="control-group submit-group">
        <button class="btn" type="submit">
          <lt:t key="Login.form.submit"/>
        </button>
        <a class="submit-option modal-closer">
          <lt:t key="Login.form.cancel"/>
        </a>
      </div>
      <div class="alert alert-message alert-progress hidden">
        <lt:t key="Login.form.submitting"/>
      </div>
      <div class="alert alert-error hidden"/>
    </form>
    <script type="text/javascript">UI.triggerInitModalLoginDialog('#loginForm')</script>
  </div>
  <div class="by-social">
        <span class="welcome">
            <lt:t key="Login.auth.3rd"/>
        </span>
    <ul class="unstyled auth-list">
      <c:forEach items="${content.authProviders}" var="e">
        <li>
          <a target="_blank" href="${au}/auth/3rd/${e.provider}" class="auth-provider-invoke x26 ${e.provider}">
            <lt:t key="Login.auth.3rd.${e.provider}"/>
          </a>
        </li>
      </c:forEach>
    </ul>
    <div class="clear"/>
  </div>
</div>