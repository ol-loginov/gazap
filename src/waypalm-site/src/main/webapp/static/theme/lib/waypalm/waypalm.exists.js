(function ($) {
    "use strict";

    $.extend($.fn, {
        exists:function () {
            return this.length > 0;
        }
    });
})(jQuery);
