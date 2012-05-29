Gazap.Ui = (function (N) {
    var G = Gazap, H = N.DomHelper;

    N.Map = function (container, width, height) {
        this.width = width;
        this.height = height;

        this.$container = H('div').appendTo(H(container));
        this.$userLayers = [];

        return this;
    };

    N.Map.prototype = {
        $container:null,

        $tileLayer:null,
        $userLayers:null,
        $actionLayer:null,

        width:0,
        height:0,

        createLayer:function (name) {
            var layer = new N.Map.Layer({name:name});
            this.$userLayers.push(layer);
            return layer;
        }
    };

    N.Map.Layer = function (initializer) {
        G.extend(this, initializer);
        return this;
    };

    N.Map.Layer.prototype = {
        name:null
    };

    return N;
})(Gazap.Ui || {});
