package gazap.site.model;

public enum ServiceError {
    INTERNAL_ERROR("E001"),
    INVALID_PARAM("E002"),

    INVALID_FILE_FORMAT("F001"),
    INVALID_FILE_DIMENSION("F002"),

    ACCESS_DENIED("EA000"),
    AUTH_BAD_CREDENTIALS("EA001"),
    AUTH_ACCOUNT_LOCKED("EA002"),
    AUTH_WRONG_IDENTITY_URL("EA003"),
    AUTH_FAILED("EA004"),

    VALIDATION_FAILED("EP001"),
    VALIDATION_FAILED_FOR_PAGE("EP002");

    private final String code;

    private ServiceError(String code) {
        this.code = code;
    }

    public String code() {
        return code;
    }
}
