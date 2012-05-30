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

    createStage:function () {
        this.uiHelper = new EditControllerUiHelper();
        this.ui = new Gazap.Ui.Map('geometryCanvasOuter', 100, 100);
    },
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
