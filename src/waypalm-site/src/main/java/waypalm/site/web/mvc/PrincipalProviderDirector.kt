package waypalm.site.web.mvc

import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.openid.OpenIDAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import javax.inject.Inject
import org.springframework.transaction.annotation.Transactional
import waypalm.domain.dao.UserRepository
import org.springframework.security.authentication.AccountStatusUserDetailsChecker
import waypalm.domain.entity.Profile
import waypalm.common.web.security.PrincipalImpl
import org.springframework.security.core.userdetails.UsernameNotFoundException
import javax.inject.Qualifier
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

public abstract class PrincipalProvider {
    var checker: AccountStatusUserDetailsChecker = AccountStatusUserDetailsChecker()

    protected fun createPrincipal(profile: Profile?): UserDetails {
        if (profile == null) {
            throw UsernameNotFoundException("no such user");
        }
        var principal = PrincipalImpl(profile);
        checker.check(principal);
        return principal;
    }
}

AuthDirector
public class PrincipalProviderDirector [Inject](
        var userRepository: UserRepository,
        var principalProviderOAuth: AuthenticationUserDetailsService<OAuthAuthenticationToken>,
        var principalProviderOpenID: AuthenticationUserDetailsService<OpenIDAuthenticationToken>
): PrincipalProvider()
, UserDetailsService
, AuthenticationUserDetailsService<AbstractAuthenticationToken>
{
    Transactional
    public override fun loadUserByUsername(username: String?): UserDetails {
        return createPrincipal(userRepository.findProfileByEmail(username))
    }

    public override fun loadUserDetails(token: AbstractAuthenticationToken?): UserDetails? {
        when(token){
            is OAuthAuthenticationToken -> return principalProviderOAuth.loadUserDetails(token as OAuthAuthenticationToken)
            is OpenIDAuthenticationToken -> return principalProviderOpenID.loadUserDetails(token as OpenIDAuthenticationToken)
            else -> return null
        }
    }
}

Retention(RetentionPolicy.RUNTIME)
Qualifier
public annotation class AuthDirector
