Gazap.extendNamespace('Ui', function (N, G) {
    var H = new N.DomHelper();

    N.PlainMap = function (opts) {
        var width = Number(opts.width || 0);
        var height = Number(opts.height || 0);

        this.tileServer = opts.tileServer || '';
        this.tileMap = opts.map;
        this.tileSize = opts.tileSize;

        this.$container = H.create('div').appendTo(H.byID(opts.container))
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

        G.defineEvents(this, 'finger.hover finger.down finger.up finger.touch finger.leave finger.enter tile.request');
        return this;
    };

    N.PlainMap.prototype = {
        tileSize:null,
        tileServer:null,

        $container:null,

        $tileLayer:null,
        $childrenLayer:null,

        layers:null,

        eventListener:null,

        width:0,
        height:0,

        viewScale:0,
        viewCenter:null,

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

        describeTileByMapPoint:function (pt, opts) {
            var q = this.tileSize * this.viewScale;
            var x = Math.floor(pt.x / q) * q, clientX = x;
            var y = Math.ceil(pt.y / q) * q, clientY = -y;
            return this.describeTile(x, y, this.viewScale, this.tileSize, clientX, clientY, opts);
        },

        describeTile:function (x, y, tileScale, tileSize, clientX, clientY, opts) {
            var setSrc = !opts || opts.setSrc;
            var request = {
                size:tileSize,
                scale:tileScale,
                x:x, y:y, clientX:clientX, clientY:clientY, id:"tile " + x + "x" + y,
                src:null, hash:'T:' + tileScale + ':' + tileSize + ':' + x + ':' + y };
            if (setSrc) {
                this.trigger('tile.request', request);
            }
            return request;
        },

        refreshTiles:function () {
            var q = this.tileSize * this.viewScale;

            var viewWidth = this.width * this.viewScale;
            var viewHeight = this.height * this.viewScale;

            var boundLeft = this.viewCenter.x - viewWidth / 2;
            boundLeft = Math.floor(boundLeft / q) * q;
            var boundRight = this.viewCenter.x + viewWidth / 2;
            boundRight = Math.ceil(boundRight / q) * q - q;

            var boundTop = this.viewCenter.y + viewHeight / 2;
            boundTop = Math.ceil(boundTop / q) * q;
            var boundBottom = this.viewCenter.y - viewHeight / 2;
            boundBottom = Math.floor(boundBottom / q) * q - q;

            for (var x = boundLeft; x <= boundRight; x += q) {
                for (var y = boundBottom; y <= boundTop; y += q) {
                    var request = {scale:this.viewScale, size:this.tileSize, x:x, y:y, src:null};
                    request.clientX = x / this.viewScale;
                    request.clientY = -y / this.viewScale;
                    request.src = this.tileServer + Gazap.format('/tiles/m{0}/c{1}s{2}x{3}y{4}', this.tileMap, request.scale, request.size, request.x, request.y);
                    this.trigger('tile.request', request);
                    this.refreshTile(request);
                }
            }
        },

        refreshTile:function (req) {
            var id = Gazap.format('gazap-ui-map-tile-{0}d{1}d{2}d{3}', req.scale, req.size, req.x, req.y);
            var img = H.byID(id);
            if (img == null) {
                img = H.create('img')
                    .attr('id', id)
                    .attr('alt', '')
                    .style({position:'absolute', border:'none', display:'none'})
                    .appendTo(this.$tileLayer);
            }
            var currentSrc = img.attr('src');
            if (currentSrc == req.src) {
                return;
            }

            console.log(Gazap.formatTemplate('refresh tile image src [{req.x},{req.y}] : {oldSrc} -> {newSrc}', {req:req, oldSrc:currentSrc, newSrc:req.src}));

            var callback = function () {
                img.style({left:req.clientX, top:req.clientY, width:req.size, height:req.size, display:'inherit'});
            };

            img
                .style({display:'none'})
                .eventAdd('load', callback)
                .attr('src', req.src);
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
                this.map.trigger('finger.touch', this.fingerPos);
            }
        },

        on_mouseenter:function () {
            this.map.trigger('finger.enter', this.fingerPos);
        },

        on_mouseleave:function () {
            this.map.trigger('finger.leave', this.fingerPos);
        },

        on_mousedown:function (e) {
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});
            e.preventDefault();
            this.fingerDown = this.fingerPos;
        },

        setTouchClientPoint:function (ev) {
            this.fingerPos = this.map.clientToMapPoint({x:ev.x, y:ev.y});
            this.map.trigger('finger.hover', this.fingerPos);
        },

        testTouchThreshold:function (a, b) {
            return Math.abs(a.x - b.x) < 2 && Math.abs(a.y - b.y) < 2;
        }
    };
});
