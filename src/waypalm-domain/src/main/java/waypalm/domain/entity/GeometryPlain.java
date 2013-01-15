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
    private Orientation orientation;

    public GeometryPlain() {
        super(Type.PLANE);
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public static enum Orientation {
        LEFT_HAND,
        RIGHT_HAND
    }
}
