package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "Profile")
@DynamicUpdate
public class Profile extends IntegerIdentityCUD implements Serializable {
    public static final int EMAIL_LENGTH = 128;
    public static final int EMAIL_CONFIRM_TOKEN_LENGTH = 128;
    public static final int PASSWORD_LENGTH = 64;
    public static final int PASSWORD_SALT_LENGTH = 32;
    public static final int DISPLAY_NAME_LENGTH = 64;

    @Column(name = "systemAccount", nullable = false)
    private boolean systemAccount;
    @Column(name = "email", nullable = false, length = EMAIL_LENGTH)
    private String email;
    @Column(name = "emailConfirmDate")
    private Date emailConfirmDate;
    @Column(name = "emailConfirmToken", length = EMAIL_CONFIRM_TOKEN_LENGTH)
    private String emailConfirmToken;
    @Column(name = "password", nullable = false, length = PASSWORD_LENGTH)
    private String password;
    @Column(name = "passwordSalt", nullable = false, length = PASSWORD_SALT_LENGTH)
    private String passwordSalt;
    @Column(name = "displayName", nullable = false, length = DISPLAY_NAME_LENGTH)
    private String displayName;
    @ElementCollection
    @CollectionTable(name = "ProfileAcl", joinColumns = @JoinColumn(name = "profile"))
    @Column(name = "aclRole")
    @Enumerated(EnumType.STRING)
    private Set<ProfileAcl> roles = Collections.emptySet();

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

    public Set<ProfileAcl> getRoles() {
        return roles;
    }

    protected void setRoles(Set<ProfileAcl> roles) {
        this.roles = roles;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Date getEmailConfirmDate() {
        return emailConfirmDate;
    }

    public void setEmailConfirmDate(Date emailConfirmDate) {
        this.emailConfirmDate = emailConfirmDate;
    }

    public String getEmailConfirmToken() {
        return emailConfirmToken;
    }

    public void setEmailConfirmToken(String emailConfirmToken) {
        this.emailConfirmToken = emailConfirmToken;
    }
}
