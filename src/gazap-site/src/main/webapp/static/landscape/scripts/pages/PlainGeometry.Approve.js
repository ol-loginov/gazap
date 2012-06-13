var ApprovePlainMapEvents = {
    CONTRIBUTION_HIGHLIGHT:'contribution_highlight',
    CONTRIBUTION_REJECT:'contribution_reject',
    CONTRIBUTION_INIT_VIEW:'contribution_init_view',
    CONTRIBUTION_INIT_VIEW_FOR_ADD_TILE:'contribution_init_view_for_add_tile',
    CONTRIBUTION_INIT_VIEW_FOR_REMOVE_TILE:'contribution_init_view_for_remove_tile',
    CURRENT_TILE_CHANGED:'current_tile_changed',
    CURRENT_TILE_UPLOAD:'current_tile_upload',
    CURRENT_TILE_CLEAR:'current_tile_clear'
};

function ApprovePlainMapController() {
    this.customTiling = {};
    this.localChanges = {};
    Gazap.defineEvents(this, ApprovePlainMapEvents);
    return this;
}

ApprovePlainMapController.prototype = {
    actionBase:null,
    actionMap:null,
    messages:null,

    tileSize:null,

    localChanges:null,
    localChangesFetching:false,
    localChangesAfter:null,

    start:function () {
        this.createStage();
        this.updateMapContainer();
        this.loadLocalChangesPage();
    },

    createStage:function () {
        var that = this;
        this.ui = new Gazap.Ui.PlainMap({container:'geometryCanvasOuter', width:100, height:100, map:this.actionMap, tileSize:this.tileSize});
        this.uiTileHelper = this.ui.createLayer();
//        this.ui.bind('finger.hover', Gazap.delegate(this, this.updateAimPosition));
//        this.ui.bind('finger.touch', Gazap.delegate(this, this.updateAimSelectorPosition));
//        this.ui.bind('tile.request', Gazap.delegate(this, this.requestTileListener));

        $(window).resize(Gazap.delegate(this, this.updateMapContainer));
    },

    updateMapContainer:function () {
        this.ui.setSize({width:$('#geometryCanvasOuter').width(), height:$('#geometryCanvasOuter').height()});
        this.setViewInfo();
    },

    setViewInfo:function () {
        var $info = $('#god-tool-surface-view-controls');
        $('.view-scale', $info).text(this.ui.viewScale);
        $('.view-center-x', $info).text(this.ui.viewCenter.x);
        $('.view-center-y', $info).text(this.ui.viewCenter.y);
    },

    loadLocalChangesPage:function () {
        if (this.localChangesFetching) {
            return;
        }
        try {
            this.localChangesFetching = true;
            var testTime = this.localChangesAfter || 0;

            var that = this;
            $.get(Gazap.format("{0}/contribution/changes.ajax", this.actionBase), {after:testTime})
                .success(function (data, status, xhr) {
                    if (status === "success" && data.success) {
                        that.localChangesAfter = testTime;
                        that.appendLocalChanges(data.list);
                    }
                })
                .error(function () {
                });
        } finally {
            this.localChangesFetching = false;
        }
    },

    appendLocalChanges:function (list) {
        var that = this;
        $.each(list, function (index) {
            if (!this) {
                return;
            }
            that.trigger(EditPlainMapEvents.CONTRIBUTION_INIT_VIEW, this);
        });
        this.ui.refreshTiles();
    }
};

$(window).load(function () {
    var controller = new ApprovePlainMapController();

    BUS.map.plain.editor.ready(controller);
});
