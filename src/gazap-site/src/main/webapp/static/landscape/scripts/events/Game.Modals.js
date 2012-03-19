BUS.game.modal_create_dialog.init(function (selector) {
    var formHelper = new FormHelper($(selector));
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    BUS.game.modal_create_dialog.close(selector);
                    BUS.account.game_added();
                } else {
                    formHelper.setError(ans.message, ans.errorList);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
    $('.form-canceller', formHelper.form).click(function () {
        BUS.game.modal_create_dialog.close(selector);
    });
});
