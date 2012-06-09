package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;

@Entity
@Table(name = "Map")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Map extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @Column(name = "title", length = TITLE_LENGTH, nullable = false)
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH, unique = true, nullable = true)
    private String alias;
    @OneToOne
    @JoinColumn(name = "geometry", nullable = true)
    private Geometry geometry;
    @Column(name = "approveLimit")
    private int approveLimit;

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

    public int getApproveLimit() {
        return approveLimit;
    }

    public void setApproveLimit(int approveLimit) {
        this.approveLimit = approveLimit;
    }
}
