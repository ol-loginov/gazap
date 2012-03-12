package gazap.site.services.impl;

import gazap.site.services.RecaptchaValidator;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaValidatorImpl implements RecaptchaValidator {
    @Value("${recaptcha.public}")
    protected String publicKey;
    @Value("${recaptcha.private}")
    protected String privateKey;
    @Value("${recaptcha.site}")
    protected String site;

    public boolean validate(String challenge, String response) {
        ReCaptcha recaptcha = ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false);
        return recaptcha.checkAnswer(site, challenge, response).isValid();
    }
}
