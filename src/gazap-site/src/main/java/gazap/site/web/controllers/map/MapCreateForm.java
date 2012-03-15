package gazap.site.web.controllers.map;

public class MapCreateForm {
    private String title;
    private String geometryClass;

    private Float geometryPlainEast;
    private Float geometryPlainWest;
    private Float geometryPlainNorth;
    private Float geometryPlainSouth;

    private Float geometryGeoidRadiusZ;
    private Float geometryGeoidRadiusX;
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

    public Float getGeometryPlainEast() {
        return geometryPlainEast;
    }

    public void setGeometryPlainEast(Float geometryPlainEast) {
        this.geometryPlainEast = geometryPlainEast;
    }

    public Float getGeometryPlainWest() {
        return geometryPlainWest;
    }

    public void setGeometryPlainWest(Float geometryPlainWest) {
        this.geometryPlainWest = geometryPlainWest;
    }

    public Float getGeometryPlainNorth() {
        return geometryPlainNorth;
    }

    public void setGeometryPlainNorth(Float geometryPlainNorth) {
        this.geometryPlainNorth = geometryPlainNorth;
    }

    public Float getGeometryPlainSouth() {
        return geometryPlainSouth;
    }

    public void setGeometryPlainSouth(Float geometryPlainSouth) {
        this.geometryPlainSouth = geometryPlainSouth;
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
}
