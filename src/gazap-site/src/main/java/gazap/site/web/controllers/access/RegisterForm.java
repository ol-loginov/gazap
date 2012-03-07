package gazap.site.web.controllers.access;

import gazap.domain.entity.UserProfile;
import gazap.site.validation.UserEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class RegisterForm {
    @UserEmail
    @Size(min = 1, max = UserProfile.CONTACT_EMAIL_LENGTH)
    private String username;
    @NotNull
    @Size(min = 1)
    private String password;

    @XmlElement(required = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement(required = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
