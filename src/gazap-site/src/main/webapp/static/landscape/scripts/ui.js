if (typeof(UI) == "undefined") {
    UI = {
        isJsonResponse:function (xhr) {
            return /json\b/i.test(xhr.getResponseHeader("content-type"));
        },
        closeModal:function () {
            $.fancybox.close();
        },
        cancelModal:function () {
            $.fancybox.cancel();
        }
    };
}

function FormHelper(form) {
    this.form = form;
    this.popoverTargets = [];
    return this;
}

FormHelper.prototype = {
    form:null,
    popoverTargets:null,

    ajaxAction:function () {
        return this.form.attr('data-ax-action');
    },
    clearError:function () {
        $('.alert-error', this.form).empty().hide();
        $('.control-group', this.form).removeClass('error');
        $(this.popoverTargets).each(function () {
            $(this).popover('hide');
        });
        this.popoverTargets = [];
        return this;
    },
    setError:function (text, errorList) {
        var self = this;
        var errorPlace = $('.alert-error', self.form).empty().show();
        if (text) {
            errorPlace.text(text)
        }
        if (errorList) {
            var ul = $('<ul/>').appendTo(errorPlace);
            $.each(errorList, function () {
                var input = $('input[name=' + this.field + ']', self.form);
                if (input.exists()) {
                    self.popoverTargets.push(input);
                    input.attr('data-content', this.message).popover({template:self.getFieldPopoverTemplate()});
                    input.closest('.control-group').addClass('error');
                } else {
                    ul.append($('<li/>').text(this.message));
                }
            });
        }
        return this;
    },
    setSubmitting:function (show) {
        this.form.toggleClass("submit-in-progress", show);
    },
    ajaxForm:function (opts) {
        var self = this;
        self.form.ajaxForm(_.extend({
            url:self.ajaxAction(),
            beforeSubmit:function () {
                self.clearError();
                self.setSubmitting(true);
            },
            error:function (/*xhr, status, error*/) {
                self.setSubmitting(false);
                self.setError(E.SCE);
            }
        }, opts));
    },
    getFieldPopoverTemplate:function () {
        return '<div class="popover field-popover"><div class="popover-inner"><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>';
    },
    value:function (inputName) {
        return $('*[name=' + inputName + ']', this.form).val();
    }
};

function ButtonHelper(selector) {
    this.button = $(selector);
}

ButtonHelper.prototype = {
    button:null,
    enable:function (enabled) {
        if (enabled) {
            this.button.removeAttr('disabled').removeClass('disabled');
        } else {
            this.button.attr('disabled', 'disabled').addClass('disabled');
        }
    },
    visible:function (visibility) {
        if (visibility) {
            this.button.removeClass('hidden');
        } else {
            this.button.addClass('hidden');
        }
    },
    ajaxHref:function () {
        return this.button.attr('data-ax-href');
    }
};

(function ($) {
    $('.modal-firer').live('click', function () {
        var href = $(this).attr('data-ax-href');
        if (!href) {
            return true;
        }
        $.fancybox.open({type:'ajax', href:href, padding:0});
        return false;
    });
    $('.modal-closer').live('click', function () {
        UI.closeModal();
        return false;
    });
})(jQuery);
