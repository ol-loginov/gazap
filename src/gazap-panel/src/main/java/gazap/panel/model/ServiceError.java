package gazap.panel.model;

public enum ServiceError {
    INTERNAL_ERROR("E001"),

    AUTH_BAD_CREDENTIALS("EA001"),
    AUTH_ACCOUNT_LOCKED("EA002"),
    AUTH_WRONG_IDENTITY_URL("EA003"),

    PAGE_VALIDATION_FAILED("EP001");

    private final String code;

    private ServiceError(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
