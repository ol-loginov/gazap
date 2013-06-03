package waypalm.site.web.mvc.oauth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.GrantedAuthority
import org.springframework.social.connect.Connection

public class OAuthAuthenticationToken(
        var principal: UserDetails? = null,
        authorities: Collection<GrantedAuthority?>? = null,
        var connection: Connection<*>? = null
)
: AbstractAuthenticationToken(authorities)
{
    {
        setAuthenticated(principal != null)
    }

    public override fun getPrincipal(): Any? {
        return principal
    }

    public override fun getCredentials(): Any? {
        return null
    }
}