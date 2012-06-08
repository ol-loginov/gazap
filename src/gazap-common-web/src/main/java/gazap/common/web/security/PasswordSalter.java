package gazap.common.web.security;

import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class PasswordSalter implements SaltSource {
    private String saltBase;

    @Autowired
    private UserProfileDao userProfileDao;

    public String getSaltBase() {
        return saltBase;
    }

    public void setSaltBase(String saltBase) {
        this.saltBase = saltBase;
    }

    @Override
    public Object getSalt(UserDetails user) {
        UserProfile profile = null;
        if (user instanceof PrincipalImpl) {
            profile = userProfileDao.getUser(((PrincipalImpl) user).getUserId());
        }
        return getSalt(profile);
    }

    public String getSalt(UserProfile profile) {
        return getSaltBase() + (profile != null ? profile.getPasswordSalt() : null);
    }
}
