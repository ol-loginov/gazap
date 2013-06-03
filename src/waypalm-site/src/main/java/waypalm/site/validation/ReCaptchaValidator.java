package waypalm.site.validation;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import waypalm.site.services.RecaptchaValidator;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReCaptchaValidator implements ConstraintValidator<ReCaptcha, Object> {
    private String challengeField;
    private String responseField;

    @Inject
    private RecaptchaValidator captchaValidator;

    @Override
    public void initialize(ReCaptcha ann) {
        challengeField = ann.challengeField();
        responseField = ann.responseField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);
        String challenge = (String) beanWrapper.getPropertyValue(challengeField);
        String response = (String) beanWrapper.getPropertyValue(responseField);
        return captchaValidator.validate(challenge, response);
    }
}
