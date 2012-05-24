function EditControllerSurface(outer) {
    this.viewport = {width:0, height:0};
    this.mapPoint = {x:0, y:0};

    this.stage = new Kinetic.Stage({container:outer, listening:true});
    this.stage.add(this.mapLayer = new Kinetic.Layer());
    this.stage.add(this.gridLayer = new Kinetic.Layer());
    this.stage.add(this.mouseLayer = new Kinetic.Layer());

    this.mouseReceiver = new Kinetic.Rect({x:0, y:0, width:100, height:100, fill:'red', alpha:0, draggable:true});
    this.mouseLayer.add(this.mouseReceiver);
    this.setMouseListener(new EditControllerSurface.MoveMapLookupMouseReceiver(this));
    return this;
}

EditControllerSurface.prototype = {
    // meters per pixel
    mpp:1,
    viewport:null,

    mapPoint:null,

    stage:null,
    mapLayer:null,
    gridLayer:null,
    mouseLayer:null,
    mouseReceiver:null,
    mouseProcessor:null,

    setMouseListener:function (listener) {
        var that = this;
        if (this.mouseProcessor) {
            $.each(this.mouseProcessor.callbacks, function () {
                that.mouseReceiver.off(this);
            });
        }
        if (listener) {
            listener.callbacks = {};
            listener.callbacks['dragstart'] = Function.delegate(listener, listener.onDragStart);
            listener.callbacks['dragend'] = Function.delegate(listener, listener.onDragEnd);
            listener.callbacks['dragmove'] = Function.delegate(listener, listener.onDragMove);
            $.each(listener.callbacks, function (name) {
                if (this) that.mouseReceiver.on(name, this);
            });
        }
        this.mouseProcessor = listener;
    },

    resizeViewport:function (size) {
        this.viewport.width = size.width;
        this.viewport.height = size.height;

        this.stage.setSize(size.width, size.height);

        this.mouseReceiver.setPosition(0, 0);
        this.mouseReceiver.setSize(size.width, size.height);

        this.centerLayer(this.mapLayer, size);
        this.centerLayer(this.gridLayer, size);
        this.drawGrid();
    },

    setMpp:function (mpp) {
        this.mpp = mpp;
    },

    setCenter:function (pos) {
        this.mapPoint = {x:pos.x, y:pos.y};
        this.gridLayer.setPosition(-pos.x, -pos.y);
        this.gridLayer.draw();

        console.log(this.mapPoint);
    },

    getWorldBounds:function () {
        var res = {left:0, top:0, right:0, bottom:0};

        res.width = this.viewport.width * this.mpp;
        res.height = this.viewport.height * this.mpp;

        res.xmin = -res.width / 2.0 - this.mapPoint.x;
        res.xmax = res.width / 2.0 - this.mapPoint.x;
        res.ymin = -res.height / 2.0 - this.mapPoint.y;
        res.ymax = res.height / 2.0 - this.mapPoint.y;
        return res;
    },

    worldPointToScreen:function (pt, centerOffset) {
        if (!centerOffset) {
            centerOffset = this.mapPoint;
        }

        var res = { x:pt.x, y:pt.y };
        //  scale
        res.x += centerOffset.x;
        res.x /= this.mpp;
        res.y += centerOffset.y;
        res.y /= -this.mpp;
        return res;
    },

    screenPointToWorld:function (pt, centerOffset) {
        if (!centerOffset) {
            centerOffset = this.mapPoint;
        }

        var res = { x:pt.x, y:pt.y};
        //  scale
        res.x *= this.mpp;
        res.x -= centerOffset.x;
        res.y *= -this.mpp;
        res.y -= centerOffset.y;
        return res;
    },

    centerLayer:function (layer, size) {
        layer.setX(size.width / 2 + .5);
        layer.setY(size.height / 2 + .5);
    },

    drawGrid:function () {
        var wb = this.getWorldBounds();
        var gridSize = 1;
        while (100 * gridSize <= wb.width) {
            gridSize *= 10;
        }

        var layer = this.gridLayer;
        layer.removeChildren();

        var gridLineOpts = {
            dashArray:[5, 5], stroke:"#999999",
            strokeWidth:1
        };

        var pt1, pt2, rightLast, bottomLast;
        for (var x = 0; x < wb.xmax; x += gridSize) {
            pt1 = this.worldPointToScreen({x:x, y:wb.ymin});
            rightLast = this.worldPointToScreen({x:x, y:wb.ymax});
            gridLineOpts.points = [pt1.x, pt1.y , rightLast.x, rightLast.y];
            layer.add(new Kinetic.Line(gridLineOpts));
        }
        for (x = -gridSize; x > wb.xmin; x -= gridSize) {
            pt1 = this.worldPointToScreen({x:x, y:wb.ymin});
            pt2 = this.worldPointToScreen({x:x, y:wb.ymax});
            gridLineOpts.points = [pt1.x, pt1.y , pt2.x, pt2.y];
            layer.add(new Kinetic.Line(gridLineOpts));
        }
        for (var y = 0; y < wb.ymax; y += gridSize) {
            pt1 = this.worldPointToScreen({x:wb.xmin, y:y});
            pt2 = this.worldPointToScreen({x:wb.xmax, y:y});
            gridLineOpts.points = [pt1.x, pt1.y , pt2.x, pt2.y];
            layer.add(new Kinetic.Line(gridLineOpts));
        }
        for (y = -gridSize; y > wb.ymin; y -= gridSize) {
            pt1 = this.worldPointToScreen({x:wb.xmin, y:y});
            bottomLast = this.worldPointToScreen({x:wb.xmax, y:y});
            gridLineOpts.points = [pt1.x, pt1.y , bottomLast.x, bottomLast.y];
            layer.add(new Kinetic.Line(gridLineOpts));
        }


        // render scale
        if (rightLast && bottomLast) {
            gridLineOpts.points = [rightLast.x - gridSize, bottomLast.y , rightLast.x, bottomLast.y];
            gridLineOpts.stroke = "black";
            gridLineOpts.dashArray = [];
            gridLineOpts.strokeWidth = 2;
            layer.add(new Kinetic.Line(gridLineOpts));
            layer.add(new Kinetic.Text({
                text:gridSize + " meters",
                fontSize:7, fontFamily:"Verdana", fontStyle:"bold", textFill:"black",
                x:rightLast.x - gridSize + 2, y:bottomLast.y - 2, align:"left", verticalAlign:"bottom"
            }));
        }

        // render ZERO
        layer.add(new Kinetic.Text({
            text:"0:0",
            fontSize:7, fontFamily:"Verdana", fontStyle:"bold", textFill:"green",
            x:0, y:0, align:"left", verticalAlign:"bottom"
        }));

        layer.draw();
    }
};

