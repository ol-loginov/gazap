PlainGeometry = {};

PlainGeometry.ContributionFactory = {
    TILE_TEMPLATE:"" +
        "<div class='change-{item.type}-{item.action}'>" +
        " <div class='about'><span class='id'>#{item.id}</span><span class='date'>{date}</span><span class='time'>{time}</span></div>" +
        " <div class='author'><a href='/u/{item.author}' class='username'><img width='16' height='16' alt='' src-async='http://www.gravatar.com/avatar/{item.authorGravatar}?s=16'><span>{item.authorName}</span></a></div>" +
        " <div class='details'><strong>{text1}</strong><span class='short'>{text2}</span></div>" +
        " <div class='btn-toolbar'>" +
        "  <div class='btn-group'><button class='btn btn-info btn-mini btn-highlight' title='{textHighlightTitle}'><span>{textHighlight}</span></button></div>" +
        "  <div class='btn-group'><button class='btn btn-success btn-mini btn-approve' title='{textApproveTitle}'><span>{textApprove}</span></button><button class='btn btn-danger btn-mini btn-reject' title='{textRejectTitle}'><span>{textReject}</span></button></div>" +
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
            data.src = Waypalm.format('{0}/contribution/{1}/tile', controller.actionBase, item.id);
        });
        return this.createView(controller, item);
    },

    createView:function (controller, item) {
        var content = Waypalm.formatTemplate(PlainGeometry.ContributionFactory.TILE_TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-add-strong'],
            text2:Waypalm.format(controller.messages['change-tile-add'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject'],
            textRejectTitle:controller.messages['change-reject-title'],
            textApprove:controller.messages['change-approve'],
            textApproveTitle:controller.messages['change-approve-title'],
            textHighlight:controller.messages['change-highlight'],
            textHighlightTitle:controller.messages['change-highlight-title']
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
        var content = Waypalm.formatTemplate(PlainGeometry.ContributionFactory.TILE_TEMPLATE, {
            item:item, date:new Date(item.createdAt).toLocaleDateString(), time:new Date(item.createdAt).toLocaleTimeString(),
            text1:controller.messages['change-tile-remove-strong'],
            text2:Waypalm.format(controller.messages['change-tile-remove'], item.scale, item.size, item.x, item.y),
            textReject:controller.messages['change-reject'],
            textRejectTitle:controller.messages['change-reject-title'],
            textApprove:controller.messages['change-approve'],
            textApproveTitle:controller.messages['change-approve-title'],
            textHighlight:controller.messages['change-highlight'],
            textHighlightTitle:controller.messages['change-highlight-title']
        });
        var $content = $(content);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-reject', $content), controller, this.rejectEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-highlight', $content), controller, this.highlightEvent, item.id);
        PlainGeometry.ContributionFactory.addControllerTrigger($('button.btn-approve', $content), controller, this.approveEvent, item.id);
        return $content;
    }
};
