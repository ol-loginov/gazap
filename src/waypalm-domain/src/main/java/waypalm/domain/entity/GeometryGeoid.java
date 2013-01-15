package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GeometryGeoid")
@DynamicUpdate
@DiscriminatorValue("GEOID")
public class GeometryGeoid extends Geometry {
    @Column(name = "radiusZ")
    private float radiusZ;
    @Column(name = "radiusX")
    private float radiusX;
    @Column(name = "radiusY")
    private float radiusY;

    public GeometryGeoid() {
        super(Type.GEOID);
    }

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
