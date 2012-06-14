Gazap.extendNamespace('Ui', function (N, G) {
    var CssNumbers = ["fillOpacity", "fontWeight", "lineHeight", "opacity", "orphans", "widows", "zIndex", "zoom"];

    N.DomHelper = function (node) {
        this.node = node;
    };

    function getDomNode(node) {
        if (node instanceof N.DomHelper) {
            node = node.node;
        }
        return node;
    }

    function applyStyleValue(target, name, value) {
        if (value != undefined) {
            var type = typeof value;

            //IS_REQ? convert relative number strings (+= or -=) to relative numbers. #7345
            if (type === "string") {
            }

            // Make sure that NaN and null values aren't set. See: #7116
            if (value == null || type === "number" && isNaN(value)) {
                return;
            }

            // If a number was passed in, add 'px' to the (except for certain CSS properties)
            if (type === "number" && CssNumbers.indexOf(name) == -1) {
                value += "px";
            }

            // If a hook was provided, use that value, otherwise just set the specified value
            // Wrapped to prevent IE from throwing errors when 'invalid' values are provided
            // Fixes bug #5509
            try {
                target[name] = value;
            } catch (e) {
            }
        }
    }

    function applyStyle(target, source) {
        for (var key in source) {
            if (source.hasOwnProperty(key)) {
                applyStyleValue(target, key, source[key]);
            }
        }
    }

    N.DomHelper.prototype = {
        node:null,

        create:function (name) {
            return new N.DomHelper(document.createElement(name));
        },

        byID:function (id) {
            var node = document.getElementById(id)
            return node != null ? new N.DomHelper(node) : null;
        },

        append:function (node) {
            this.node.appendChild(getDomNode(node));
            return this;
        },

        appendTo:function (node) {
            getDomNode(node).appendChild(this.node);
            return this;
        },

        style:function (properties) {
            applyStyle(this.node.style, properties);
            return this;
        },

        eventAdd:function (name, value) {
            this.node.addEventListener(name, value);
            return this;
        },

        attr:function (name, value) {
            if (typeof value === "undefined") {
                return this.node[name];
            } else {
                try {
                    this.node[name] = value;
                } catch (e) {
                }
                return this;
            }
        },

        addClass:function (value) {
            var classNames, setClass, c, cl, elem = getDomNode(this);
            if (value && typeof value === "string") {
                classNames = value.split(/\s+/);

                if (elem.nodeType === 1) {
                    if (!elem.className && classNames.length === 1) {
                        elem.className = value;
                    } else {
                        setClass = " " + elem.className + " ";
                        for (c = 0, cl = classNames.length; c < cl; c++) {
                            if (!~setClass.indexOf(" " + classNames[ c ] + " ")) {
                                setClass += classNames[ c ] + " ";
                            }
                        }
                        elem.className = Gazap.trim(setClass);
                    }
                }
            }
            return this;
        },

        bind:function (events, listener, useCapture) {
            if (useCapture == undefined) {
                useCapture = false;
            }
            var node = getDomNode(this);
            G.each(events.split(/\s+/), function () {
                if (this.length == 0) {
                    return;
                }
                var methodName = 'on_' + this;
                if (G.isFunction(listener)) {
                    node.addEventListener(this, listener, useCapture);
                } else if (typeof listener === "object" && listener[methodName] != undefined) {
                    node.addEventListener(this, G.delegate(listener, listener[methodName]), useCapture);
                } else {
                    throw new Error('listener is not identifiable');
                }
            });
        }
    };
});
