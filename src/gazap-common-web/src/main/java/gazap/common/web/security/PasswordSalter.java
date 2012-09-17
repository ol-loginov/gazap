package gazap.common.web.security;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class PasswordSalter implements SaltSource {
    private String saltBase;

    public String getSaltBase() {
        return saltBase;
    }

    public void setSaltBase(String saltBase) {
        this.saltBase = saltBase;
    }

    @Override
    public Object getSalt(UserDetails user) {
        String passwordSalt = null;
        if (user instanceof PrincipalImpl) {
            passwordSalt = ((PrincipalImpl) user).getPasswordSalt();
        }
        return getSalt(passwordSalt);
    }

    public String getSalt(String passwordSalt) {
        return getSaltBase() + passwordSalt;
    }
}
