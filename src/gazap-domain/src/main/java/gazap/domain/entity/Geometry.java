package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;

@Entity
@Table(name = "Geometry")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "class")
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

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }
}
