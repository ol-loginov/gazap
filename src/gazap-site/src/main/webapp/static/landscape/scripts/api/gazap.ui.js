Gazap.Ui = Gazap.extend(Gazap.Ui || {}, {});
(function (N) {
    N.DomHelper = function (node) {
        this.node = node;
    };

    function getDomNode(node) {
        if (node instanceof N.DomHelper) {
            node = node.node;
        }
        return node;
    }

    var cssNumbers = {
        "fillOpacity":true,
        "fontWeight":true,
        "lineHeight":true,
        "opacity":true,
        "orphans":true,
        "widows":true,
        "zIndex":true,
        "zoom":true
    };

    var rclass = /[\n\t\r]/g,
        rspace = /\s+/,
        rreturn = /\r/g,
        rtype = /^(?:button|input)$/i,
        rfocusable = /^(?:button|input|object|select|textarea)$/i,
        rclickable = /^a(?:rea)?$/i,
        rboolean = /^(?:autofocus|autoplay|async|checked|controls|defer|disabled|hidden|loop|multiple|open|readonly|required|scoped|selected)$/i,
        getSetAttribute = jQuery.support.getSetAttribute,
        nodeHook, boolHook, fixSpecified;

    var applyStyleValue = function (target, name, value) {
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
            if (type === "number" && !cssNumbers[name]) {
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

    var applyStyle = function (target, source) {
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
            return new N.DomHelper(document.getElementById(id));
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

        addClass:function (value) {
            var classNames, i, l, setClass, c, cl, elem = this.node;
            if (value && typeof value === "string") {
                classNames = value.split(rspace);

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
        }
    };
    return N;
})(Gazap.Ui);
