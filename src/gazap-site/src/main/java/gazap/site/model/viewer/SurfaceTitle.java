package gazap.site.model.viewer;

public class SurfaceTitle {
    private int id;
    private String title;
    private String alias;
    private String route;

    private int approveCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getApproveCount() {
        return approveCount;
    }

    public void setApproveCount(int approveCount) {
        this.approveCount = approveCount;
    }
}
