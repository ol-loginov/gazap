package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.IntegerIdentityCUD;

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
    private Type type;

    protected Geometry() {
    }

    protected Geometry(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public static enum Type {
        PLANE,
        GEOID
    }
}
