BUS.world.modal_create_dialog.init(function (selector) {
    var formHelper = new FormHelper($(selector));
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    BUS.world.modal_create_dialog.close(selector);
                    BUS.account.world_added();
                } else {
                    formHelper.setError(ans.message, ans.errorList);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
    $('.form-canceller', formHelper.form).click(function () {
        BUS.world.modal_create_dialog.close(selector);
    });
});
