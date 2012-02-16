package gazap.site.web.modules;

import com.iserv2.commons.mvc.views.ContentModule;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "viewMeta")
@XmlAccessorType(XmlAccessType.NONE)
public class ViewMetaModule extends ContentModule {
    @XmlElement
    private String titleKey;
    @XmlElement
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }
}
