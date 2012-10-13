package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@Table(name = "Surface")
@DynamicUpdate
@XmlSeeAlso({GeometryPlain.class, GeometryGeoid.class})
public class Surface extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @Version
    @Column(name = "version")
    private int version;
    @ManyToOne
    @JoinColumn(name = "world", nullable = false, updatable = false)
    @XmlElement(name = "world")
    private World world;
    @Column(name = "hidden")
    @XmlElement
    private boolean hidden;
    @Column(name = "title", length = TITLE_LENGTH, nullable = false)
    @XmlElement(name = "title")
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH)
    @XmlElement(name = "alias")
    private String alias;
    @Column(name = "main", length = ALIAS_LENGTH)
    @XmlElement(name = "main")
    private boolean main;
    @OneToOne
    @JoinColumn(name = "geometry", nullable = true)
    @XmlElement(name = "geometry")
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

    public boolean isMain() {
        return main;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    @Transient
    @XmlElement(name = "slug")
    public String getSlug() {
        return StringUtils.hasText(getAlias()) ? getAlias() : Integer.toString(getId());
    }
}
