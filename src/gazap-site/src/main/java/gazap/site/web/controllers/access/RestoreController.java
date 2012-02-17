package gazap.site.web.controllers.access;

import com.iserv2.commons.mvc.views.ViewName;
import gazap.site.web.views.access.Restore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RestoreController {
    @RequestMapping(value = "/auth/restore", method = RequestMethod.GET)
    @ViewName(name = "access/restore", response = Restore.class)
    public Restore showForm() {
        Restore response = new Restore();
        response.setForm(new RestoreForm());
        return response;
    }

    @RequestMapping(value = "/auth/restore", method = RequestMethod.POST)
    @ViewName(name = "access/restore", response = Restore.class)
    public Restore submitForm(RestoreForm form) {
        Restore response = new Restore();
        response.setForm(form);
        return response;
    }
}
