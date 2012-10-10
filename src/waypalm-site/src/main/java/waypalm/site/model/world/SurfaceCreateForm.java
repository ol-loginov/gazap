package waypalm.site.model.world;

import org.hibernate.validator.constraints.NotEmpty;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.SurfaceKind;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SurfaceCreateForm {
    @NotEmpty
    @Size(max = Surface.TITLE_LENGTH)
    private String title;
    @Size(max = Surface.ALIAS_LENGTH)
    private String alias;
    @NotNull
    private SurfaceKind kind;

    private boolean plainClockwise;

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

    public boolean isPlainClockwise() {
        return plainClockwise;
    }

    public void setPlainClockwise(boolean plainClockwise) {
        this.plainClockwise = plainClockwise;
    }
}
