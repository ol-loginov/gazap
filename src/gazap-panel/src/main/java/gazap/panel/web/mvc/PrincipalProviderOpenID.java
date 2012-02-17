package gazap.panel.web.mvc;

import com.iserv2.commons.lang.HashUtil;
import com.iserv2.commons.lang.Tester;
import com.iserv2.commons.lang.collections.Collections;
import gazap.domain.entity.UserProfile;
import gazap.domain.entity.UserSocialLink;
import gazap.panel.model.ServiceError;
import gazap.panel.model.ServiceErrorException;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

@Service("principalProvider.OpenID")
public class PrincipalProviderOpenID extends PrincipalProvider implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        try {
            return createPrincipal(loadUser(token));
        } catch (ServiceErrorException e) {
            throw new UsernameNotFoundException("internal error", e);
        }
    }

    private String createOpenIDProvider(URL url) {
        return url.getHost().toLowerCase(Locale.ENGLISH);
    }

    private String createOpenIDUserName(URL url) {
        return HashUtil.md5(url.toString());
    }

    private UserProfile loadUser(OpenIDAuthenticationToken token) throws ServiceErrorException {
        URL url;
        try {
            url = new URL(token.getIdentityUrl());
        } catch (MalformedURLException e) {
            throw new ServiceErrorException(ServiceError.AUTH_WRONG_IDENTITY_URL);
        }

        String provider = createOpenIDProvider(url);
        String userName = createOpenIDUserName(url);
        String email = takeFirstAttribute(token.getAttributes(), "email");

        if (email == null) {
            email = takeFirstAttribute(token.getAttributes(), "axEmail");
        }

        UserSocialLink socialLink = userProfileDao.findSocialConnection(provider, userName, email);
        return socialLink == null ? null : socialLink.getUser();
    }

    private String takeFirstAttribute(List<OpenIDAttribute> attributes, final String name) {
        OpenIDAttribute attr = Collections.find(attributes, new Tester<OpenIDAttribute>() {
            @Override
            public boolean test(OpenIDAttribute item) {
                return name.equals(item.getName());
            }
        });
        return attr == null || attr.getCount() < 1 ? null : attr.getValues().get(0);
    }
}
