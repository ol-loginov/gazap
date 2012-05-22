package gazap.site.exceptions;

public class ObjectIllegalStateException extends Exception {
    public ObjectIllegalStateException(String message) {
        super(message);
    }

    public ObjectIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
