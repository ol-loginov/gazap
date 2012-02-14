package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCU;

import javax.persistence.*;

@Entity
@Table(name = "UserSocialLink")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserSocialLink extends IntegerIdentityCU {
    @Column(name = "provider", length = 32)
    private String provider;
    @Column(name = "providerUser", length = 50)
    private String providerUser;
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private UserProfile user;
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

    public UserProfile getUser() {
        return user;
    }

    public void setUser(UserProfile user) {
        this.user = user;
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
