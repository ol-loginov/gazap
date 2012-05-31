Gazap.extendNamespace('Ui', function (N, G) {
    var H = new N.DomHelper();

    N.PlainMap = function (container, width, height) {
        width = Number(width || 0);
        height = Number(height || 0);

        this.$container = H.create('div').appendTo(H.byID(container))
            .style({position:'relative', overflow:'hidden'})
            .addClass('gazap-ui-map-container');

        // init tiling
        this.$tileLayer = H.create('div').appendTo(this.$container)
            .addClass('gazap-ui-map-tile-layer')
            .style({position:'absolute', left:0, right:0, width:0, height:0, overflow:'visible'});

        // init eventually
        this.$eventLayer = H.create('div').appendTo(this.$container)
            .addClass('gazap-ui-map-events-layer')
            .style({position:'absolute', left:0, right:0, top:0, bottom:0});
        this.eventListener = new N.PlainMap.EventListener();
        this.$eventLayer.bind('mousemove mouseup mousedown', this.eventListener);

        // init custom layering
        this.layers = [];
        this.$childrenLayer = H.create('div').appendTo(this.$container)
            .addClass('gazap-ui-map-children')
            .style({position:'absolute', left:0, right:0, width:0, height:0, overflow:'hidden'});

        this.setSize({
            width:isNaN(width) ? 0 : width,
            height:isNaN(height) ? 0 : height
        });
        return this;
    };

    N.PlainMap.prototype = {
        $container:null,

        $tileLayer:null,
        $childrenLayer:null,
        $eventLayer:null,

        layers:null,

        eventListener:null,

        width:0,
        height:0,

        createLayer:function (name) {
            var layer = new N.PlainMap.Layer({name:name});
            layer.$node = H.create('div').appendTo(this.$childrenLayer)
                .addClass('gazap-ui-map-child-layer')
                .style({position:'absolute', left:0, right:0, width:0, height:0, overflow:'visible'});
            this.layers.push(layer);
            return layer;
        },

        setSize:function (size) {
            this.width = size.width;
            this.height = size.height;
            this.$container.style({width:this.width, height:this.height});
        }
    };

    N.PlainMap.Layer = function (initializer) {
        G.extend(this, initializer, ['name']);
        return this;
    };

    N.PlainMap.Layer.prototype = {
        name:null,
        $node:null
    };

    N.PlainMap.EventListener = function () {
        return this;
    };

    N.PlainMap.EventListener.prototype = {
        on_mousemove:function (e) {
            console.log('mm');
            e.preventDefault();
        },

        on_mouseup:function (e) {
            console.log('mu');
            e.preventDefault();
        },

        on_mousedown:function (e) {
            console.log('md');
            e.preventDefault();
        }
    };
});
