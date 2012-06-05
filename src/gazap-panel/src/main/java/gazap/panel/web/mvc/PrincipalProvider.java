package gazap.panel.web.mvc;

import gazap.common.web.security.PrincipalImpl;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserAcl;
import gazap.domain.entity.UserProfile;
import gazap.panel.services.UserAccess;
import gazap.panel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public abstract class PrincipalProvider {
    private AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserProfileDao userProfileDao;
    @Autowired
    protected UserAccess auth;

    protected UserDetails createPrincipal(UserProfile profile) {
        if (profile == null) {
            throw new UsernameNotFoundException("no such user");
        }

        Set<UserAcl> profileRoles = profile.getRoles();

        PrincipalImpl principal = new PrincipalImpl(profile);
        principal.setEnabled(profileRoles.contains(UserAcl.GOD_MODE));

        checker.check(principal);
        return principal;
    }

    protected UserProfile getLoggedUser() {
        return getLoggedUser(userProfileDao);
    }

    public static UserProfile getLoggedUser(UserProfileDao userProfileDao) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() instanceof PrincipalImpl
                ? userProfileDao.getUser(((PrincipalImpl) authentication.getPrincipal()).getUserId())
                : null;
    }
}
