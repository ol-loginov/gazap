package waypalm.site.model.constructor;

import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.GeometryPlain;
import waypalm.domain.entity.Surface;
import waypalm.site.validation.ValidationGroups;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateSurfaceForm {
    @Size(max = Surface.TITLE_LENGTH)
    private String title;
    @NotNull
    private Geometry.Type type;
    @NotNull(groups = ValidationGroups.PlainGeometry.class)
    private GeometryPlain.Orientation plainOrientation;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Geometry.Type getType() {
        return type;
    }

    public void setType(Geometry.Type type) {
        this.type = type;
    }

    public GeometryPlain.Orientation getPlainOrientation() {
        return plainOrientation;
    }

    public void setPlainOrientation(GeometryPlain.Orientation plainOrientation) {
        this.plainOrientation = plainOrientation;
    }
}
