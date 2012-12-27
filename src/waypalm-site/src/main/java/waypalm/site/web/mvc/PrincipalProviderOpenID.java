package waypalm.site.web.mvc;

import com.iserv2.commons.lang.IservHashUtil;
import com.iserv2.commons.lang.Predicate;
import com.iserv2.commons.lang.collections.IservCollections;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.social.connect.ConnectionKey;
import waypalm.common.web.model.SocialProfile;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.SocialLink;
import waypalm.site.model.ServiceError;
import waypalm.site.model.ServiceErrorException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

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
        return IservHashUtil.md5(url.toString());
    }

    private Profile loadUser(OpenIDAuthenticationToken token) throws ServiceErrorException {
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

        SocialLink socialLink = userRepository.findSocialConnection(provider, userName, email);
        if (socialLink == null) {
            ConnectionKey socialKey = new ConnectionKey(provider, userName);
            SocialProfile socialProfile = new SocialProfile();
            socialProfile.setUrl(token.getIdentityUrl());
            socialProfile.setEmail(email);
            socialLink = userService.createSocialConnection(getLoggedUser(), socialKey, socialProfile);
        }
        return socialLink.getProfile();
    }

    private String takeFirstAttribute(List<OpenIDAttribute> attributes, final String name) {
        OpenIDAttribute attr = IservCollections.findFirst(attributes, new Predicate<OpenIDAttribute>() {
            @Override
            public boolean test(OpenIDAttribute item) {
                return name.equals(item.getName());
            }
        });
        return attr == null || attr.getCount() < 1 ? null : attr.getValues().get(0);
    }
}
