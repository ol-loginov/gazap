C = null;

function EditController() {
    this.location = {x:0.0, y:0.0};
    this.viewport = {w:0, h:0}
    this.surfaceStage = new Kinetic.Stage({container:this.surfaceContainer});
    this.surfaceStage.add(this.surfaceMainLayer = new Kinetic.Layer());
    this.surfaceStage.add(this.surfaceGridLayer = new Kinetic.Layer());
    this.surfaceStage.add(this.surfaceMouseLayer = new Kinetic.Layer({draggable:true}));

    this.$surfaceContainer = $('#' + this.surfaceContainer);
    this.addWindowListeners();
    this.setAltitude(1000);
    this.windowResizeCallback();
    return this;
}

EditController.prototype = {
    $surfaceContainer:null,
    surfaceContainer:'geometryCanvasOuter',

    surfaceStage:null,
    surfaceMainLayer:null,
    surfaceGridLayer:null,
    surfaceMouseLayer:null,

    viewport:null,

    altitude:1000,
    location:null,

    mip:1.0,

    addWindowListeners:function () {
        $(window).resize(Function.delegate(this, this.windowResizeCallback));
        this.windowResizeCallback();
    },

    windowResizeCallback:function () {
        this.centerLayers();
        this._redrawGrid();
        this._redrawMouseReceiver();
    },

    centerLayers:function () {
        this.viewport = {w:this.$surfaceContainer.width(), h:this.$surfaceContainer.height()};
        this.surfaceStage.setSize(this.viewport.w, this.viewport.h);
        this.centerLayer(this.surfaceMainLayer);
        this.centerLayer(this.surfaceGridLayer);
    },

    centerLayer:function (layer) {
        layer.setX(this.viewport.w / 2 + .5);
        layer.setY(this.viewport.h / 2 + .5);
        layer.draw();
    },

    getWorldBounds:function () {
        var res = {left:0, top:0, right:0, bottom:0};

        res.width = this.viewport.w * this.mip;
        res.height = this.viewport.h * this.mip;

        res.xmin = -res.width / 2.0;
        res.xmax = res.width / 2.0;
        res.ymin = -res.height / 2.0;
        res.ymax = res.height / 2.0;
        return res;
    },

    worldPointToScreen:function (pt) {
        var res = { x:pt.x, y:pt.y};
        //  scale
        res.x /= this.mip;
        res.y /= -this.mip;
        return res;
    },

    setAltitude:function (value) {
        this.altitude = value;
        this._redrawMouseReceiver();
        this._redrawGrid();
    },

    _redrawMouseReceiver:function () {
        var layer = this.surfaceMouseLayer;
        layer.removeChildren();

        var rect = new Kinetic.Rect({
            draggable:true,
            x:0, y:0, width:this.viewport.w, height:this.viewport.h,
            fill:"#00D2FF",
            stroke:"black",
            strokeWidth:4
        });
        rect.on("dragmove dragstart dragstop", function (evt) {
            var e = e;
        });

        layer.add(rect);
        layer.draw();
    },

    _redrawGrid:function () {
        var wb = this.getWorldBounds();
        var gridSize = 1;
        while (100 * gridSize <= wb.width) {
            gridSize *= 10;
        }

        var layer = this.surfaceGridLayer;
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

        // render ZERO
        this.surfaceGridLayer.add(new Kinetic.Text({
            text:"0:0",
            fontSize:8, fontFamily:"Verdana", fontStyle:"bold",
            textFill:"green",
            x:0, y:0, align:"left", verticalAlign:"bottom"
        }));

        // render scale
        if (rightLast && bottomLast) {
            gridLineOpts.points = [rightLast.x - gridSize, bottomLast.y , rightLast.x, bottomLast.y];
            gridLineOpts.stroke = "black";
            gridLineOpts.dashArray = [];
            gridLineOpts.strokeWidth = 2;
            layer.add(new Kinetic.Line(gridLineOpts));

            this.surfaceGridLayer.add(new Kinetic.Text({
                text:gridSize + " meters",
                fontSize:8, fontFamily:"Verdana", fontStyle:"bold",
                textFill:"black",
                x:rightLast.x - gridSize + 2, y:bottomLast.y - 2, align:"left", verticalAlign:"bottom"
            }));
        }


        this.surfaceGridLayer.draw();
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
