package gazap.site.validation;

import gazap.site.services.RecaptchaValidator;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ReCaptchaValidator implements ConstraintValidator<ReCaptcha, Object> {
    private String challengeField;
    private String responseField;

    @Autowired
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
