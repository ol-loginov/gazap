Gazap.extendNamespace('Ui', function (N, G) {
    var H = new N.DomHelper();

    N.PlainMap = function (container, width, height) {
        width = Number(width || 0);
        height = Number(height || 0);

        this.$container = H.create('div').appendTo(H.byID(container))
            .style({position:'relative', overflow:'hidden', background:'red'})
            .addClass('gazap-ui-map-container');

        // init eventually
        this.eventListener = new N.PlainMap.EventListener(this);
        this.$container.bind('mousemove mouseup mousedown mouseenter mouseleave', this.eventListener);

        // init tiling
        this.$tileLayer = H.create('div').appendTo(this.$container)
            .addClass('gazap-ui-map-tile-layer')
            .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});

        // init custom layering
        this.layers = [];
        this.$childrenLayer = H.create('div').appendTo(this.$container)
            .addClass('gazap-ui-map-children')
            .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});

        this.viewScale = 1;
        this.viewCenter = {x:0, y:0};
        this.setSize({width:isNaN(width) ? 0 : width, height:isNaN(height) ? 0 : height});

        G.defineEvents(this, 'finger.hover finger.down finger.up finger.touch finger.leave finger.enter');
        return this;
    };

    N.PlainMap.prototype = {
        $container:null,

        $tileLayer:null,
        $childrenLayer:null,

        layers:null,

        eventListener:null,

        width:0,
        height:0,

        viewScale:0,
        viewCenter:null,

        tileSize:200,

        createLayer:function (name) {
            var layer = new N.PlainMap.Layer({name:name});
            layer.$node = H.create('div').appendTo(this.$childrenLayer)
                .addClass('gazap-ui-map-child-layer')
                .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});
            this.layers.push(layer);
            return layer;
        },

        setSize:function (size) {
            this.width = size.width;
            this.height = size.height;

            this.$container.style({width:this.width, height:this.height});
            this.$childrenLayer.style({left:this.width / 2, top:this.height / 2});
            this.$tileLayer.style({left:this.width / 2, top:this.height / 2});
        },

        mapToClientPoint:function (pt) {
            var x1 = (pt.x + this.viewCenter.x) / this.viewScale + this.width / 2;
            var y1 = (pt.y + this.viewCenter.y) / this.viewScale - this.height / 2;
            return {x:x1, y:-y1};
        },

        clientToMapPoint:function (pt) {
            var x2 = (pt.x - this.width / 2) * this.viewScale - this.viewCenter.x;
            var y2 = (this.height / 2 - pt.y) * this.viewScale - this.viewCenter.y;
            return {x:x2, y:y2};
        },

        describeClientPointTile:function (pt) {
            var x = Math.floor(pt.x / this.tileSize) * this.viewScale;
            var y = Math.ceil(pt.y / this.tileSize) * this.viewScale;
            var clientX = x * this.tileSize;
            var clientY = -y * this.tileSize;
            return {
                size:this.tileSize,
                x:x, y:y, clientX:clientX, clientY:clientY, id:"tile " + x + "x" + y,
                src:null, hash:'T:' + this.tileSize + ':' + x + ':' + y };
        }
    };

    N.PlainMap.Layer = function (initializer) {
        G.extend(this, initializer, ['name']);
        return this;
    };

    N.PlainMap.Layer.prototype = {
        name:null,
        $node:null,

        getContent:function () {
            return this.$node.node;
        }
    };

    N.PlainMap.EventListener = function (map) {
        this.map = map;
        return this;
    };

    N.PlainMap.EventListener.prototype = {
        map:null,

        fingerPos:null,
        fingerDown:null,

        on_mousemove:function (e) {
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});
            e.preventDefault();
        },

        on_mouseup:function (e) {
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});
            e.preventDefault();
            if (this.testTouchThreshold(this.fingerDown, this.fingerPos)) {
                this.map.trigger('finger.touch', this.map, this.fingerPos);
            }
        },

        on_mouseenter:function () {
            this.map.trigger('finger.enter', this.map, this.fingerPos);
        },

        on_mouseleave:function () {
            this.map.trigger('finger.leave', this.map, this.fingerPos);
        },

        on_mousedown:function (e) {
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});
            e.preventDefault();
            this.fingerDown = this.fingerPos;
        },

        setTouchClientPoint:function (ev) {
            this.fingerPos = this.map.clientToMapPoint({x:ev.x, y:ev.y});
            this.map.trigger('finger.hover', this.map, this.fingerPos);
        },

        testTouchThreshold:function (a, b) {
            return Math.abs(a.x - b.x) < 2 && Math.abs(a.y - b.y) < 2;
        }
    };
});
