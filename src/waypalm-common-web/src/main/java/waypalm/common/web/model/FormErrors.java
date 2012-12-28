package waypalm.common.web.model;

import java.util.ArrayList;
import java.util.List;

public class FormErrors {
    private List<FieldError> fields;
    private List<GlobalError> globals;

    public List<FieldError> getFieldErrors() {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        return fields;
    }

    public List<GlobalError> getGlobalErrors() {
        if (globals == null) {
            globals = new ArrayList<>();
        }
        return globals;
    }

    public boolean hasFieldErrors() {
        return fields != null && !fields.isEmpty();
    }

    public boolean hasFieldError(String field) {
        if (!hasFieldErrors()) return false;
        for (FieldError fieldError : fields) {
            if (field.equals(fieldError.getField()))
                return true;
        }
        return false;
    }

    public String getFieldError(String field) {
        if (!hasFieldErrors()) return null;
        for (FieldError fieldError : fields) {
            if (field.equals(fieldError.getField()))
                return fieldError.getMessage();
        }
        return null;
    }

    public boolean hasGlobalErrors() {
        return globals != null && !globals.isEmpty();
    }

    public boolean isEmpty() {
        return !hasFieldErrors() && !hasGlobalErrors();
    }

    public static class GlobalError {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class FieldError extends GlobalError {
        private String field;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
