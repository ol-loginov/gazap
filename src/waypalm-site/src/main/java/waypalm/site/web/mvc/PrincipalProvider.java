package waypalm.site.web.mvc;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;
import waypalm.site.services.UserAccess;
import waypalm.site.services.UserService;

import javax.inject.Inject;

public abstract class PrincipalProvider {
    AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    @Inject
    protected UserService userService;
    @Inject
    protected UserRepository userRepository;
    @Inject
    protected UserAccess auth;

    protected UserDetails createPrincipal(Profile profile) {
        if (profile == null) {
            throw new UsernameNotFoundException("no such user");
        }

        PrincipalImpl principal = new PrincipalImpl(profile);
        checker.check(principal);
        return principal;
    }
}
