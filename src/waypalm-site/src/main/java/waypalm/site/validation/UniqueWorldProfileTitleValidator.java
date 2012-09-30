package waypalm.site.validation;

import org.springframework.beans.factory.annotation.Autowired;
import waypalm.domain.dao.WorldRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueWorldProfileTitleValidator implements ConstraintValidator<UniqueWorldProfileTitle, String> {
    @Autowired
    WorldRepository worldRepository;
    private boolean nullIsValid;

    @Override
    public void initialize(UniqueWorldProfileTitle annotation) {
        nullIsValid = annotation.nullIsValid();
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null) {
            return nullIsValid;
        }
        return null == worldRepository.findWorldByTitle(title);
    }
}
