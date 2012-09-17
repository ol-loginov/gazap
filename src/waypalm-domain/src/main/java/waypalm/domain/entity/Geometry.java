package waypalm.domain.entity;

import waypalm.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "Geometry")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.STRING)
public abstract class Geometry extends IntegerIdentityCUD {
    public static interface Geoid {
        String CLASS = "geoid";
    }

    public static interface Plain {
        String CLASS = "plain";
    }

    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "class", updatable = false)
    private String geometryClass = null;

    protected Geometry() {
    }

    protected Geometry(String geometryClass) {
        this.geometryClass = geometryClass;
    }

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public String getGeometryClass() {
        return geometryClass;
    }
}
