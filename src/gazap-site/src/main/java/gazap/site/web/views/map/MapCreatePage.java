package gazap.site.web.views.map;

import gazap.site.web.controllers.map.MapCreateForm;
import gazap.site.web.views.GazapPage;

public class MapCreatePage extends GazapPage {
    private MapCreateForm form;

    public MapCreateForm getForm() {
        return form;
    }

    public void setForm(MapCreateForm form) {
        this.form = form;
    }
}
