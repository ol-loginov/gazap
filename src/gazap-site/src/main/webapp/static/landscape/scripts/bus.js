if (typeof(BUS) == "undefined") {
    function BusImpl(name, parent) {
        this._name = name;
        this._parent = parent;
        if (this._parent == null) {
            _.extend(this, Backbone.Events);
        }
    }

    BusImpl.prototype = {
        _parent:null,
        _name:null,
        bus:function (name) {
            var parts = name.split('.');
            var next = this;
            for (var i = 0; i < parts.length; ++i) {
                var part = parts[i];
                next._assert_field_is_bus(part);
                next = next[part] || (next[part] = new BusImpl(part, next));
            }
            return next;
        },
        _get_bus_root:function () {
            return this._parent == null ? this : this._parent._get_bus_root();
        },
        _get_bus_path:function () {
            var path = "", current_bus = this;
            while (current_bus != null) {
                path = "." + current_bus._name + path;
                current_bus = current_bus._parent;
            }
            return path;
        },
        define:function (name) {
            this._assert_field_is_undefined(name);
            var self = this;
            self [name] = function (arg) {
                var event_path = self._get_bus_path() + ":" + name , event_dispatcher = self._get_bus_root();
                if (arg instanceof Function) {
                    event_dispatcher.bind(event_path, arg);
                } else {
                    event_dispatcher.trigger(event_path, arg);
                }
            };
            return this;
        },
        _assert_field_is_bus:function (name) {
            if (this[name] && !(this[name] instanceof BusImpl)) {
                throw new Error("field " + name + " is not bus");
            }
        },
        _assert_field_is_undefined:function (name) {
            if (this[name]) {
                throw new Error("field " + name + " is already defined");
            }
        }
    };

    BUS = new BusImpl("", null);

    BUS.bus('account.modal_login_dialog')
        .define('init')
        .define('close');

    BUS.bus('account.modal_register_dialog')
        .define('init');

    BUS.bus('account')
        .define("after_login")
        .define('world_added');

    BUS.bus('captcha')
        .define('init')
        .define('reload');

    BUS.bus("world.modal_create_dialog")
        .define("init")
        .define("close");

    BUS.bus("map.modal_create_dialog")
        .define("init")
        .define("close");

    BUS.bus('map.plain.editor')
        .define('ready');
}

if (!Function.delegate) {
    Function.delegate = function (self, func) {
        if (func) {
            return function () {
                return func.apply(self, arguments);
            };
        }
        throw new Error("delegate has no target function");
    }
}

function ActionList() {
    this.root = {id:null, next:null};
    return this;
}

ActionList.prototype = {
    root:null,

    empty:function () {
        return this.root.next == null;
    },
    remove:function (id) {
        if (this.empty()) {
            return false;
        }
        var current = this.root, previous = current;
        while (current != null) {
            if (current.id == id) {
                previous.next = current.next;
                return true;
            }
            previous = current;
            current = current.next;
        }
    },
    addAction:function (id, action) {
        this.getLast().next = {id:id, action:action, next:null};
    },
    getLast:function () {
        var last = this.root;
        while (last.next != null) {
            last = last.next;
        }
        return last;
    },
    execute:function () {
        var last = this.root;
        while (last != null) {
            if (last.action) {
                last.action.apply(null, arguments);
            }
            last = last.next;
        }
    }
};
