UI.bindInitLoginDialogAjax(function (selector) {
    var form = $(selector);
    form.ajaxForm({
        url:form.attr('a-action'),
        success:function (ans, status, xhr) {
            if (UI.isJsonResponse(xhr)) {

            } else {
                form.replaceWith(ans);
            }
        }
    });
});