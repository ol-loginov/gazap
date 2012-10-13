package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "GeometryPlain")
@DynamicUpdate
@DiscriminatorValue("PLANE")
public class GeometryPlain extends Geometry {
    @Column(name = "orientation", nullable = false)
    @Enumerated(EnumType.STRING)
    private SurfaceOrientation orientation;

    public GeometryPlain() {
        super(SurfaceGeometry.PLANE);
    }

    public SurfaceOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(SurfaceOrientation orientation) {
        this.orientation = orientation;
    }
}
