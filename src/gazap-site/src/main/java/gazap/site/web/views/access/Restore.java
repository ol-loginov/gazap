package gazap.site.web.views.access;

import gazap.site.web.annotations.PageTitleKey;
import gazap.site.web.controllers.access.RestoreForm;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Restore")
@XmlAccessorType(XmlAccessType.NONE)
@PageTitleKey("Restore.pageTitle")
public class Restore extends GazapPage {
    private RestoreForm form;

    public RestoreForm getForm() {
        return form;
    }

    public void setForm(RestoreForm form) {
        this.form = form;
    }
}
