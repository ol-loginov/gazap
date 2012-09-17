package waypalm.domain.entity;

import waypalm.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "UserProfile")
@DynamicUpdate
public class UserProfile extends IntegerIdentityCUD {
    public static final int CONTACT_EMAIL_LENGTH = 128;
    public static final int PASSWORD_LENGTH = 64;
    public static final int PASSWORD_SALT_LENGTH = 32;
    public static final int DISPLAY_NAME_LENGTH = 64;
    public static final int ALIAS_LENGTH = 32;

    @Column(name = "systemAccount", nullable = false)
    private boolean systemAccount;
    @Column(name = "alias", length = ALIAS_LENGTH)
    private String alias;
    @Column(name = "email", nullable = false, length = CONTACT_EMAIL_LENGTH)
    private String email;
    @Column(name = "password", nullable = false, length = PASSWORD_LENGTH)
    private String password;
    @Column(name = "passwordSalt", nullable = false, length = PASSWORD_SALT_LENGTH)
    private String passwordSalt;
    @Column(name = "displayName", nullable = false, length = DISPLAY_NAME_LENGTH)
    private String displayName;
    @ElementCollection
    @CollectionTable(name = "UserAcl", joinColumns = @JoinColumn(name = "userProfile"))
    @Column(name = "aclRole")
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}
