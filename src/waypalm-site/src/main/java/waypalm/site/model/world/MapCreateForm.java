package waypalm.site.model.world;

import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.Surface;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MapCreateForm {
    @NotNull
    @Size(min = 1, max = Surface.TITLE_LENGTH)
    private String title;
    @Size(min = 1, max = Surface.ALIAS_LENGTH)
    private String alias;
    @NotNull
    @Pattern(regexp = Geometry.Geoid.CLASS + "|" + Geometry.Plain.CLASS)
    private String geometryClass;

    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 1, groups = Geometry.Geoid.class)
    private Float geoidRadiusZ;
    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 1, groups = Geometry.Geoid.class)
    private Float geoidRadiusX;
    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 1, groups = Geometry.Geoid.class)
    private Float geoidRadiusY;

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

    public String getGeometryClass() {
        return geometryClass;
    }

    public void setGeometryClass(String geometryClass) {
        this.geometryClass = geometryClass;
    }

    public Float getGeoidRadiusZ() {
        return geoidRadiusZ;
    }

    public void setGeoidRadiusZ(Float geoidRadiusZ) {
        this.geoidRadiusZ = geoidRadiusZ;
    }

    public Float getGeoidRadiusX() {
        return geoidRadiusX;
    }

    public void setGeoidRadiusX(Float geoidRadiusX) {
        this.geoidRadiusX = geoidRadiusX;
    }

    public Float getGeoidRadiusY() {
        return geoidRadiusY;
    }

    public void setGeoidRadiusY(Float geoidRadiusY) {
        this.geoidRadiusY = geoidRadiusY;
    }
}
