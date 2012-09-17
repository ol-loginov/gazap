package waypalm.site.model;

import java.io.Serializable;

public class ServiceErrorException extends Exception {
    private final ServiceError error;
    private final Serializable extra;

    public ServiceErrorException(ServiceError error) {
        this(error, null);
    }

    public ServiceErrorException(ServiceError error, Throwable cause) {
        this(error, cause, null);
    }

    public ServiceErrorException(ServiceError error, Throwable cause, Serializable extra) {
        super(error.name(), cause);
        this.error = error;
        this.extra = extra;
    }

    public ServiceError getError() {
        return error;
    }

    public Serializable getExtra() {
        return extra;
    }
}
