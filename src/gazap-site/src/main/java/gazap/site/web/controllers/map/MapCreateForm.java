package gazap.site.web.controllers.map;

import gazap.domain.entity.Geometry;
import gazap.domain.entity.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MapCreateForm {
    @NotNull
    @Size(min = 1, max = Map.TITLE_LENGTH)
    private String title;
    @Size(min = 1, max = Map.ALIAS_LENGTH)
    private String alias;
    @NotNull
    @Pattern(regexp = Geometry.Geoid.CLASS + "|" + Geometry.Plain.CLASS)
    private String geometryClass;

    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 0, groups = Geometry.Geoid.class)
    private Float geometryGeoidRadiusZ;
    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 0, groups = Geometry.Geoid.class)
    private Float geometryGeoidRadiusX;
    @NotNull(groups = Geometry.Geoid.class)
    @Min(value = 0, groups = Geometry.Geoid.class)
    private Float geometryGeoidRadiusY;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeometryClass() {
        return geometryClass;
    }

    public void setGeometryClass(String geometryClass) {
        this.geometryClass = geometryClass;
    }

    public Float getGeometryGeoidRadiusZ() {
        return geometryGeoidRadiusZ;
    }

    public void setGeometryGeoidRadiusZ(Float geometryGeoidRadiusZ) {
        this.geometryGeoidRadiusZ = geometryGeoidRadiusZ;
    }

    public Float getGeometryGeoidRadiusX() {
        return geometryGeoidRadiusX;
    }

    public void setGeometryGeoidRadiusX(Float geometryGeoidRadiusX) {
        this.geometryGeoidRadiusX = geometryGeoidRadiusX;
    }

    public Float getGeometryGeoidRadiusY() {
        return geometryGeoidRadiusY;
    }

    public void setGeometryGeoidRadiusY(Float geometryGeoidRadiusY) {
        this.geometryGeoidRadiusY = geometryGeoidRadiusY;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
