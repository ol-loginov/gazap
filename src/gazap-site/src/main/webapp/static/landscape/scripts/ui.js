if (typeof(UI) == "undefined") {
    UI = {
        addTrigger:function (eventName) {
            var self = this;
            self['trigger' + eventName] = function (parameters) {
                self.trigger(eventName, parameters);
            };
            self['bind' + eventName] = function (fn) {
                self.bind(eventName, fn);
            };
            self['once' + eventName] = function (fn) {
                var onceHandler = function () {
                    fn.apply(self, arguments);
                    self.unbind(eventName, onceHandler);
                }
                self.bind(eventName, onceHandler);
            }
        },
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
    _.extend(UI, Backbone.Events);
    UI.addTrigger('InitModalLoginDialog');
    UI.addTrigger('InitModalRegisterDialog');
    UI.addTrigger('InitFastSearch');
    UI.addTrigger('LogIn');
    UI.addTrigger('ReloadCaptcha');
    UI.addTrigger('InitCaptcha');
}

function FormHelper(form) {
    this.form = form;
    this.popoverTargets = [];
    return this;
}

FormHelper.prototype = {
    form:null,
    popoverTargets:null,

    dataAxAction:function () {
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
            url:self.dataAxAction(),
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
