UI.bindInitModalRegisterDialog(function (selector) {
    UI.triggerReloadCaptcha();

    var formHelper = new FormHelper($(selector));
    var loginPerformer = function (loginUrl) {
        var loginArgs = {
            j_username:formHelper.value('username'),
            j_password:formHelper.value('password'),
            _spring_security_user_name:"on"
        };
        $.post(loginUrl, loginArgs).success(function () {
            UI.closeModal();
            UI.triggerLogIn();
        });
    };
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    loginPerformer(ans.loginUrl);
                } else {
                    formHelper.setError(ans.message, ans.errorList);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
})
;