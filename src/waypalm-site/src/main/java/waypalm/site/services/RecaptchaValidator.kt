package waypalm.site.services

import org.springframework.beans.factory.annotation.Value
import javax.inject.Named
import net.tanesha.recaptcha.ReCaptchaFactory

public trait RecaptchaValidator {
    fun validate(challenge: String, response: String): Boolean
}


Named
public class RecaptchaValidatorImpl : RecaptchaValidator {
    Value("\${recaptcha.public}") var publicKey: String = ""
    Value("\${recaptcha.private}")var privateKey: String = ""
    Value("\${recaptcha.site}")var site: String = ""
    Value("\${recaptcha.enabled}") var enabled: Boolean = false

    public override fun validate(challenge: String, response: String): Boolean {
        if (!enabled) return true;
        if (challenge.isEmpty() || response.isEmpty()) {
            return false;
        }
        return ReCaptchaFactory.newReCaptcha(publicKey, privateKey, false)!!
                .checkAnswer(site, challenge, response)!!
                .isValid();
    }
}
