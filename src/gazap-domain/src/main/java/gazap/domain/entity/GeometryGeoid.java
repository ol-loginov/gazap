package gazap.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GeometryGeoid")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class GeometryGeoid extends Geometry {
    @Column(name = "radiusZ")
    private float radiusZ;
    @Column(name = "radiusX")
    private float radiusX;
    @Column(name = "radiusY")
    private float radiusY;

    public float getRadiusZ() {
        return radiusZ;
    }

    public void setRadiusZ(float radiusZ) {
        this.radiusZ = radiusZ;
    }

    public float getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }

    public float getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }
}
