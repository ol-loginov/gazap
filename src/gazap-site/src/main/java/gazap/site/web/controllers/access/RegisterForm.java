package gazap.site.web.controllers.access;

import gazap.domain.entity.UserProfile;
import gazap.site.validation.ReCaptcha;
import gazap.site.validation.UserEmail;
import gazap.site.validation.UserEmailRegistered;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ReCaptcha
public class RegisterForm {
    @UserEmail
    @Size(min = 1, max = UserProfile.CONTACT_EMAIL_LENGTH)
    @UserEmailRegistered(false)
    private String username;
    @NotNull
    @Size(min = 1)
    private String password;
    private String recaptcha_challenge_field;
    private String recaptcha_response_field;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRecaptcha_challenge_field(String recaptcha_challenge_field) {
        this.recaptcha_challenge_field = recaptcha_challenge_field;
    }

    public void setRecaptcha_response_field(String recaptcha_response_field) {
        this.recaptcha_response_field = recaptcha_response_field;
    }

    public String getRecaptcha_challenge_field() {
        return recaptcha_challenge_field;
    }

    public String getRecaptcha_response_field() {
        return recaptcha_response_field;
    }
}
