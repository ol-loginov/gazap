BUS.game.create_dialog.init_modal(function (selector) {
    var formHelper = new FormHelper($(selector));
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    BUS.game.create_dialog.close_modal(selector);
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
        BUS.game.create_dialog.close_modal(selector);
    });
});
