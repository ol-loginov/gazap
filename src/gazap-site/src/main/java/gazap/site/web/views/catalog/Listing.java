package gazap.site.web.views.catalog;

import gazap.common.web.model.Anchor;

import java.util.ArrayList;
import java.util.List;

public class Listing {
    private boolean welcome;
    private List<Anchor> breadcrumbs = new ArrayList<Anchor>();
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
