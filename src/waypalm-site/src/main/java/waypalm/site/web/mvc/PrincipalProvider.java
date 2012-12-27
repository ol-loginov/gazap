package waypalm.site.web.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.Profile;
import waypalm.site.services.UserAccess;
import waypalm.site.services.UserService;

public abstract class PrincipalProvider {
    private AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    @Autowired
    protected UserService userService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
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
