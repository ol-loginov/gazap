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

    $aimTemplate:'<div style="border:solid 2px green;margin-left:-2px;margin-top:-2px;position:absolute; cursor:pointer" class="surface-map-aim"></div>',
    $aim:null,
    $aimTile:null,
    $aimSelectorTemplate:'<div style="border:none;position:absolute;background:transparent url(/static/img/pFFF-50.png);" class="surface-map-selector"></div>',
    $aimSelector:null,

    createStage:function () {
        var that = this;
        this.uiHelper = new EditControllerUiHelper();
        this.ui = new Gazap.Ui.PlainMap('geometryCanvasOuter', 100, 100);
        this.uiTileHelper = this.ui.createLayer();
        this.ui.bind('finger.hover', Gazap.delegate(this, this.updateAimPosition));
        this.ui.bind('finger.touch', Gazap.delegate(this, this.updateAimSelectorPosition));

        this.createAim();
        this.updateAimPosition(this.ui, {x:0, y:0});

        this.updateMapContainer();
        $(window).resize(Gazap.delegate(this, this.updateMapContainer));
    },

    createAim:function () {
        var tileSize = this.ui.tileSize;
        this.$aim = $(this.$aimTemplate)
            .css({width:tileSize, height:tileSize});

        this.$aim.appendTo($(this.uiTileHelper.getContent()));
    },

    updateAimPosition:function (sender, mapPoint) {
        var tile = this.ui.describeClientPointTile(mapPoint);
        if (this.$aimTile == null || this.$aimTile.hash != tile.hash) {
            this.$aimTile = tile;
            this.$aim.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
        }
    },

    updateAimSelectorPosition:function (sender, mapPoint) {
        var tile = this.ui.describeClientPointTile(mapPoint);
        if (this.$aimSelector == null) {
            this.$aimSelector = $(this.$aimSelectorTemplate)
                .prependTo($(this.uiTileHelper.getContent()));
        }
        this.$aimSelector.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
        var $info = $('#god-tool-surface-tile-current').toggle(true);
        $info.toggleClass('has-tile-thumb', tile.src != null);
        $('.tile-hash', $info).text(tile.hash);
        $('.tile-thumb', $info).attr('src', tile.src == null ? '/static/img/tan-200-' + E.language + '.png' : tile.src);
    },

    updateMapContainer:function () {
        this.ui.setSize({width:$('#geometryCanvasOuter').width(), height:$('#geometryCanvasOuter').height()})
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
