BUS.account.modal_register_dialog.init(function (selector) {
    BUS.captcha.reload();

    var formHelper = new FormHelper($(selector));
    var loginPerformer = function (loginUrl) {
        var loginArgs = {
            j_username:formHelper.value('username'),
            j_password:formHelper.value('password'),
            _spring_security_user_name:"on"
        };
        $.post(loginUrl, loginArgs).success(function () {
            UI.closeModal();
            BUS.account.after_login();
        });
    };
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    loginPerformer(ans.loginUrl);
                } else {
                    BUS.captcha.reload();
                    formHelper.setError(ans.message, ans.errorList);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
});

BUS.account.modal_login_dialog.init(function (selector) {
    var formHelper = new FormHelper($(selector));
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    UI.closeModal();
                    BUS.account.after_login();
                } else {
                    formHelper.setError(ans.message);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
});