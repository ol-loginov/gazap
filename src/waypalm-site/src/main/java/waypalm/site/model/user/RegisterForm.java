package waypalm.site.model.user;

import waypalm.domain.entity.Profile;
import waypalm.site.validation.Captcha;
import waypalm.site.validation.UserEmail;
import waypalm.site.validation.UserEmailRegistered;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Captcha
public class RegisterForm {
    @NotNull(message = "{validation.RegisterForm.username.isNull}")
    @Size(min = 1, max = Profile.EMAIL_LENGTH, message = "{validation.RegisterForm.username.wrongSize}")
    @UserEmail(message = "{validation.RegisterForm.username.notEmail}")
    @UserEmailRegistered(value = false, message = "{validation.RegisterForm.username.alreadyRegistered}")
    private String username;
    @NotNull(message = "{validation.RegisterForm.password.isNull}")
    @Size(min = 1, message = "{validation.RegisterForm.password.wrongSize}")
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
