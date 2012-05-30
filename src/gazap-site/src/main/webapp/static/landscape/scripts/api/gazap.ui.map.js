Gazap.Ui = (function (N) {
    var H = new N.DomHelper();

    N.Map = function (container, width, height) {
        width = Number(width || 0);
        height = Number(height || 0);

        this.width = isNaN(width) ? 0 : width;
        this.height = isNaN(height) ? 0 : height;

        this.$container = H.create('div').appendTo(H.byID(container))
            .style({width:this.width, height:this.height})
            .addClass('gazap-map-ui-container');
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
