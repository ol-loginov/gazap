package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.CollectionOfElements;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "UserProfile")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserProfile extends IntegerIdentityCUD {
    public static final int CONTACT_EMAIL_LENGTH = 128;
    public static final int PASSWORD_LENGTH = 64;
    public static final int DISPLAY_NAME_LENGTH = 64;

    @Column(name = "systemAccount", nullable = false)
    private boolean systemAccount;
    @Column(name = "email", nullable = false, length = CONTACT_EMAIL_LENGTH)
    private String email;
    @Column(name = "password", nullable = false, length = PASSWORD_LENGTH)
    private String password;
    @Column(name = "displayName", nullable = false, length = DISPLAY_NAME_LENGTH)
    private String displayName;
    @JoinTable(name = "UserAcl", joinColumns = @JoinColumn(name = "userProfile"))
    @Column(name = "aclRole", nullable = false)
    @CollectionOfElements(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<UserAcl> roles = Collections.emptySet();

    public boolean isSystemAccount() {
        return systemAccount;
    }

    public void setSystemAccount(boolean systemAccount) {
        this.systemAccount = systemAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<UserAcl> getRoles() {
        return roles;
    }

    protected void setRoles(Set<UserAcl> roles) {
        this.roles = roles;
    }
}
