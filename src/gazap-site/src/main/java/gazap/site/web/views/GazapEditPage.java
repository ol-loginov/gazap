package gazap.site.web.views;

import com.iserv2.commons.mvc.model.BaseEditForm;
import com.iserv2.commons.mvc.views.ContentModule;
import com.iserv2.commons.mvc.views.ModuleList;
import com.iserv2.commons.mvc.views.ModuleUtil;
import gazap.site.web.modules.CdnModule;
import gazap.site.web.modules.ViewMetaModule;
import gazap.site.web.modules.VisitorModule;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
@ModuleList({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
public abstract class GazapEditPage<Form extends BaseEditForm> extends com.iserv2.commons.mvc.model.BaseEditPage<Form> {
    @XmlElementWrapper(name = "modules")
    @XmlAnyElement
    private List<ContentModule> modules;

    @Override
    public List<ContentModule> getModules() {
        if (modules == null) {
            modules = new ArrayList<ContentModule>();
        }
        return modules;
    }

    @Override
    public <T extends ContentModule> T getModule(Class<T> moduleClass) {
        return ModuleUtil.getModule(getModules(), moduleClass);
    }

    public GazapEditPage<Form> setSuccess(boolean success) {
        getOperationStatus().setSuccess(success);
        return this;
    }
}
