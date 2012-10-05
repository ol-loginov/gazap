(function ($) {
    "use strict";

    var DATA_KEY = 'waypalm.controller';

    function sortByDataTabName(a, b) {
        var aTab = $(a).attr('data-tab');
        var bTab = $(b).attr('data-tab');
        return aTab.localeCompare(bTab);
    }

    var defaultOptions = {
        showEffect:'slideDown',
        showDuration:250,
        hideEffect:'slideUp',
        hideDuration:250,
        debugMode:false,
        toggleMode:false,
        classActiveClicker:'active-tab',
        classActiveContent:'active-tab',
        classTabShown:'tab-show'
    };

    function Controller(/*jQuery*/target, /*Object*/options) {
        this.options = $.extend({}, defaultOptions, options);
        this.options.toggleMode |= ('true' == $(target).attr('data-toggle'));

        this.target = target;
        this.clickers = $('.mini-tab-navigator *[data-tab]', this.target).sort(sortByDataTabName).toArray();
        this.contents = $('.mini-tab-container *[data-tab]', this.target).sort(sortByDataTabName).toArray();
        this.bindHandlers();
        return this;
    }

    Controller.prototype = {
        /*Object*/options:null,
        /*Element*/ target:null,
        /*Array*/ clickers:null,
        /*Array*/ contents:null,

        /*Number*/ currentIndex:-1,

        bindHandlers:function () {
            var self = this;
            $(this.clickers).click(function () {
                self.showTab(self.clickers.indexOf(this));
                return false;
            });
        },

        pickUpState:function () {
            var self = this;
            $(this.clickers).each(function (index, item) {
                if ($(item).hasClass(self.options.classActiveClicker)) {
                    self.showTab(index);
                    return false;
                }
            });
        },

        showTab:function (/*Number*/index) {
            if (this.currentIndex == index) {
                if (this.options.toggleMode) {
                    index = -1;
                } else return;
            }
            if (this.currentIndex >= 0) {
                this._hideTab(this.currentIndex);
                this.currentIndex = -1;
            }
            this.currentIndex = index;
            if (this.currentIndex >= 0) {
                this._showTab(this.currentIndex);
            }
            this._logState();
        },

        _hideTab:function (/*Number*/index) {
            $(this.clickers[index]).removeClass(this.options.classActiveClicker);
            $(this.contents[index]).removeClass(this.options.classActiveContent)[this.options.hideEffect](this.options.hideDuration);
            $(this.target).removeClass(this.options.classTabShown);
        },

        _showTab:function (/*Number*/index) {
            $(this.clickers[index]).addClass(this.options.classActiveClicker);
            $(this.contents[index]).addClass(this.options.classActiveContent)[this.options.showEffect](this.options.showDuration);
            $(this.target).addClass(this.options.classTabShown);
        },

        _logState:function () {
            if (this.options.debugMode && console && console.log) {
                console.log($.param({currentIndex:this.currentIndex, options:this.options}));
            }
        }
    };

    $.extend($.fn, {
        miniTabs:function (/*Object*/options) {
            if (this.length == 0) {
                return null;
            }
            var controllers = [];
            $.each(this, function (key, item) {
                var target = $(item);
                var controller = target.data(DATA_KEY);
                if (!controller) {
                    target.data(DATA_KEY, controller = new Controller(target, options));
                    controller.pickUpState();
                }
                controllers.push(controller);
            });
            return controllers.length == 1 ? controllers[0] : controllers;
        }
    });
})(jQuery);