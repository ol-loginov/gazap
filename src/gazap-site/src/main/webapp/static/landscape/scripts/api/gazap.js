var Gazap = (function (gazap) {
    gazap.each = $.each;

    gazap.requireNamespace = function (namespace) {
        var parts = namespace.split('.'), parent = gazap;
        if (parts.length == 0) {
            throw new Error("namespace is empty");
        }
        if (parts.length > 0 && parts[0] == 'Gazap') {
            parts = parts.slice(1);
        }

        for (var i = 0; i < parts.length; ++i) {
            if (typeof parent[parts[i]] === "undefined") {
                parent[parts[i]] = {};
            }
            parent = parent[parts[i]];
        }
        return parent;
    };

    gazap.extend = function (destination, source) {
        for (var key in source.prototype) {
            if (source.prototype.hasOwnProperty(key) && destination.prototype[key] === undefined) {
                destination.prototype[key] = source.prototype[key];
            }
        }
        return destination;
    };

    return  gazap;
})(Gazap || {});