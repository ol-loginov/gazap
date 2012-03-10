UI.bindInitModalLoginDialog(function (selector) {
    var formHelper = new FormHelper($(selector));
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    UI.closeModal();
                    UI.triggerLogIn();
                } else {
                    formHelper.setError(ans.message);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
});