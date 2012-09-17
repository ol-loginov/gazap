package waypalm.site.web.mvc;

import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.UserProfile;
import waypalm.site.services.UserAccess;
import waypalm.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public abstract class PrincipalProvider {
    private AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected UserAccess auth;

    protected UserDetails createPrincipal(UserProfile profile) {
        if (profile == null) {
            throw new UsernameNotFoundException("no such user");
        }

        PrincipalImpl principal = new PrincipalImpl(profile);
        checker.check(principal);
        return principal;
    }

    protected UserProfile getLoggedUser() {
        return getLoggedUser(userRepository);
    }

    public static UserProfile getLoggedUser(UserRepository userRepository) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getPrincipal() instanceof PrincipalImpl
                ? userRepository.getUser(((PrincipalImpl) authentication.getPrincipal()).getUserId())
                : null;
    }
}
