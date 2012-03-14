<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="lf" uri="http://gazap/jstl/local" %>
<%@ taglib prefix="lt" uri="http://gazap/jstl/local-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:import url="../shared/html.jsp"/>
<div id="modalRegister" class="small-modal-dialog">
    <h1>
        <lt:t key="Register.title"/>
    <span class="notice">
      <lt:t key="Register.title.login" arg1="${au}"/>
    </span>
    </h1>

    <p class="notice alert-info">
        <lt:t key="Register.notice" arg1="${au}"/>
    </p>

    <div>
        <form id="registerForm" action="${au}/auth/register" data-ax-action="${au}/auth/register.ajax"
              method="post">
            <div class="control-group">
                <div class="controls">
                    <input type="text" name="username" placeholder="<lt:t key="Register.form.username.placeholder"/>"/>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <input type="password" name="password"
                           placeholder="<lt:t key="Register.form.password.placeholder"/>"/>
                </div>
            </div>
            <div id="recaptchaContainer">
                <div id="recaptcha-container"
                     class="recaptcha_nothad_incorrect_sol recaptcha_isnot_showing_audio">
                    <p class="wait-message">
                        <lt:t key="Register.waitCaptcha"/>
                    </p>
                </div>
            </div>
            <div class="control-group submit-group">
                <button class="btn" type="submit">
                    <lt:t key="Register.form.submit"/>
                </button>
                <a class="submit-option modal-closer">
                    <lt:t key="Register.form.cancel"/>
                </a>
            </div>
            <div class="alert alert-message alert-progress hidden">
                <lt:t key="Register.form.submitting"/>
            </div>
            <div class="alert alert-error hidden"/>
        </form>
        <script type="text/javascript">
            BUS.captcha.init('<lt:t key="recaptcha.public"/>');
            BUS.account.modal_register_dialog.init('#registerForm')
        </script>
    </div>
</div>
