package waypalm.site.model.view;

import waypalm.domain.entity.ProfileSummary;

public class UserName {
    private int id;
    private String name;
    private String gravatar;
    private String route;
    private ProfileSummary summary;
    private boolean me;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ProfileSummary getSummary() {
        return summary;
    }

    public void setSummary(ProfileSummary summary) {
        this.summary = summary;
    }

    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }
}
