function EditControllerDisplay($outer) {
    this.createScene($outer);
    return this;
}

EditControllerDisplay.prototype = {
    // meters per pixel
    mpp:1,

    $rootContainer:null,

    $imageContainer:null,
    $imageContainerTarget:null,

    sceneSize:function () {
        return {width:this.$rootContainer.width(), height:this.$rootContainer.height()};
    },

    createScene:function ($outer) {
        var that = this;
        this.$rootContainer = $('<div class="gazap-widget-plain-map-embedded"/>').appendTo($outer);

        var size = this.sceneSize();
        this.$imageContainer = $('<div class="gazap-widget-plain-map-image-layer"/>')
            .css({position:'absolute', left:size.width / 2, top:size.height / 2, overflow:'visible', width:100, height:100})
            .appendTo(this.$rootContainer);

        this.$rootContainer.mousemove(function (e) {
            if (that.$imageContainerTarget == null) {
                that.$imageContainerTarget = $('<div class="gazap-widget-plain-map-image-layer-target"/>')
                    .appendTo(that.$imageContainer)
                    .css({width:100, height:100, position:'absolute'});
            }
            that.$imageContainerTarget.css({left:e.pageX, top:e.pageY});
        });

        this.$rootContainer.mouseleave(function () {
            if (that.$imageContainerTarget != null) {
                that.$imageContainerTarget.remove();
                that.$imageContainerTarget = null;
            }
        });
    },

    setMpp:function (mpp) {
        this.mpp = mpp;
    }
};
