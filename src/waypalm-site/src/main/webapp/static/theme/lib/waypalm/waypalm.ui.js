var A = {};

(function (undefined) {
    A.ajaxFormController = function () {
        var submitClass = 'ajax-submit-in-progress';
        var controller = {
            form: null,
            beforeSubmit: function (params, $form, opts) {
                controller.form = $form;
                controller.form.addClass(submitClass);
            },
            error: function (xhr, status, err) {
                controller.form.removeClass(submitClass);
            },
            success: function (response, status, xhr) {
                controller.form.removeClass(submitClass);
            }
        };
        return controller;
    };
})();
