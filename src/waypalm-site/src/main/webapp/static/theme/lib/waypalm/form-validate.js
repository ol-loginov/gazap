(function ($) {
    function Validator(form) {
        this.form = form;
        this.form.attr('novalidate', 'novalidate');
        this.shown = [];
        return this;
    }

    Validator.prototype = {
        form:null,
        shown:null,

        showErrors:function (/*Array*/ errors) {
            var self = this, display = {}, remove = {};
            $.each(errors, function (key, text) {
                display[key] = text;
            });
        }
    };

    $.extend($.fn, {
        waypalmValidate:function (options) {
            if (this.length != 1) {
                return null;
            }

            var validator = $.data(this.get(0), 'waypalm-validator');
            if (validator) {
                return validator;
            }

            validator = new Validator(this);

            $.data(this.get(0), 'waypalm-validator', validator);
            return validator;
        }
    });
})(jQuery);