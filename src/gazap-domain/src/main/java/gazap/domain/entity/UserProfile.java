package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserProfile")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserProfile extends IntegerIdentityCUD {
    public static final int CONTACT_EMAIL_LENGTH = 128;
    public static final int PASSWORD_LENGTH = 64;
    public static final int DISPLAY_NAME_LENGTH = 64;

    @Column(name = "contactEmail", nullable = false, length = CONTACT_EMAIL_LENGTH)
    private String contactEmail;
    @Column(name = "password", nullable = false, length = PASSWORD_LENGTH)
    private String password;
    @Column(name = "displayName", nullable = false, length = DISPLAY_NAME_LENGTH)
    private String displayName;

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
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
}
