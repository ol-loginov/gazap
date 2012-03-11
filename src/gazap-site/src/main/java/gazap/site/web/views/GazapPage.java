package gazap.site.web.views;

import com.iserv2.commons.mvc.views.*;
import gazap.site.web.modules.CdnModule;
import gazap.site.web.modules.ViewMetaModule;
import gazap.site.web.modules.VisitorModule;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
@ModuleList({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
public abstract class GazapPage implements Content, OperationStatusKeeper {
    private List<ContentModule> modules;
    private OperationStatus operationStatus;

    @XmlElementWrapper(name = "modules")
    @XmlAnyElement
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

    // needed in JSTL
    public CdnModule getModuleCdn() {
        return ModuleUtil.getModule(getModules(), CdnModule.class);
    }

    // needed in JSTL
    @SuppressWarnings("UnusedDeclaration")
    public VisitorModule getModuleVisitor() {
        return ModuleUtil.getModule(getModules(), VisitorModule.class);
    }

    // needed in JSTL
    @SuppressWarnings("UnusedDeclaration")
    public ViewMetaModule getModuleViewMeta() {
        return ModuleUtil.getModule(getModules(), ViewMetaModule.class);
    }

    @XmlElement
    @Override
    public OperationStatus getOperationStatus() {
        if (operationStatus == null) {
            operationStatus = new OperationStatus();
        }
        return operationStatus;
    }

    public GazapPage setSuccess(boolean success) {
        getOperationStatus().setSuccess(success);
        return this;
    }
}
