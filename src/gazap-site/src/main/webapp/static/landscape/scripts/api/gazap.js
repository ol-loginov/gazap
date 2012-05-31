var Gazap = (function (T) {
    T.each = $.each;
    T.trim = String.prototype.trim;

    T.extend = function (destination, source, propertyArray) {
        for (var key in source.prototype) {
            if (source.prototype.hasOwnProperty(key) && destination.prototype[key] === undefined) {
                if (propertyArray && propertyArray.indexOf(key) == -1) {
                    continue;
                }
                destination.prototype[key] = source.prototype[key];
            }
        }
        return destination;
    };

    T.isFunction = function (val) {
        return typeof val === "function";
    };

    T.extendNamespace = function () {
        var base, namespaceName, initializationFunction;
        if (arguments.length == 3) {
            base = arguments[0];
            namespaceName = arguments[1];
            initializationFunction = arguments[2];
        } else
        if (arguments.length == 2) {
            base = T;
            namespaceName = arguments[0];
            initializationFunction = arguments[1];
        } else {
            throw new Error('not enough parameters');
        }

        if (!T.isFunction(initializationFunction)) {
            throw new Error('extender is not a function');
        }

        if (typeof namespaceName !== "string") {
            throw new Error('namespace name is not a string');
        }

        var namespace = base[namespaceName];
        if (namespace == undefined) {
            namespace = {};
        }

        initializationFunction.call(namespace, namespace, base);
        base[namespaceName] = namespace;
    };

    T.delegate = function (self, func) {
        if (func) {
            return function () {
                return func.apply(self, arguments);
            };
        }
        throw new Error("delegate has no target function");
    };

    return  T;
})(Gazap || {});