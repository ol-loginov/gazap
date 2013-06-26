var A = {};

(function ($, undefined) {
    A.needF = function (desiredFunction) {
        return $.isFunction(desiredFunction) ? desiredFunction : $.noop;
    };

    A.showFormErrors = function (form, fieldErrors) {
        form.find('[name]').each(function () {
            var field = $(this), fieldName = field.attr('name'), errorSpans = '';
            $.each(fieldErrors, function () {
                if (this.field == fieldName) {
                    errorSpans += '<span class="help-inline">' + this.message + '</span>';
                }
            });

            var fieldHasErrors = errorSpans.length > 0;
            field.closest('.control-group').toggleClass('error', fieldHasErrors);
            field.closest('.controls').find('span.help-inline').remove();
            if (fieldHasErrors) {
                field.closest('.controls').append($(errorSpans));
            }
        });
    };

    A.ajaxFormController = function (onSuccess, onError, onBeforeSubmit) {
        var submitClass = 'ajax-submit-in-progress';
        var currentForm = null;
        return A.ajaxFormControllerRaw(
            function (data) {
                if (data.errors && data.errors.fields) {
                    A.showFormErrors(currentForm, data.errors.fields);
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
