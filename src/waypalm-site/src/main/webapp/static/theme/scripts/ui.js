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

head.ready(function () {
});
