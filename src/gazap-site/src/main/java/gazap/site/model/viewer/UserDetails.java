package gazap.site.model.viewer;

import gazap.domain.entity.UserSummary;

public class UserDetails {
    private int id;
    private String alias;
    private String name;
    private String gravatar;
    private String route;
    private UserSummary summary;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public UserSummary getSummary() {
        return summary;
    }

    public void setSummary(UserSummary summary) {
        this.summary = summary;
    }
}
