BUS.bus('map.plain.editor.changes')
    .define('generate_tile_add_view')
    .define('generate_tile_remove_view');

function EditPlainMapController() {
    this.customTiling = {};
    this.createStage();
    return this;
}

EditPlainMapController.prototype = {
    actionBase:null,
    messages:null,

    ui:null,
    uiTileHelper:null,

    $aimTemplate:'<div style="border:solid 2px green;margin-left:-2px;margin-top:-2px;position:absolute;cursor:pointer;" class="surface-map-aim"></div>',
    $aim:null,
    $aimTile:null,
    $aimSelectorTemplate:'<div style="border:none;position:absolute;background:transparent url(/static/img/pFFF-50.png);" class="surface-map-selector"></div>',
    $aimSelector:null,
    $aimSelectorTile:null,
    $aimSelectorEnabled:true,

    localChangesFetching:false,
    localChangesAfter:null,

    customTiling:null,

    createStage:function () {
        this.ui = new Gazap.Ui.PlainMap('geometryCanvasOuter', 100, 100);
        this.uiTileHelper = this.ui.createLayer();
        this.ui.bind('finger.hover', Gazap.delegate(this, this.updateAimPosition));
        this.ui.bind('finger.touch', Gazap.delegate(this, this.updateAimSelectorPosition));
        this.ui.bind('finger.leave finger.enter', Gazap.delegate(this, this.updateAimSelectorVisibility));
        this.ui.bind('tile.request', Gazap.delegate(this, this.requestTile));

        this.createAim();

        $(window).resize(Gazap.delegate(this, this.updateMapContainer));
        $('#god-tool-surface-tile-current-load').click(Gazap.delegate(this, this.loadTilePicture));
    },

    start:function () {
        this.updateAimPosition(this.ui, "", {x:0, y:0});
        this.updateMapContainer();
        this.loadLocalChangesPage();
    },

    createAim:function () {
        var tileSize = this.ui.tileSize;
        this.$aim = $(this.$aimTemplate)
            .css({width:tileSize, height:tileSize});

        this.$aim.appendTo($(this.uiTileHelper.getContent()));
    },

    updateAimPosition:function (sender, event, mapPoint) {
        var tile = this.ui.describeTileByMapPoint(mapPoint);
        if (this.$aimTile == null || this.$aimTile.hash != tile.hash) {
            this.$aimTile = tile;
            this.$aim.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
        }
    },

    updateAimSelectorPosition:function (sender, event, mapPoint) {
        var tile = this.ui.describeTileByMapPoint(mapPoint);
        if (this.$aimSelector == null) {
            this.$aimSelector = $(this.$aimSelectorTemplate)
                .prependTo($(this.uiTileHelper.getContent()));
        }
        if (this.$aimSelectorEnabled) {
            this.$aimSelector.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
            this.setTileInfo(tile);
        }
    },

    updateAimSelectorVisibility:function (sender, event) {
        this.$aim.toggle(event == 'finger.enter');
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

    setTileInfo:function (tile, deleteProgress) {
        this.$aimSelectorTile = tile;
        var $info = $('#god-tool-surface-tile-current').toggle(true);
        $info.toggleClass('has-tile-thumb', tile.src != null);
        $('.tile-hash', $info).text(tile.hash);
        $('.tile-thumb img', $info).attr('src', tile.src);
        $('.tile-x', $info).text(tile.x);
        $('.tile-y', $info).text(tile.y);
        $('.tile-size', $info).text(tile.size);
        if (deleteProgress == undefined || deleteProgress) {
            $('#god-tool-surface-tile-current .tile-change-messages').empty();
        }
    },

    addTileChangeProgress:function ($el) {
        $('#god-tool-surface-tile-current .tile-change-messages').prepend($('<li/>').append($el));
    },

    loadTilePicture:function () {
        var that = this, $tileControls = $('#god-tool-surface-tile-current'), tile = this.$aimSelectorTile;
        var $form = $('<form method="post" enctype="multipart/form-data" action="' + this.actionBase + '/addTile.ajax"/>').appendTo($('.tile-change-forms', $tileControls));
        var $progress = $('<span/>');

        var toggleUi = function (enabled) {
            $('.btn-toolbar', $tileControls).toggle(enabled);
            that.$aimSelectorEnabled = enabled;
        };

        var formSubmitSuccess = function (response, status, xhr, $form) {
            if (status != 'success' || response.success === false) {
                formSubmitFailure();
                return;
            }

            $form.remove();
            $progress.text(that.messages['change-tile-load-image-success']);
            that.setTileInfo(that.ui.describeTileByHash(tile.hash), false);
            toggleUi(true);
        };

        var formSubmitFailure = function () {
            $form.remove();
            $progress.text(that.messages['change-tile-load-image-failure']);
            toggleUi(true);
        };

        var fileChanged = function () {
            toggleUi(false);
            that.addTileChangeProgress($progress);
            $progress.text(that.messages['change-tile-load-image']);
            $form.ajaxSubmit({success:formSubmitSuccess, error:formSubmitFailure});
        };

        $('<input type="hidden" name="x"/>').val(tile.x).appendTo($form);
        $('<input type="hidden" name="y"/>').val(tile.y).appendTo($form);
        $('<input type="hidden" name="scale"/>').val(tile.scale).appendTo($form);
        $('<input type="hidden" name="size"/>').val(tile.size).appendTo($form);
        $('<input type="file" name="file"/>').appendTo($form)
            .change(fileChanged).focus().click();
    },

    loadLocalChangesPage:function () {
        if (this.localChangesFetching) {
            return;
        }
        try {
            this.localChangesFetching = true;
            var testTime = this.localChangesAfter || 0;

            var that = this;
            $.get(this.actionBase + "/localChanges.ajax", {after:testTime})
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
        var that = this, container = $('#god-tool-contribution-list ul.contribution-list');
        $.each(list, function (index) {
            if (!this) {
                return;
            }
            var target = this.type + ':' + this.action, $li = $('<li/>');
            switch (target) {
                case 'TILE:ADD':
                    BUS.map.plain.editor.changes.generate_tile_add_view({controller:that, container:$li, item:this});
                    break;
                case 'TILE:REMOVE':
                    BUS.map.plain.editor.changes.generate_tile_remove_view({controller:that, container:$li, item:this});
                    break;
            }
            $li.appendTo(container);
        });
        this.ui.refreshTiles();
    },

    generateTileHash:function (scale, size, x, y) {
        return Gazap.format('T:{0}:{1}:{2}:{3}', scale, size, x, y);
    },

    requestTile:function (sender, event, arg) {
        var key = this.generateTileHash(arg.scale, arg.size, arg.x, arg.y);
        if (this.customTiling[key]) {
            this.customTiling[key](arg);
        }
    }
};

ChangeTileAddView = function () {
};

ChangeTileAddView.prototype = {
    create:function (e) {
        var item = e.item, container = e.container, controller = e.controller;
        this.createView(controller, container, item);
        controller.customTiling[controller.generateTileHash(item.scale, item.size, item.x, item.y)] = function (data) {
            data.src = controller.actionBase + '/contribution/' + item.id + '/tile';
        };
    },

    createView:function (controller, container, item) {
        var $div = $('<div/>').addClass(('change-' + item.type + '-' + item.action).toLowerCase());
        $('<span class="id"/>').text('#' + item.id).appendTo($div);
        $('<strong/>').text(controller.messages['change-tile-add-strong']).appendTo($div);
        $('<span class="short"/>').text(Gazap.format(controller.messages['change-tile-add'], item.scale, item.size, item.x, item.y)).appendTo($div);
        container.append($div);
    }
};


ChangeTileRemoveView = function () {
};

ChangeTileRemoveView.prototype = {
    create:function (e) {
        var item = e.item, container = e.container, controller = e.controller;
        this.createView(controller, container, item);
        controller.customTiling[controller.generateTileHash(item.scale, item.size, item.x, item.y)] = function (data) {
            data.src = null;
        };
    },

    createView:function (controller, container, item) {
        var $div = $('<div/>').addClass(('change-' + item.type + '-' + item.action).toLowerCase());
        $('<span class="id"/>').text('#' + item.id).appendTo($div);
        $('<strong/>').text(controller.messages['change-tile-add-strong']).appendTo($div);
        $('<span class="short"/>').text(Gazap.format(controller.messages['change-tile-add'], item.scale, item.size, item.x, item.y)).appendTo($div);
        container.append($div);
    }
};

$(window).load(function () {
    var C = new EditPlainMapController();
    BUS.map.plain.editor.ready(C);
    var changeTileAddView = new ChangeTileAddView();
    BUS.map.plain.editor.changes.generate_tile_add_view(Gazap.delegate(changeTileAddView, changeTileAddView.create));
    var changeTileRemoveView = new ChangeTileRemoveView();
    BUS.map.plain.editor.changes.generate_tile_remove_view(Gazap.delegate(changeTileRemoveView, changeTileRemoveView.create));
});
