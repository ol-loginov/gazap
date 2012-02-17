package gazap.panel.web.views;

import com.iserv2.commons.mvc.views.*;
import gazap.panel.web.modules.CdnModule;
import gazap.panel.web.modules.ViewMetaModule;
import gazap.panel.web.modules.VisitorModule;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
@ModuleList({CdnModule.class, VisitorModule.class, ViewMetaModule.class})
public abstract class GazapPage implements Content, OperationStatusKeeper {
    @XmlElementWrapper(name = "modules")
    @XmlAnyElement
    private List<ContentModule> modules;
    @XmlElement
    private OperationStatus operationStatus;

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
