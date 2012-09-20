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
    current:null,
    toggleMenu:function (menu) {
        $('li', this.list).removeClass('open');
        if (this.current == menu) {
            this.hideMenu();
        } else {
            this.showMenu(menu);
        }
    },
    showMenu:function (/*String*/menu) {
        switch (menu) {
            case 'site':
                $('li.first', this.list).addClass('open');
                break;
            case 'account':
                $('li.second', this.list).addClass('open');
                break;
        }
        this.content.fadeIn();
        this.current = menu;
    },
    hideMenu:function () {
        this.current = null;
        this.content.fadeOut();
    }
};

head.ready(function () {
    var controller = UI.BrandMenuController;
    controller.list = $('#brandMenu');
    controller.content = $('#brandMenuContent');
    $('#siteMenuToggler').click(function () {
        controller.toggleMenu('site');
    });
    $('#accountMenuToggler').click(function () {
        controller.toggleMenu('account');
    });
});
