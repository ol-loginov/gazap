PlainGeometry.EditController = function () {
    this.customTiling = {};
    this.localChanges = {};
    this.localChangesFactory = {};
    Gazap.defineEvents(this, PlainGeometry.EditController.Events);
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

    $aimTemplate:'<div style="border:solid 2px green;margin-left:-2px;margin-top:-2px;position:absolute;cursor:pointer;" class="surface-map-aim"></div>',
    $aim:null,
    $aimTile:null,
    $aimSelectorTemplate:'<div style="border:none;position:absolute;background:transparent url(/static/img/pFFF-50.png);" class="surface-map-selector"></div>',
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
        this.ui = new Gazap.Ui.PlainMap({container:'geometryCanvasOuter', width:100, height:100, map:this.actionMap, tileSize:this.tileSize});
        this.uiTileHelper = this.ui.createLayer();
        this.ui.bind('finger.hover', Gazap.delegate(this, this.updateAimPosition));
        this.ui.bind('finger.touch', Gazap.delegate(this, this.updateAimSelectorPosition));
        this.ui.bind('finger.leave finger.enter', Gazap.delegate(this, this.updateAimSelectorVisibility));
        this.ui.bind('tile.request', Gazap.delegate(this, this.requestTileListener));

        this.createAim();

        $(window).resize(Gazap.delegate(this, this.updateMapContainer));
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
        this.setViewInfo();
    },

    setViewInfo:function () {
        var $info = $('#god-tool-surface-view-controls');
        $('.view-scale', $info).text(this.ui.viewScale);
        $('.view-center-x', $info).text(this.ui.viewCenter.x);
        $('.view-center-y', $info).text(this.ui.viewCenter.y);
    },

    createTileOperationForm:function (tile) {
        var $tileControls = $('#god-tool-surface-tile-current');
        var $form = $('<form method="post"/>').appendTo($('.tile-change-forms', $tileControls));
        $('<input type="hidden" name="x"/>').val(tile.x).appendTo($form);
        $('<input type="hidden" name="y"/>').val(tile.y).appendTo($form);
        $('<input type="hidden" name="scale"/>').val(tile.scale).appendTo($form);
        $('<input type="hidden" name="size"/>').val(tile.size).appendTo($form);
        return $form;
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
        var that = this, $container = $('#god-tool-contribution-list ul.contribution-list');
        $.each(list, function (index) {
            if (!this) {
                return;
            }
            that.localChanges[this.id] = this;
            var factoryKey = Gazap.format("{0}:{1}", this.type, this.action);
            var factory = that.localChangesFactory[factoryKey];
            if (!factory) {
                throw new Error('Support for change with key ' + factoryKey + ' is not implemented');
            }
            var $content = factory.create(that, this);
            if ($content != null) {
                $('<li/>').attr('data-contribution-id', this.id)
                    .appendTo($container)
                    .append($content);
            }
        });
        this.ui.refreshTiles();
    },

    generateTileHash:function (scale, size, x, y) {
        return Gazap.format('T:{0}:{1}:{2}:{3}', scale, size, x, y);
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
            console.log('request tile for key ' + key);
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
        var view = $('#god-tool-contribution-list ul.contribution-list li[data-contribution-id=' + item.id + ']');
        view.addClass('disabled');

        switch (item.type) {
            case 'TILE':
                var $form = $('<form class="hidden" method="post"/>')
                    .attr("action", Gazap.format("{0}/contribution/{1}/reject.ajax", sender.actionBase, item.id))
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
    initialize:function (sender, event, opts) {
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
    execute:function (sender, event, args) {
        var tile = sender.selectionTile,
            $form = sender.createTileOperationForm(tile),
            $progress = $('<span/>');

        $form
            .attr("enctype", "multipart/form-data")
            .attr("action", Gazap.format("{0}/contribution/add_tile.ajax", sender.actionBase));

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
    var controller = new PlainGeometry.EditController();
    controller.localChangesFactory['TILE:ADD'] = new PlainGeometry.ContributionTileAddFactory(PlainGeometry.EditController.Events.CONTRIBUTION_REJECT);
    controller.localChangesFactory['TILE:REMOVE'] = new PlainGeometry.ContributionTileRemoveFactory(PlainGeometry.EditController.Events.CONTRIBUTION_REJECT);

    var conReject = new ContributionRejectOperator();
    controller.bind(PlainGeometry.EditController.Events.CONTRIBUTION_REJECT, Gazap.delegate(conReject, conReject.execute));

    var selectedTileOperationView = new SelectedTileOperationView();
    var selectedTileUploadOperator = new SelectedTileUploadOperator();
    controller.bind(PlainGeometry.EditController.Events.CURRENT_TILE_CHANGED, Gazap.delegate(selectedTileOperationView, selectedTileOperationView.initialize));
    controller.bind(PlainGeometry.EditController.Events.CURRENT_TILE_UPLOAD, Gazap.delegate(selectedTileUploadOperator, selectedTileUploadOperator.execute));

    BUS.map.plain.editor.ready(controller);
});
