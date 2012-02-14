package gazap.site.services;

public interface FormatService {
    String getMessage(String code, Object... args);

    String pluralize(long value, String code);
}
