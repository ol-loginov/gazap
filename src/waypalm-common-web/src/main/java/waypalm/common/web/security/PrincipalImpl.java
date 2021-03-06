package waypalm.common.web.security;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import waypalm.domain.entity.Profile;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PrincipalImpl implements UserDetails {
    private final int userId;
    private final String userName;
    private final String userPass;
    private final String userPassSalt;

    private final Set<GrantedAuthority> authorities = new HashSet<>();

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled;

    public PrincipalImpl(Profile profile) {
        this.enabled = true;

        if (profile != null) {
            this.userId = profile.getId();
            this.userPass = profile.getPassword();
            this.userPassSalt = profile.getPasswordSalt();
            this.userName = profile.getEmail();
        } else {
            this.userId = 0;
            this.userName = null;
            this.userPass = null;
            this.userPassSalt = null;
        }
    }

    public String getUsername() {
        return userName;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPass;
    }

    public String getPasswordSalt() {
        return userPassSalt;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public boolean isValid() {
        return userId > 0;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("userId", userId)
                .append("userName", userName)
                .append("authorities", authorities)
                .append("enabled", enabled)
                .append("nonExpired", accountNonExpired)
                .append("nonLocked", accountNonLocked)
                .append("credNonExpired", credentialsNonExpired)
                .toString();
    }
}
