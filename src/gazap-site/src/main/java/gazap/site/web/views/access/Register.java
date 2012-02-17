package gazap.site.web.views.access;

import gazap.site.web.annotations.PageTitleKey;
import gazap.site.web.controllers.access.RegisterForm;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Register")
@XmlAccessorType(XmlAccessType.NONE)
@PageTitleKey("Register.pageTitle")
public class Register extends GazapPage {
    @XmlElement
    private RegisterForm form;

    public RegisterForm getForm() {
        return form;
    }

    public void setForm(RegisterForm form) {
        this.form = form;
    }
}
