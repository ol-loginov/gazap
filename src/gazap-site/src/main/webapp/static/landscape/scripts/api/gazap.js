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

    T.ListenerChain = function () {
        this.first = {next:null, callback:null, skip:false};
        this.last = this.first;
        return this;
    };

    T.ListenerChain.prototype = {
        first:null,
        last:null,
        length:0,

        running:false,

        add:function (callback) {
            if (!T.isFunction(callback)) {
                return;
            }
            this.last = this.last.next = {next:null, callback:callback, skip:false};
            this.length++;
        },

        trigger:function (_this, name, event) {
            try {
                this.running = true;
                var current = this.first;
                while (current != null) {
                    if (current.callback != null && !current.skip) {
                        current.callback.call(_this, _this, name, event);
                    }
                    current = current.next;
                }
            } finally {
                this.running = false;
                this.cleanup();
            }
        },

        cleanup:function () {
            var current = this.first;
            var previous = current;
            while (current != null) {
                if (current.skip) {
                    previous.next = current.next;
                } else {
                    previous = current;
                }
                current = current.next;
            }
        },

        remove:function (callback) {
            if (!T.isFunction(callback)) {
                return;
            }
            var current = this.first;
            while (current != null) {
                if (current.callback === callback) {
                    current.skip = true;
                }
                current = current.next;
            }

            if (!this.running) {
                this.cleanup();
            }
        }
    };

    T.defineEvents = function (dispatcher, names) {
        if (!dispatcher._events) {
            dispatcher._events = {};
        }

        T.each(names.split(/\s+/g), function (index, name) {
            dispatcher._events[name] = new T.ListenerChain();
        });

        dispatcher.trigger = function (name, _this, value) {
            var chain = dispatcher._events[name];
            if (chain == undefined) {
                throw new Error('event listener named "' + name + '" is not defined');
            }
            chain.trigger.call(chain, _this, name, value);
        };

        dispatcher.bind = function (names, listener) {
            T.each(names.split(/\s+/g), function (index, name) {
                var chain = dispatcher._events[name];
                if (chain == undefined) {
                    throw new Error('event listener named "' + name + '" is not defined');
                }
                chain.add(listener);
            });
        };

        dispatcher.unbind = function (names, listener) {
            T.each(names.split(/\s+/g), function (index, name) {
                var chain = dispatcher._events[name];
                if (chain == undefined) {
                    throw new Error('event listener named "' + name + '" is not defined');
                }
                chain.remove(listener);
            });
        };
    };

    return  T;
})(Gazap || {});