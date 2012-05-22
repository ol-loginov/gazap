package gazap.site.exceptions;

public class ObjectNotFoundException extends RecoverableException {
    private final Class target;
    private final String id;

    public ObjectNotFoundException(Class target, String id) {
        super("object not found of type " + target.getSimpleName());
        this.id = id;
        this.target = target;
    }

    public ObjectNotFoundException(String message, Throwable cause, Class target, String id) {
        super("object not found of type " + target.getSimpleName(), cause);
        this.id = id;
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public Class getTarget() {
        return target;
    }
}
