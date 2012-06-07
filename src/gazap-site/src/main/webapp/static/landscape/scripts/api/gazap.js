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

    T.runOnce = function (func) {
        var name = 'run_once_' + new Date().getTime() + '_' + Math.round(1000000 * Math.random());
        window[name] = function () {
            delete window[name];
            func();
        };
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

    T.defineEvents = function (host, names) {
        if (!host._dispatcher) {
            host._dispatcher = {};
        }

        var events = [];
        if (typeof names === "string") {
            T.each(names.split(/\s+/g), function (index, value) {
                events.push(value);
            });
        }
        if (typeof names === "object") {
            T.each(names, function (index, value) {
                if (typeof value === "string") {
                    events.push(value);
                }
            });
        }

        T.each(events, function (index, name) {
            host._dispatcher[name] = new T.ListenerChain();
        });

        host.trigger = function (name, value) {
            var chain = host._dispatcher[name];
            if (chain == undefined) {
                throw new Error('event listener named "' + name + '" is not defined');
            }
            chain.trigger.call(chain, host, name, value);
        };

        host.bind = function (names, listener) {
            T.each(names.split(/\s+/g), function (index, name) {
                var chain = host._dispatcher[name];
                if (chain == undefined) {
                    throw new Error('event listener named "' + name + '" is not defined');
                }
                chain.add(listener);
            });
        };

        host.unbind = function (names, listener) {
            T.each(names.split(/\s+/g), function (index, name) {
                var chain = host._dispatcher[name];
                if (chain == undefined) {
                    throw new Error('event listener named "' + name + '" is not defined');
                }
                chain.remove(listener);
            });
        };
    };

    T.format = function (string) {
        var args = arguments;
        return string.replace(/{(\d+)}/g, function (match, number) {
            return typeof args[Number(number) + 1] != 'undefined' ? args[Number(number) + 1] : match;
        });
    };

    return  T;
})(Gazap || {});