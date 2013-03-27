var A = {};

(function ($, undefined) {
    A.ajaxFormController = function () {
        var submitClass = 'ajax-submit-in-progress';

        function success(response, status, xhr, form) {
            form.removeClass(submitClass);
        }

        function error(xhr, status, err, form) {
            form.removeClass(submitClass);
        }

        function beforeSubmit(params, form) {
            params.push({name: "_response", type: "hidden", value: "json"});
            form.addClass(submitClass);
        }

        return A.ajaxFormControllerRaw(success, error, beforeSubmit);
    };

    A.ajaxFormControllerRaw = function (successCallback, errorCallback, beforeSubmit) {
        var controller = {
            form: null,
            beforeSubmit: function (params, $form, opts) {
                if ($.isFunction(beforeSubmit)) beforeSubmit(params, $form, opts);
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
