Gazap.extendNamespace('Ui', function (N, G) {
    var H = new N.DomHelper();

    N.PlainMap = function (opts) {
        G.defineEvents(this, 'finger.hover finger.down finger.up finger.touch finger.leave finger.enter tile.request');
        G.defineEvents(this, 'tile.request');
        G.defineEvents(this, 'view.point');

        var width = Number(opts.width || 0);
        var height = Number(opts.height || 0);

        this.tileServer = opts.tileServer || '';
        this.tileMap = opts.map;
        this.tileSize = opts.tileSize;

        this.$domRoot = H.create('div').appendTo(H.byID(opts.container))
            .style({position:'relative', overflow:'hidden', background:'red'})
            .addClass('gazap-ui-map-container');

        // init eventually
        this.eventListener = new N.PlainMap.EventListener(this);
        this.$domRoot.bind('mousemove mouseup mousedown mouseenter mouseleave', this.eventListener);

        // init tiling
        this.$domTiles = H.create('div').appendTo(this.$domRoot)
            .addClass('gazap-ui-map-tiles')
            .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});

        // init custom layering
        this.$domLayers = H.create('div').appendTo(this.$domRoot)
            .addClass('gazap-ui-map-layers')
            .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});
        this.layers = [];

        this.viewScale = 1;
        this.viewPoint = {x:0, y:0};
        this.setSize({width:isNaN(width) ? 0 : width, height:isNaN(height) ? 0 : height});

        return this;
    };

    N.PlainMap.prototype = {
        tileSize:null,
        tileServer:null,

        $domRoot:null,
        $domTiles:null,
        $domLayers:null,

        layers:null,

        eventListener:null,

        width:0,
        height:0,

        viewScale:0,
        viewPoint:null,

        dragNavigatable:true,

        createLayer:function (name) {
            var layer = new N.PlainMap.Layer({name:name});
            layer.$node = H.create('div').appendTo(this.$domLayers)
                .addClass('gazap-ui-map-child-layer')
                .style({position:'absolute', left:0, top:0, width:0, height:0, overflow:'visible'});
            this.layers.push(layer);
            return layer;
        },

        setSize:function (size) {
            this.width = size.width;
            this.height = size.height;

            this.$domRoot.style({width:this.width, height:this.height});
            this.$domLayers.style({left:this.width / 2, top:this.height / 2});
            this.$domTiles.style({left:this.width / 2, top:this.height / 2});

            this.setViewPoint(this.viewPoint);
        },

        setViewPoint:function (pt) {
            this.viewPoint = pt;
            this.$domLayers.style({left:this.width / 2 - this.viewPoint.x, top:this.height / 2 + this.viewPoint.y});
            this.$domTiles.style({left:this.width / 2 - this.viewPoint.x, top:this.height / 2 + this.viewPoint.y});
            this.trigger('view.point', pt);
        },

        mapToClientPoint:function (pt) {
            var x1 = (pt.x + this.viewPoint.x) / this.viewScale + this.width / 2;
            var y1 = (pt.y + this.viewPoint.y) / this.viewScale - this.height / 2;
            return {x:x1, y:-y1};
        },

        clientToMapPoint:function (pt) {
            var x2 = (pt.x - this.width / 2) * this.viewScale - this.viewPoint.x;
            var y2 = (this.height / 2 - pt.y) * this.viewScale - this.viewPoint.y;
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

            var boundLeft = this.viewPoint.x - viewWidth / 2;
            boundLeft = Math.floor(boundLeft / q) * q;
            var boundRight = this.viewPoint.x + viewWidth / 2;
            boundRight = Math.ceil(boundRight / q) * q - q;

            var boundTop = this.viewPoint.y + viewHeight / 2;
            boundTop = Math.ceil(boundTop / q) * q;
            var boundBottom = this.viewPoint.y - viewHeight / 2;
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
                    .appendTo(this.$domTiles);
            }
            var currentSrc = img.attr('src');
            if (currentSrc == req.src) {
                return;
            }

            var callback = function () {
                img.style({left:req.clientX, top:req.clientY, width:req.size, height:req.size, display:'inherit'});
            };

            img
                .style({display:'none'})
                .eventAdd('load', callback)
                .attr('src', req.src);
        },

        fingerTouch:function (clientPt) {
            this.trigger('finger.touch', this.clientToMapPoint(clientPt));
        },

        fingerEnter:function (clientPt) {
            this.trigger('finger.enter', this.clientToMapPoint(clientPt));
        },

        fingerLeave:function (clientPt) {
            this.trigger('finger.leave', this.clientToMapPoint(clientPt));
        },

        fingerHover:function (clientPt) {
            this.trigger('finger.hover', this.clientToMapPoint(clientPt));
        },

        dragStart:function (clientPt) {
            this.clientPtDrag = {x:clientPt.x, y:clientPt.y};
        },

        dragMove:function (clientPtStart, clientPtCurrent) {
            var x = this.clientPtDrag.x - (clientPtCurrent.x - clientPtStart.x) - this.viewPoint.x;
            var y = this.clientPtDrag.y - (clientPtCurrent.y - clientPtStart.y) - this.viewPoint.y;
            this.setViewPoint({x:x, y:y});
        },

        dragStop:function (clientPtStart, clientPtCurrent) {
            this.clientPtDrag = null;
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

    var MODE_DRAG = 'drag';
    var MODE_FINGER = 'finger';

    N.PlainMap.EventListener.prototype = {
        map:null,

        fingerPos:null,
        fingerDown:null,

        mode:MODE_FINGER,

        _cleanupFinger:function () {
            this.fingerDown = null;
        },

        _isFingerMode:function () {
            return this.mode == MODE_FINGER;
        },

        _isDragMode:function () {
            return this.mode == MODE_DRAG;
        },

        _canStartDrag:function () {
            return this.map.dragNavigatable && this.fingerDown != null;
        },

        _dragStart:function () {
            this.mode = MODE_DRAG;
            this.map.dragStart(this.fingerDown);
        },

        _dragMove:function () {
            if (!this._isDragMode()) return;

            this.map.dragMove(this.fingerDown, this.fingerPos);
        },

        _dragStop:function () {
            if (!this._isDragMode()) return;

            this.mode = MODE_FINGER;
            this.map.dragStop(this.fingerDown, this.fingerPos);
        },

        on_mousemove:function (e) {
            e.preventDefault();
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});

            if (this._isFingerMode() && this._canStartDrag()) {
                this._dragStart();
            }

            this._dragMove();
        },

        on_mouseup:function (e) {
            e.preventDefault();
            this.setTouchClientPoint({x:e.clientX, y:e.clientY});

            if (this._isFingerMode()) {
                this.map.fingerTouch(this.fingerPos);
            }
            if (this._isDragMode()) {
                this._dragStop();
            }
            this._cleanupFinger();
        },

        on_mouseenter:function () {
            if (this._isFingerMode()) {
                this.map.fingerEnter(this.fingerPos);
            }
        },

        on_mouseleave:function () {
            if (this._isFingerMode()) {
                this.map.fingerLeave(this.fingerPos);
            }
            if (this._isDragMode()) {
                this._dragStop();
            }
            this._cleanupFinger();
        },

        on_mousedown:function (e) {
            e.preventDefault();

            this.setTouchClientPoint({x:e.clientX, y:e.clientY});
            this.fingerDown = this.fingerPos;
        },

        setTouchClientPoint:function (ev) {
            this.fingerPos = {x:ev.x, y:ev.y};
            if (this._isFingerMode()) {
                this.map.fingerHover(this.fingerPos);
            }
        }
    };
});
