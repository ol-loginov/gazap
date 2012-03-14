package gazap.site.validation;

import gazap.domain.dao.GameProfileDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGameProfileTitleValidator implements ConstraintValidator<UniqueGameProfileTitle, String> {
    @Autowired
    protected GameProfileDao gameProfileDao;

    @Override
    public void initialize(UniqueGameProfileTitle uniqueGameProfileTitle) {
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return title == null || null == gameProfileDao.findGameByTitle(title);
    }
}
