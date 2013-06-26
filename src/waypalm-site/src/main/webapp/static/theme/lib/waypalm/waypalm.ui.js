var A = {};

(function ($, undefined) {
    A.needF = function (desiredFunction) {
        return $.isFunction(desiredFunction) ? desiredFunction : $.noop;
    };

    A.showFormErrors = function (form, errorList) {
    };

    A.ajaxFormController = function (onSuccess, onError, onBeforeSubmit) {
        var submitClass = 'ajax-submit-in-progress';
        var currentForm = null;
        return A.ajaxFormControllerRaw(
            function (data) {
                if (data.errors && data.errors.length > 0) {
                    A.showFormErrors(currentForm, data.errors);
                }
                currentForm.removeClass(submitClass);
                return A.needF(onSuccess).apply(this, arguments);
            },
            function () {
                currentForm.removeClass(submitClass);
                return A.needF(onError).apply(this, arguments);
            },
            function (params, form) {
                currentForm = form;
                currentForm.addClass(submitClass);
                params.push({name: "_response", type: "hidden", value: "json"});
                return A.needF(onBeforeSubmit).apply(this, arguments);
            });
    };

    A.ajaxFormControllerRaw = function (successCallback, errorCallback, beforeSubmit) {
        var controller = {
            form: null,
            beforeSubmit: function (params, $form, opts) {
                return $.isFunction(beforeSubmit) ? beforeSubmit(params, $form, opts) : true;
            },
            error: function (xhr, status, err) {
                if ($.isFunction(errorCallback)) errorCallback(xhr, status, err, controller.form);
            },
            success: function (response, status, xhr) {
                if ($.isFunction(successCallback)) successCallback(response, status, xhr, controller.form);
            }
        };
        return controller;
    };
})(jQuery);
