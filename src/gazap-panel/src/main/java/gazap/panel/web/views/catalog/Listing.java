package gazap.panel.web.views.catalog;

import gazap.panel.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Listing")
@XmlAccessorType(XmlAccessType.NONE)
public class Listing extends GazapPage {
    private boolean welcome;
    @XmlElementWrapper(name = "breadcrumbs")
    @XmlElement(name = "anchor")
    private List<gazap.common.web.model.Anchor> breadcrumbs = new ArrayList<gazap.common.web.model.Anchor>();
    @XmlElementWrapper(name = "suggestions")
    @XmlElement(name = "anchor")
    private List<gazap.common.web.model.Anchor> suggestions = new ArrayList<gazap.common.web.model.Anchor>();

    public List<gazap.common.web.model.Anchor> getBreadcrumbs() {
        return breadcrumbs;
    }

    public List<gazap.common.web.model.Anchor> getSuggestions() {
        return suggestions;
    }

    public boolean isWelcome() {
        return welcome;
    }

    public void setWelcome(boolean welcome) {
        this.welcome = welcome;
    }
}
