package gazap.site.services;

public interface RecaptchaValidator {
    boolean validate(String challenge, String response);
}