EditControllerSurface.MoveMapLookupMouseReceiver = function (facade) {
    this.facade = facade;
    return this;
}

EditControllerSurface.MoveMapLookupMouseReceiver.prototype = {
    facade:null,

    screenStart:null,
    worldStart:null,

    screenPoint:null,
    worldPoint:null,

    worldOrigin:null,

    onDragStart:function (ev) {
        this.worldOrigin = {x:this.facade.mapPoint.x, y:this.facade.mapPoint.y};
        this.screenStart = {x:ev.screenX, y:ev.screenY};
        this.screenPoint = {x:ev.screenX, y:ev.screenY};
        this.worldStart = this.facade.screenPointToWorld(this.screenStart, this.worldOrigin);
        this.worldPoint = this.facade.screenPointToWorld(this.screenPoint, this.worldOrigin);
    },

    onDragEnd:function (ev) {
        this.facade.mouseReceiver.setPosition(0, 0);
        this.screenStart = null;
        this.screenPoint = null;
    },

    onDragMove:function (ev) {
        this.screenPoint = {x:ev.screenX, y:ev.screenY};
        this.worldPoint = this.facade.screenPointToWorld(this.screenPoint, this.worldOrigin);

        this.facade.setCenter({
            x:this.worldOrigin.x + (this.worldPoint.x - this.worldStart.x),
            y:this.worldOrigin.y + (this.worldPoint.y - this.worldStart.y)
        });
    }
};
