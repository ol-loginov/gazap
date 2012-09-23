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

UI.BrandMenuController = {
    list:null,
    contentUrl:null,
    toggleMenu:function (/*String*/contentUrl) {
        $('li', this.list).removeClass('open');
        if (this.contentUrl == contentUrl) {
            this.hideMenu();
        } else {
            this.showMenu(contentUrl);
        }
    },
    showMenu:function (/*String*/contentUrl) {
        var self = this;
        $('a[href="' + contentUrl + '"]', this.list).parent('li').first().addClass('open');
        this.content.empty().append('<div class="load-wait"/>').fadeIn();
        this.contentUrl = contentUrl;
        $.get(this.addTargetParameter(this.contentUrl), function (data, status, xhr) {
            $(data).hide().appendTo(self.content.empty()).fadeIn(500);
        });
    },
    hideMenu:function () {
        this.contentUrl = null;
        this.content.fadeOut().empty();
    },
    addTargetParameter:function (/*String*/url) {
        return url + (url.indexOf('?') > 0 ? "&" : "?") + "_target=bar";
    }
};

head.ready(function () {
    var controller = UI.BrandMenuController;
    controller.list = $('#brandMenu');
    controller.content = $('#brandMenuContent');
    $('#siteMenuToggler, #accountMenuToggler').click(function () {
        controller.toggleMenu($(this).attr('href'));
        return false;
    });
});
