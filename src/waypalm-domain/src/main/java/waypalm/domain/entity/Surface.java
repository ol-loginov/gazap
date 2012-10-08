package waypalm.domain.entity;

import waypalm.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "Surface")
@DynamicUpdate
public class Surface extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @ManyToOne
    @JoinColumn(name = "world", nullable = false, updatable = false)
    private World world;
    @Column(name = "title", length = TITLE_LENGTH, nullable = false)
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH)
    private String alias;
    @OneToOne
    @JoinColumn(name = "geometry", nullable = true)
    private Geometry geometry;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
