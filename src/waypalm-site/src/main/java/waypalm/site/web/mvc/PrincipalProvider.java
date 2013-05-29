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
    private AccountStatusUserDetailsChecker checker = new AccountStatusUserDetailsChecker();

    protected UserService userService;
    protected UserRepository userRepository;
    protected UserAccess auth;

    @Inject
    public PrincipalProvider(UserService userService, UserRepository userRepository, UserAccess auth) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.auth = auth;
    }

    protected UserDetails createPrincipal(Profile profile) {
        if (profile == null) {
            throw new UsernameNotFoundException("no such user");
        }

        PrincipalImpl principal = new PrincipalImpl(profile);
        checker.check(principal);
        return principal;
    }
}
