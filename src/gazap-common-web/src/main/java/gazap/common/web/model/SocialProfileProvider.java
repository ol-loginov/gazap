package gazap.common.web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class SocialProfileProvider {
    public static final String OAUTH = "oauth";
    public static final String OPENID = "openid";

    @XmlElement(required = true)
    private String type;
    @XmlElement(required = true)
    private String provider;
    @XmlElement(required = true)
    private String url;

    public SocialProfileProvider() {
        this(null, null, null);
    }

    public SocialProfileProvider(String type, String provider, String url) {
        this.type = type;
        this.provider = provider;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public String getProvider() {
        return provider;
    }

    public String getUrl() {
        return url;
    }
}
