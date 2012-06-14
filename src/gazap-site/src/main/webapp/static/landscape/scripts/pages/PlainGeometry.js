PlainGeometry = {};

PlainGeometry.ContributionFactory = {
    TILE_TEMPLATE:"" +
        "<div class='change-{item.type}-{item.action}'>" +
        " <div class='author'><span class='id'>#{item.id}</span><span class='date'>{date}</span><span class='time'>{time}</span></div>" +
        " <div class='details'><strong>{text1}</strong><span class='short'>{text2}</span></div>" +
        " <div class='btn-toolbar'>" +
        "  <div class='btn-group'><button class='btn btn-info btn-mini btn-highlight'><span>{textHighlight}</span></button></div>" +
        "  <div class='btn-group'><button class='btn btn-success btn-mini btn-approve'><span>{textApprove}</span></button></div>" +
        "  <div class='btn-group'><button class='btn btn-danger btn-mini btn-reject'><span>{textReject}</span></button></div>" +
        " </div>" +
        "</div>",

    addControllerTrigger:function ($button, controller, event, arg) {
        if (typeof event === "string") {
            $button.click(function () {
                controller.trigger(event, arg);
            });
        } else {
            $button.remove();
        }
    }
};

PlainGeometry.ContributionTileAddFactory = function (rejectEvent, highlightEvent, approveEvent) {
    this.rejectEvent = rejectEvent;
    this.highlightEvent = highlightEvent;
    this.approveEvent = approveEvent;
    return this;
};

PlainGeometry.ContributionTileAddFactory.prototype = {
    rejectEvent:null,
    highlightEvent:null,
    approveEvent:null,

    create:function (controller, item) {
        controller.setCustomTile(item.scale, item.size, item.x, item.y, item.id, function (data) {
            data.src = Gazap.format('{0}/contribution/{1}/tile', controller.actionBase, item.id);
        });
        return this.createView(controller, item);
    },

    createView:function (controller, item) {
        var content = Gazap.formatTemplate(PlainGeometry.ContributionFactory.TILE_TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-add-strong'],
            text2:Gazap.format(controller.messages['change-tile-add'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject'],
            textApprove:controller.messages['change-approve'],
            textHighlight:controller.messages['change-highlight']
        });
        var $content = $(content);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-reject', $content), controller, this.rejectEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-highlight', $content), controller, this.highlightEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-approve', $content), controller, this.approveEvent, item.id);
        return $content;
    }
};

PlainGeometry.ContributionTileRemoveFactory = function (rejectEvent, highlightEvent, approveEvent) {
    this.rejectEvent = rejectEvent;
    this.highlightEvent = highlightEvent;
    this.approveEvent = approveEvent;
    return this;
};

PlainGeometry.ContributionTileRemoveFactory.prototype = {
    rejectEvent:null,
    highlightEvent:null,
    approveEvent:null,

    create:function (controller, item) {
        controller.setCustomTile(item.scale, item.size, item.x, item.y, item.id, function (data) {
            data.src = null;
        });
        return this.createView(controller, item);
    },

    createView:function (controller, item) {
        var content = Gazap.formatTemplate(PlainGeometry.ContributionFactory.TILE_TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-remove-strong'],
            text2:Gazap.format(controller.messages['change-tile-remove'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject'],
            textApprove:controller.messages['change-approve'],
            textHighlight:controller.messages['change-highlight']
        });
        var $content = $(content);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-reject', $content), controller, this.rejectEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-highlight', $content), controller, this.highlightEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-approve', $content), controller, this.approveEvent, item.id);
        return $content;
    }
};
