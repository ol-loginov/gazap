PlainGeometry = {};

PlainGeometry.ContributionTileAddFactory = function (rejectEvent) {
    this.rejectEvent = rejectEvent;
    return this;
};

PlainGeometry.ContributionTileAddFactory.prototype = {
    rejectEvent:null,

    TEMPLATE:"" +
        "<div class='change-{item.type}-{item.action}'>" +
        " <div class='author'><span class='id'>#{item.id}</span><span class='date'>{date}</span><span class='time'>{time}</span></div>" +
        " <div class='details'><strong>{text1}</strong><span class='short'>{text2}</span></div>" +
        " <div class='btn-toolbar'><div class='btn-group'><button class='btn btn-danger btn-mini btn-reject'><span>{textReject}</span></button></div></div>" +
        "</div>",

    create:function (controller, item) {
        controller.setCustomTile(item.scale, item.size, item.x, item.y, item.id, function (data) {
            data.src = Gazap.format('{0}/contribution/{1}/tile', controller.actionBase, item.id);
        });
        return this.createView(controller, item);
    },

    createView:function (controller, item) {
        var that = this;
        var content = Gazap.formatTemplate(this.TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-add-strong'],
            text2:Gazap.format(controller.messages['change-tile-add'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject']
        });
        var $content = $(content);
        $('button.btn-reject', $content).click(function () {
            controller.trigger(that.rejectEvent, item.id);
        });
        return $content;
    }
};

PlainGeometry.ContributionTileRemoveFactory = function (rejectEvent) {
    this.rejectEvent = rejectEvent;
    return this;
};

PlainGeometry.ContributionTileRemoveFactory.prototype = {
    rejectEvent:null,

    TEMPLATE:"" +
        "<div class='change-{item.type}-{item.action}'>" +
        " <div class='author'><span class='id'>#{item.id}</span><span class='date'>{date}</span><span class='time'>{time}</span></div>" +
        " <div class='details'><strong>{text1}</strong><span class='short'>{text2}</span></div>" +
        " <div class='btn-toolbar'><div class='btn-group'><button class='btn btn-danger btn-mini btn-reject'><span>{textReject}</span></button></div></div>" +
        "</div>",

    create:function (controller, item) {
        controller.setCustomTile(item.scale, item.size, item.x, item.y, item.id, function (data) {
            data.src = null;
        });
        return this.createView(controller, item);
    },

    createView:function (controller, item) {
        var that = this;
        var content = Gazap.formatTemplate(this.TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-remove-strong'],
            text2:Gazap.format(controller.messages['change-tile-remove'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject']
        });
        var $content = $(content);
        $('button.btn-reject', $content).click(function () {
            controller.trigger(that.rejectEvent, item.id);
        });
        return $content;
    }
};
