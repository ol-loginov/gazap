Gazap.requireNamespace('Gazap.Map');

Gazap.Map.Ui = function (container, width, height) {
    this.width = width;
    this.height = height;

    this.$container = $(container);
    this.$layers = [];

    return this;
};

Gazap.Map.Ui.prototype = {
    $container:null,
    $layers:null,

    width:0,
    height:0,

    addLayer:function (name) {

    },

    addMouseHandler:function (handler) {
        this.$container.mousemove(Function.delegate(handler, handler.mouseMove));
        this.$container.mouseenter(Function.delegate(handler, handler.mouseEnter));
        this.$container.mouseleave(Function.delegate(handler, handler.mouseLeave));
    }
};