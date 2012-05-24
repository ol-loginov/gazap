C = null;

function EditController() {
    this.$surfaceOuter = $('#geometryCanvasOuter');
    this.location = {x:0.0, y:0.0};
    this.viewport = {width:0, height:0};
    this.createStage();
    this.setMagnitude(1);
    this.windowResizeCallback();
    return this;
}

EditController.prototype = {
    $surfaceOuter:null,

    surface:null,

    viewport:null,
    magnitude:1,
    location:null,

    mip:1.0,

    createStage:function () {
        this.surface = new EditControllerSurface(this.$surfaceOuter.get(0));
        $(window).resize(Function.delegate(this, this.windowResizeCallback));
    },

    windowResizeCallback:function () {
        this.viewport = {width:this.$surfaceOuter.width(), height:this.$surfaceOuter.height()};
        this.surface.resizeViewport(this.viewport);
    },

    setMagnitude:function (value) {
        this.magnitude = value;
        this.surface.setMpp(value);
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
