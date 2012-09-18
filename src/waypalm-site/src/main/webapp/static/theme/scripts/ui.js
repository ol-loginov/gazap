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
        },
        loadAsyncImages:function () {
            $('img[src-async]').each(function () {
                var asyncSrc = $(this).attr('src-async');
                $(this).removeAttr('src-async').attr('src', asyncSrc);
            });
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
            $(this).popover('disable');
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
            var ulSource = [];
            $.each(errorList, function () {
                var input = $('input[name=' + this.field + ']', self.form);
                if (input.attr('type') === "radio") {
                    input = $('label[for=' + this.field + ']', self.form);
                }
                if (input.exists()) {
                    self.popoverTargets.push(input);
                    input.attr('data-content', this.message).popover({template:self.getFieldPopoverTemplate()}).popover('enable');
                    input.closest('.control-group').addClass('error');
                } else {
                    ulSource.push(this.message);
                }
            });
            if (ulSource.length > 0) {
                var ul = $('<ul/>').appendTo(errorPlace);
                $.each(ulSource, function () {
                    ul.append($('<li/>').text(this));
                });
            }
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
        return '<div class="popover field-popover no-header error-popover"><div class="popover-inner"><h2 class="popover-title"></h2><div class="popover-content"><div></div></div></div></div>';
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
    $(function () {
        UI.loadAsyncImages();
    });
})(jQuery);
