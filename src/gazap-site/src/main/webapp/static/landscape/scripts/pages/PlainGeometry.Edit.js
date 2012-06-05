C = null;

function EditController() {
    this.createStage();
    return this;
}

EditController.prototype = {
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

    createStage:function () {
        this.ui = new Gazap.Ui.PlainMap('geometryCanvasOuter', 100, 100);
        this.uiTileHelper = this.ui.createLayer();
        this.ui.bind('finger.hover', Gazap.delegate(this, this.updateAimPosition));
        this.ui.bind('finger.touch', Gazap.delegate(this, this.updateAimSelectorPosition));
        this.ui.bind('finger.leave finger.enter', Gazap.delegate(this, this.updateAimSelectorVisibility));

        this.createAim();
        this.updateAimPosition(this.ui, "", {x:0, y:0});

        this.updateMapContainer();
        $(window).resize(Gazap.delegate(this, this.updateMapContainer));

        $('#god-tool-surface-tile-current-load').click(Gazap.delegate(this, this.loadTilePicture));
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
        var that = this, $tileControls = $('#god-tool-surface-tile-current');
        var $form = $('<form method="post" enctype="multipart/form-data" action="' + this.actionBase + '/addTile.ajax"/>').appendTo($('.tile-change-forms', $tileControls));
        var $progress = $('<span/>');

        var toggleUi = function (enabled) {
            $('.btn-toolbar', $tileControls).toggle(enabled);
            that.$aimSelectorEnabled = enabled;
        };

        var formSubmitSuccess = function () {
            $form.remove();
            $progress.text(that.messages['change-tile-load-image-success']);
            that.setTileInfo(that.ui.describeTileByHash(that.$aimSelectorTile.hash), false);
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

        $('<input type="file" name="file"/>').appendTo($form)
            .change(fileChanged).focus().click();
    }
};

$(window).load(function () {
    C = new EditController();
    BUS.map.plain.editor.ready(C);
});
