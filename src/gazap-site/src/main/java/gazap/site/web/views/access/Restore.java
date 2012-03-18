package gazap.site.web.views.access;

import gazap.site.web.annotations.PageTitleKey;
import gazap.site.web.controllers.access.RestoreForm;

@PageTitleKey("Restore.pageTitle")
public class Restore {
    private RestoreForm form;

    public RestoreForm getForm() {
        return form;
    }

    public void setForm(RestoreForm form) {
        this.form = form;
    }
}
