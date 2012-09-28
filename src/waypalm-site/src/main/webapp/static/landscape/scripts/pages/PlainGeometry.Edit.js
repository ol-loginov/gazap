PlainGeometry.EditController = function () {
    this.customTiling = {};
    this.localChanges = {};
    this.localChangesFactory = {};

    Waypalm.defineEvents(this, PlainGeometry.EditController.Events);
    return this;
};

PlainGeometry.EditController.Events = {
    CONTRIBUTION_REJECT:'contribution_reject',
    CURRENT_TILE_CHANGED:'current_tile_changed',
    CURRENT_TILE_UPLOAD:'current_tile_upload',
    CURRENT_TILE_CLEAR:'current_tile_clear'
};

PlainGeometry.EditController.prototype = {
    actionBase:null,
    actionMap:null,
    messages:null,

    tileSize:null,

    ui:null,
    uiTileHelper:null,

    $aim:null,
    $aimTile:null,
    $aimSelector:null,

    selectionTile:null,
    selectionEnabled:true,

    localChangesFactory:null,
    localChanges:null,
    localChangesFetching:false,
    localChangesAfter:null,

    customTiling:null,

    createStage:function () {
        var that = this;
        this.ui = new Waypalm.Ui.PlainMap({container:'geometryCanvasOuter', width:100, height:100, map:this.actionMap, tileSize:this.tileSize, tileServer:this.tileServer});
        this.uiTileHelper = this.ui.createLayer();
        this.ui.bind('finger.hover', Waypalm.delegate(this, this.updateAimPosition));
        this.ui.bind('finger.touch', Waypalm.delegate(this, this.updateAimSelectorPosition));
        this.ui.bind('finger.leave finger.enter', Waypalm.delegate(this, this.updateAimSelectorVisibility));
        this.ui.bind('tile.request', Waypalm.delegate(this, this.requestTileListener));
        this.ui.bind('view.point', Waypalm.delegate(this, this.updateMapPositionStatus));

        this.createAim();

        $(window).resize(Waypalm.delegate(this, this.updateMapContainer));
        $('#god-tool-surface-tile-current-load').click(function () {
            that.trigger(PlainGeometry.EditController.Events.CURRENT_TILE_UPLOAD);
        });
        $('#god-tool-surface-tile-current-clear').click(function () {
            that.trigger(PlainGeometry.EditController.Events.CURRENT_TILE_CLEAR);
        });
    },

    start:function () {
        this.createStage();
        this.updateAimPosition(this.ui, "", {x:0, y:0});
        this.updateMapContainer();
        this.loadLocalChangesPage();
    },

    updateMapPositionStatus:function () {
        var $info = $('#god-tool-surface-view-controls');
        $('.view-scale', $info).text(this.ui.viewScale);
        $('.view-center-x', $info).text(this.ui.viewPoint.x);
        $('.view-center-y', $info).text(this.ui.viewPoint.y);
    },

    createAim:function () {
        this.$aim = $('<div style="border:solid 2px green;margin-left:-2px;margin-top:-2px;position:absolute;cursor:pointer;" class="surface-map-aim"/>')
            .css({width:this.ui.tileSize, height:this.ui.tileSize})
            .appendTo($(this.uiTileHelper.getContent()));
    },

    updateAimPosition:function (sender, event, mapPoint) {
        var tile = this.ui.describeTileByMapPoint(mapPoint, {setSrc:false});
        if (this.$aimTile == null || this.$aimTile.hash != tile.hash) {
            this.$aimTile = tile;
            this.$aim.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
        }
    },

    updateAimSelectorPosition:function (sender, event, mapPoint) {
        var tile = this.ui.describeTileByMapPoint(mapPoint);
        if (this.$aimSelector == null) {
            this.$aimSelector = $('<div style="border:none;position:absolute;background:transparent url(/static/img/pFFF-50.png);" class="surface-map-selector"/>')
                .prependTo($(this.uiTileHelper.getContent()));
        }
        if (this.selectionEnabled) {
            this.$aimSelector.css({left:tile.clientX, top:tile.clientY, width:tile.size, height:tile.size});
            this.selectionTile = tile;
            this.trigger(PlainGeometry.EditController.Events.CURRENT_TILE_CHANGED);
        }
    },

    updateAimSelectorVisibility:function (sender, event) {
        this.$aim.toggle(event == 'finger.enter');
    },

    updateMapContainer:function () {
        this.ui.setSize({width:$('#geometryCanvasOuter').width(), height:$('#geometryCanvasOuter').height()});
        this.updateMapPositionStatus();
    },

    createTileOperationForm:function (tile) {
        var template = '<form method="post"><input type="hidden" name="x" value="{x}"/><input type="hidden" name="y" value="{y}"/><input type="hidden" name="scale" value="{scale}"/><input type="hidden" name="size" value="{size}"/></form>';
        var $outer = $('#god-tool-surface-tile-current .tile-change-forms');
        return $(Waypalm.formatTemplate(template, tile)).appendTo($outer);
    },

    toggleTileUI:function (enabled) {
        $('#god-tool-surface-tile-current').toggleClass('ui-disabled', !enabled);
        this.selectionEnabled = enabled;
    },

    loadLocalChangesPage:function () {
        if (this.localChangesFetching) {
            return;
        }
        try {
            this.localChangesFetching = true;
            var testTime = this.localChangesAfter || 0;

            var that = this;
            $.get(Waypalm.format("{0}/contribution/changes.ajax", this.actionBase), {after:testTime})
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
        var that = this, $container = $('#god-tool-contribution-list ul.contribution-list');
        $.each(list, function (index) {
            if (!this) {
                return;
            }
            that.localChanges[this.id] = this;
            var factoryKey = Waypalm.format("{0}:{1}", this.type, this.action);
            var factory = that.localChangesFactory[factoryKey];
            if (!factory) {
                throw new Error(Waypalm.format('Support for change with key {0} is not implemented', factoryKey));
            }
            var $content = factory.create(that, this);
            if ($content != null) {
                $(Waypalm.formatTemplate('<li data-contribution-id="{id}"/>', this))
                    .appendTo($container)
                    .append($content);
            }
        });
        UI.loadAsyncImages();
        this.ui.refreshTiles();
    },

    generateTileHash:function (scale, size, x, y) {
        return Waypalm.format('T:{0}:{1}:{2}:{3}', scale, size, x, y);
    },

    requestTileListener:function (sender, event, arg) {
        this.applyTileCustom(arg);
    },

    setCustomTile:function (scale, size, x, y, getterId, getter) {
        var key = this.generateTileHash(scale, size, x, y);
        var custom = this.customTiling[key];
        if (getter == null) {
            if (!custom) {
                return;
            }
            custom.remove(getterId);
            if (custom.empty()) {
                delete this.customTiling[key];
            }
        } else {
            if (!custom) {
                this.customTiling[key] = custom = new ActionList();
            }
            custom.addAction(getterId, getter);
        }
    },

    applyTileCustom:function (data) {
        var key = this.generateTileHash(data.scale, data.size, data.x, data.y);
        if (this.customTiling[key]) {
            this.customTiling[key].execute(data);
        }
    },

    hasTileCustom:function (data) {
        return !!this.customTiling[this.generateTileHash(data.scale, data.size, data.x, data.y)];
    }
};

ContributionRejectOperator = function () {
};

ContributionRejectOperator.prototype = {
    execute:function (sender, event, id) {
        if (!confirm(sender.messages['change-reject-confirm'])) {
            return;
        }

        var item = sender.localChanges[id];
        var view = $(Waypalm.format('#god-tool-contribution-list ul.contribution-list li[data-contribution-id={0}]', item.id)).addClass('disabled');

        switch (item.type) {
            case 'TILE':
                var $form = $('<form class="hidden" method="post"/>')
                    .attr("action", Waypalm.format("{0}/contribution/{1}/reject.ajax", sender.actionBase, item.id))
                    .appendTo($('body'));

                var formSubmitFailure = function () {
                    $form.remove();
                };

                var formSubmitSuccess = function (response, status, xhr, $form) {
                    if (status != 'success' || response.success === false) {
                        formSubmitFailure();
                        return;
                    }

                    view.slideUp();
                    sender.selectionTile = null;
                    sender.trigger(PlainGeometry.EditController.Events.CURRENT_TILE_CHANGED);
                    sender.setCustomTile(item.scale, item.size, item.x, item.y, id, null);
                    sender.ui.refreshTiles();
                };

                $form.ajaxSubmit({success:formSubmitSuccess, error:formSubmitFailure});
                break;
        }
    }
};

SelectedTileOperationView = function () {
    return this;
};

SelectedTileOperationView.prototype = {
    initialize:function (sender) {
        var tile = sender.selectionTile, $info = $('#god-tool-surface-tile-current').toggle(tile != null);
        $info.toggleClass('has-tile-thumb', tile != null && tile.src != null);
        $info.toggleClass('has-tile-operations', tile != null && !sender.hasTileCustom(tile));
        if (tile != null) {
            $('.tile-hash', $info).text(tile.hash);
            $('.tile-thumb img', $info).attr('src', tile.src);
            $('.tile-x', $info).text(tile.x);
            $('.tile-y', $info).text(tile.y);
            $('.tile-size', $info).text(tile.size);
        }
    }
};

SelectedTileUploadOperator = function () {
};

SelectedTileUploadOperator.prototype = {
    execute:function (sender) {
        var tile = sender.selectionTile,
            $form = sender.createTileOperationForm(tile),
            $progress = $('<span/>');

        $form
            .attr("enctype", "multipart/form-data")
            .attr("action", Waypalm.format("{0}/contribution/add_tile.ajax", sender.actionBase));

        var formSubmitSuccess = function (response, status, xhr, $form) {
            if (status != 'success' || response.success === false) {
                formSubmitFailure();
                return;
            }

            $form.remove();
            $progress.text(sender.messages['change-tile-load-image-success']);
            sender.appendLocalChanges([response.contribution]);

            sender.selectionTile = sender.ui.describeTile(tile.x, tile.y, tile.scale, tile.size);
            sender.trigger(PlainGeometry.EditController.Events.CURRENT_TILE_CHANGED);
            sender.toggleTileUI(true);
            sender.ui.refreshTiles();
        };

        var formSubmitFailure = function () {
            $form.remove();
            $progress.text(sender.messages['change-tile-load-image-failure']);
            sender.toggleTileUI(true);
        };

        var fileChanged = function () {
            sender.toggleTileUI(false);
            $progress.text(sender.messages['change-tile-load-image']);
            $form.ajaxSubmit({success:formSubmitSuccess, error:formSubmitFailure});
        };

        $('<input type="file" name="file"/>').appendTo($form)
            .change(fileChanged).focus().click();
    }
};

$(window).load(function () {
    var ControllerClass = PlainGeometry.EditController;
    var controller = new ControllerClass();
    controller.localChangesFactory['TILE:ADD'] = new PlainGeometry.ContributionTileAddFactory(ControllerClass.Events.CONTRIBUTION_REJECT);
    controller.localChangesFactory['TILE:REMOVE'] = new PlainGeometry.ContributionTileRemoveFactory(ControllerClass.Events.CONTRIBUTION_REJECT);

    var conReject = new ContributionRejectOperator();
    controller.bind(ControllerClass.Events.CONTRIBUTION_REJECT, Waypalm.delegate(conReject, conReject.execute));

    var selectedTileOperationView = new SelectedTileOperationView();
    var selectedTileUploadOperator = new SelectedTileUploadOperator();
    controller.bind(ControllerClass.Events.CURRENT_TILE_CHANGED, Waypalm.delegate(selectedTileOperationView, selectedTileOperationView.initialize));
    controller.bind(ControllerClass.Events.CURRENT_TILE_UPLOAD, Waypalm.delegate(selectedTileUploadOperator, selectedTileUploadOperator.execute));

    BUS.map.plain.editor.ready(controller);
});