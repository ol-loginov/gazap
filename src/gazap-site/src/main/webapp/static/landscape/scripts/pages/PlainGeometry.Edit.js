C = null;

function EditControllerUiHelper() {
    return this;
}

EditControllerUiHelper.prototype = {
    mouseLeave:function (ev) {
    },
    mouseMove:function (ev) {
    },
    mouseEnter:function (ev) {
    }
};

function EditController() {
    this.location = {x:0.0, y:0.0};
    this.createStage();
    return this;
}

EditController.prototype = {
    ui:null,
    uiHelper:null,

    uiTileHelper:null,

    createStage:function () {
        this.uiHelper = new EditControllerUiHelper();
        this.ui = new Gazap.Ui.PlainMap('geometryCanvasOuter', 100, 100);
        this.ui.setSize({width:$('#geometryCanvasOuter').width(), height:$('#geometryCanvasOuter').height()})
        this.uiTileHelper = this.ui.createLayer();
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
