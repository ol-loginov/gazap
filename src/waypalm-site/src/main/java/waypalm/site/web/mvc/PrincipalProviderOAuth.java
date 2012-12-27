package waypalm.site.web.mvc;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import waypalm.common.web.model.SocialProfile;
import waypalm.domain.entity.Profile;
import waypalm.domain.entity.SocialLink;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.web.mvc.oauth.OAuthAuthenticationToken;

public class PrincipalProviderOAuth extends PrincipalProvider implements AuthenticationUserDetailsService<OAuthAuthenticationToken> {
    @Override
    public UserDetails loadUserDetails(OAuthAuthenticationToken token) {
        try {
            return createPrincipal(loadUser(token.getConnection()));
        } catch (ServiceErrorException e) {
            throw new UsernameNotFoundException("internal error", e);
        }
    }

    private Profile loadUser(Connection<?> connection) throws ServiceErrorException {
        ConnectionKey key = connection.getKey();
        SocialLink socialLink = userRepository.findSocialConnection(key.getProviderId(), key.getProviderUserId(), null);
        if (socialLink == null) {
            socialLink = userService.createSocialConnection(getLoggedUser(), key, wrap(connection));
        }
        return socialLink.getProfile();
    }

    private SocialProfile wrap(Connection<?> connection) {
        SocialProfile res = new SocialProfile();
        Object api = connection.getApi();
        if (api instanceof Facebook) {
            initialize(res, (Facebook) api);
        } else if (api instanceof Twitter) {
            initialize(res, (Twitter) api);
        }
        return res;
    }

    private void initialize(SocialProfile data, Facebook api) {
        FacebookProfile p = api.userOperations().getUserProfile();
        data.setNick(StringUtils.hasLength(p.getUsername()) ? p.getUsername() : ("fb" + p.getId()));
        data.setEmail(p.getEmail());
        data.setDisplayName(p.getName());
        data.setUrl(p.getLink());
    }

    private void initialize(SocialProfile data, Twitter api) {
        TwitterProfile p = api.userOperations().getUserProfile();
        data.setDisplayName(p.getName());
        data.setNick(p.getScreenName());
        data.setUrl(p.getProfileUrl());
    }
}
