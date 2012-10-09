package waypalm.site.model.world;

import waypalm.domain.entity.SurfaceKind;

public class SurfaceCreateForm {
    private String title;
    private String alias;
    private SurfaceKind kind;

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

    public SurfaceKind getKind() {
        return kind;
    }

    public void setKind(SurfaceKind kind) {
        this.kind = kind;
    }
}
