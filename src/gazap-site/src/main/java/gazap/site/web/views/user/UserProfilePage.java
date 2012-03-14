package gazap.site.web.views.user;

import gazap.site.model.viewer.UserDetails;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UserProfilePage")
@XmlAccessorType(XmlAccessType.NONE)
public class UserProfilePage extends GazapPage {
    private UserDetails user;

    @XmlElement
    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails user) {
        this.user = user;
    }
}
