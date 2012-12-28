package waypalm.site.services.impl;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import waypalm.site.services.RecaptchaValidator;

@Service
public class RecaptchaValidatorImpl implements RecaptchaValidator {
    @Value("${recaptcha.public}")
    protected String publicKey;
    @Value("${recaptcha.private}")
    protected String privateKey;
    @Value("${recaptcha.site}")
    protected String site;
    @Value("${recaptcha.enabled}")
    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean validate(String challenge, String response) {
        if (!isEnabled()) return true;
        if (!StringUtils.hasText(challenge) || !StringUtils.hasText(response)) {
            return false;
        }
        ReCaptcha recaptcha = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
        return recaptcha.checkAnswer(site, challenge, response).isValid();
    }
}
