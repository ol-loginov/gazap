package gazap.site.web.views.catalog;

import gazap.site.model.Anchor;
import gazap.site.web.views.GazapPage;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Listing")
@XmlAccessorType(XmlAccessType.NONE)
public class Listing extends GazapPage {
    private boolean welcome;
    @XmlElementWrapper(name = "breadcrumbs")
    @XmlElement(name = "anchor")
    private List<Anchor> breadcrumbs = new ArrayList<Anchor>();
    @XmlElementWrapper(name = "suggestions")
    @XmlElement(name = "anchor")
    private List<Anchor> suggestions = new ArrayList<Anchor>();

    public List<Anchor> getBreadcrumbs() {
        return breadcrumbs;
    }

    public List<Anchor> getSuggestions() {
        return suggestions;
    }

    public boolean isWelcome() {
        return welcome;
    }

    public void setWelcome(boolean welcome) {
        this.welcome = welcome;
    }
}
