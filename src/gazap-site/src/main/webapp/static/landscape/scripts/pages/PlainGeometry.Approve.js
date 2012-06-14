PlainGeometry.ApproveController = function () {
    this.customTiling = {};
    this.localChanges = {};
    this.localChangesFactory = {};
    Gazap.defineEvents(this, PlainGeometry.ApproveController.Events);
    return this;
};

PlainGeometry.ApproveController.Events = {
    CONTRIBUTION_HIGHLIGHT:'contribution_highlight',
    CONTRIBUTION_REJECT:'contribution_reject',
    CONTRIBUTION_APPROVE:'contribution_approve',
    CURRENT_TILE_CHANGED:'current_tile_changed',
    CURRENT_TILE_UPLOAD:'current_tile_upload',
    CURRENT_TILE_CLEAR:'current_tile_clear'
};

PlainGeometry.ApproveController.prototype = {
    actionBase:null,
    actionMap:null,
    messages:null,

    tileSize:null,

    localChangesFactory:null,
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
        this.ui = new Gazap.Ui.PlainMap({container:'geometryCanvasOuter', width:100, height:100, map:this.actionMap, tileSize:this.tileSize, tileServer:this.tileServer});
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

    generateTileHash:function (scale, size, x, y) {
        return Gazap.format('T:{0}:{1}:{2}:{3}', scale, size, x, y);
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
    }
};

PlainGeometry.ApproveController.RejectOperator = function () {
};

PlainGeometry.ApproveController.RejectOperator.prototype = {
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
                    sender.trigger(PlainGeometry.ApproveController.Events.CURRENT_TILE_CHANGED);
                    sender.setCustomTile(item.scale, item.size, item.x, item.y, id, null);
                    sender.ui.refreshTiles();
                };

                $form.ajaxSubmit({success:formSubmitSuccess, error:formSubmitFailure});
                break;
        }
    }
};

$(window).load(function () {
    var ControllerClass = PlainGeometry.ApproveController;
    var controller = new ControllerClass();
    controller.localChangesFactory['TILE:ADD'] = new PlainGeometry.ContributionTileAddFactory(ControllerClass.Events.CONTRIBUTION_REJECT, ControllerClass.Events.CONTRIBUTION_HIGHLIGHT, ControllerClass.Events.CONTRIBUTION_APPROVE);
    controller.localChangesFactory['TILE:REMOVE'] = new PlainGeometry.ContributionTileRemoveFactory(ControllerClass.Events.CONTRIBUTION_REJECT, ControllerClass.Events.CONTRIBUTION_HIGHLIGHT, ControllerClass.Events.CONTRIBUTION_APPROVE);

    var conReject = new PlainGeometry.ApproveController.RejectOperator();
    controller.bind(ControllerClass.Events.CONTRIBUTION_REJECT, Gazap.delegate(conReject, conReject.execute));

    BUS.map.plain.editor.ready(controller);
});
