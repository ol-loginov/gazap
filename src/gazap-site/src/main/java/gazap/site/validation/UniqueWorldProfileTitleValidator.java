package gazap.site.validation;

import gazap.domain.dao.WorldDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueWorldProfileTitleValidator implements ConstraintValidator<UniqueWorldProfileTitle, String> {
    @Autowired
    protected WorldDao worldDao;

    @Override
    public void initialize(UniqueWorldProfileTitle uniqueWorldProfileTitle) {
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return title == null || null == worldDao.findWorldByTitle(title);
    }
}
