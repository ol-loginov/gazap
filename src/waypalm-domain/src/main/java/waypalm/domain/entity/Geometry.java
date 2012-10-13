package waypalm.domain.entity;

import waypalm.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "Geometry")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "geometry", discriminatorType = DiscriminatorType.STRING)
public abstract class Geometry extends IntegerIdentityCUD {
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "geometry", updatable = false)
    @XmlElement(name = "geometry")
    @Enumerated(EnumType.STRING)
    private SurfaceGeometry geometry;

    protected Geometry() {
    }

    protected Geometry(SurfaceGeometry geometry) {
        this.geometry = geometry;
    }

    public SurfaceGeometry getGeometry() {
        return geometry;
    }
}
