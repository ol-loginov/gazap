package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
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

    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "title", length = TITLE_LENGTH, nullable = false)
    @XmlElement(name = "title")
    private String title;
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

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
