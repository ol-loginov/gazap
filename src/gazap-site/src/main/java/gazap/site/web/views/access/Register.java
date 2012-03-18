package gazap.site.web.views.access;

import gazap.site.web.annotations.PageTitleKey;
import gazap.site.web.controllers.access.RegisterForm;

import javax.xml.bind.annotation.XmlElement;

@PageTitleKey("Register.pageTitle")
public class Register {
    @XmlElement
    private RegisterForm form;

    public RegisterForm getForm() {
        return form;
    }

    public void setForm(RegisterForm form) {
        this.form = form;
    }
}
