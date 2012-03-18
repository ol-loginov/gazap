package gazap.site.validation;

import gazap.domain.dao.GameDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGameProfileTitleValidator implements ConstraintValidator<UniqueGameProfileTitle, String> {
    @Autowired
    protected GameDao gameDao;

    @Override
    public void initialize(UniqueGameProfileTitle uniqueGameProfileTitle) {
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return title == null || null == gameDao.findGameByTitle(title);
    }
}
