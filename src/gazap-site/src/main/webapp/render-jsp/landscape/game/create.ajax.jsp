<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="l" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:import url="../shared/html.jsp"/>
<div id="modalGameCreate">
    <form id="loginForm" action="${cp}/game/create" data-ax-action="${au}//game/create.ajax" method="post">
        <input type="hidden" name="_spring_security_remember_me" value="on"/>

        <div class="control-group">
            <div class="controls">
                <input type="email" name="j_username"
                       placeholder="<lt:t key="Login.auth.form.username.placeholder"/>"/>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <input type="password" name="j_password"
                       placeholder="<lt:t key="Login.auth.form.password.placeholder"/>"/>
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
        <script type="text/javascript">/*UI.triggerInitModalLoginDialog('#loginForm')*/</script>
    </form>
    <hr/>
</div>