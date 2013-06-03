package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.IntegerIdentityCU;

import javax.persistence.*;

@Entity
@Table(name = "SocialLink")
@DynamicUpdate
public class SocialLink extends IntegerIdentityCU {
    @Column(name = "provider", length = 32)
    private String provider;
    @Column(name = "providerUser", length = 50)
    private String providerUser;
    @ManyToOne
    @JoinColumn(name = "profile", nullable = false)
    private Profile profile;
    @Column(name = "userUrl", length = 512, nullable = false)
    private String userUrl;
    @Column(name = "userEmail", length = 128)
    private String userEmail;
    @Column(name = "accessToken", length = 512)
    private String accessToken;
    @Column(name = "accessSecret", length = 512)
    private String accessSecret;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUser() {
        return providerUser;
    }

    public void setProviderUser(String providerUser) {
        this.providerUser = providerUser;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
