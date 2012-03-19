BUS.map.modal_create_dialog.init(function (selector) {
    var formHelper = new FormHelper($(selector));
    $('#geometryClassGeoid', formHelper.form).change(function () {
        $('.control-group[data-visible-for-input=geometryClassPlain]', formHelper.form).toggle(false);
        $('.control-group[data-visible-for-input=geometryClassGeoid]', formHelper.form).toggle($(this).is(':checked'));
    });
    $('#geometryClassPlain', formHelper.form).change(function () {
        $('.control-group[data-visible-for-input=geometryClassGeoid]', formHelper.form).toggle(false);
        $('.control-group[data-visible-for-input=geometryClassPlain]', formHelper.form).toggle($(this).is(':checked'));
    });
    formHelper.ajaxForm({
        success:function (ans, status, xhr) {
            formHelper.setSubmitting(false);
            if (UI.isJsonResponse(xhr)) {
                if (ans.success) {
                    BUS.map.modal_create_dialog.close(selector);
                    document.location.href = ans.map.route;
                } else {
                    formHelper.setError(ans.message, ans.errorList);
                }
            } else {
                formHelper.form.replaceWith(ans);
            }
        }
    });
});