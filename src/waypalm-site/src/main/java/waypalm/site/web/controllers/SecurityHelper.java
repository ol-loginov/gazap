package waypalm.site.web.controllers;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import waypalm.common.web.security.PrincipalImpl;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityHelper {
    @Autowired
    protected UserRepository userRepository;

    public UserProfile requireCurrentUser() {
        UserProfile profile = getCurrentUser();
        if (profile == null) {
            throw new InsufficientAuthenticationException("require authenticated visitor");
        }
        return profile;
    }

    public UserProfile getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof PrincipalImpl) {
            return userRepository.getUser(((PrincipalImpl) principal).getUserId());
        }
        return null;
    }
}
