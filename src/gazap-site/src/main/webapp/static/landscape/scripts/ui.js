(function ($) {
    $('.modal-firer').live('click', function () {
        var href = $(this).attr('a-href');
        if (!href) {
            return true;
        }
        $.fancybox.open({type:'ajax', href:href, padding:0});
        return false;
    });
})(jQuery);

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
        }
    };
    _.extend(UI, Backbone.Events);
    UI.addTrigger('InitLoginDialogAjax');
}
