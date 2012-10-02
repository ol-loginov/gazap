if (typeof(UI) == "undefined") {
    UI = {
        isJsonResponse:function (xhr) {
            return /json\b/i.test(xhr.getResponseHeader("content-type"));
        }
    };
}

(function ($) {
    function addModalParameter(/*String*/href) {
        if (href.indexOf('?') > 0)
            return href + "&_target=modal";
        return href + "?_target=modal";
    }

    $('.modal-firer').live('click', function () {
        var href = $(this).attr('href');
        if (!href) {
            return true;
        }
        $.fancybox.open({type:'ajax', href:addModalParameter(href), padding:0});
        return false;
    });
    $('.modal-closer').live('click', function () {
        UI.closeModal();
        return false;
    });
})(jQuery);

head(function () {
    $.validator.setDefaults({onfocusout:false, onkeyup:false});
    $.validator.addMethod("validate-good", function (value, element) {
        return true;
    }, "");
});

(function () {
    UI.ajaxErrorsToValidationList = function (errors) {
        var res = {};
        $.each(errors, function () {
            if (this.field) {
                var trap = "";
                if (res[this.field]) {
                    trap = res[this.field] + " ";
                }
                res[this.field] = trap + this.message;
            }
        });
        return res;
    };

    UI.ajaxForm = function (/*String*/formSelector, /*Function*/success, /*Function*/failure) {
        var form = $(formSelector),
            submitter = $('input[type=submit], button.submit'),
            validator = null,
            formOptions = {
                beforeSubmit:function (arr, $form, options) {
                    validator = validator || form.validate();
                    submitter.attr('disabled', 'disabled').addClass('busy');
                },
                error:function (status, xhr) {
                    submitter.removeAttr('disabled').removeClass('busy');
                    failure();
                },
                success:function (data, status, xhr, $form) {
                    submitter.removeAttr('disabled').removeClass('busy');
                    if (!data.success) {
                        validator.form();
                        validator.showErrors(UI.ajaxErrorsToValidationList(data.errorList));
                    } else {
                        success();
                    }
                }
            };
        form
            .append($('<input type=hidden name="_response" value="json"/>'))
            .ajaxForm(formOptions);
    };
})();
