C = null;

function EditController() {
    this.surfaceStage = new Kinetic.Stage({container:this.surfaceContainer});
    this.surfaceStage.add(this.surfaceMainLayer = new Kinetic.Layer());
    this.surfaceStage.add(this.surfaceGridLayer = new Kinetic.Layer());

    this.$surfaceContainer = $('#' + this.surfaceContainer);
    this.addWindowListeners();
    this.setAltitude(1000);
    this.centerLayers();
    return this;
}

EditController.prototype = {
    $surfaceContainer:null,
    surfaceContainer:'geometryCanvasOuter',
    surfaceStage:null,
    surfaceMainLayer:null,
    surfaceGridLayer:null,

    altitude:1000,

    addWindowListeners:function () {
        var self = this;
        $(window).resize(function () {
            self.centerLayers();
        });
        this.centerLayers();
    },
    centerLayers:function () {
        var w = this.$surfaceContainer.width();
        var h = this.$surfaceContainer.height();
        this.surfaceStage.setSize(w, h);
        this.centerLayer(this.surfaceMainLayer, w, h);
        this.centerLayer(this.surfaceGridLayer, w, h);
    },
    centerLayer:function (layer, w, h) {
        layer.setX(w / 2 + 0.5);
        layer.setY(h / 2 + 0.5);
        layer.draw();
    },
    setAltitude:function (value) {
        this.altitude = value;
        this.surfaceStage.remove(this.surfaceGridLayer);
        this.surfaceStage.add(this.surfaceGridLayer = new Kinetic.Layer());

        this.surfaceGridLayer.add(new Kinetic.Line({
            points:[0.5, -1000, 0.5, 1000],
            dashArray:[10, 10],
            stroke:"#999999",
            strokeWidth:0,
            lineWidth:1
        }));
        this.surfaceGridLayer.draw();
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
