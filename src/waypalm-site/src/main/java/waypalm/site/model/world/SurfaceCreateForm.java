package waypalm.site.model.world;

import org.hibernate.validator.constraints.NotEmpty;
import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.SurfaceGeometry;
import waypalm.domain.entity.SurfaceOrientation;
import waypalm.site.validation.ValidationGroups;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SurfaceCreateForm {
    @NotEmpty
    @Size(max = Surface.TITLE_LENGTH)
    private String title;
    @Size(max = Surface.ALIAS_LENGTH)
    private String alias;
    @NotNull
    private SurfaceGeometry geometry;
    @NotNull(groups = ValidationGroups.PlainGeometry.class)
    private SurfaceOrientation plainOrientation;

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

    public SurfaceGeometry getGeometry() {
        return geometry;
    }

    public void setGeometry(SurfaceGeometry geometry) {
        this.geometry = geometry;
    }

    public SurfaceOrientation getPlainOrientation() {
        return plainOrientation;
    }

    public void setPlainOrientation(SurfaceOrientation plainOrientation) {
        this.plainOrientation = plainOrientation;
    }
}
