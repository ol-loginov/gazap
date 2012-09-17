package gazap.site.exceptions;

import java.io.Serializable;

public class ObjectNotFoundException extends RecoverableException {
    private final Class target;
    private final Serializable id;

    public ObjectNotFoundException(Class target, Serializable id) {
        super("object not found of type " + target.getSimpleName());
        this.id = id;
        this.target = target;
    }

    public ObjectNotFoundException(String message, Throwable cause, Class target, Serializable id) {
        super("object not found of type " + target.getSimpleName(), cause);
        this.id = id;
        this.target = target;
    }

    public Serializable getId() {
        return id;
    }

    public Class getTarget() {
        return target;
    }
}
