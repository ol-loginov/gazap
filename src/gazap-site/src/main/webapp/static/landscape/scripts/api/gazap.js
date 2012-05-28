var Gazap = (function (gazap) {
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
    return  gazap;
})(Gazap || {});