package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "World")
@DynamicUpdate
public class World extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @Column(name = "title", length = TITLE_LENGTH, unique = true, nullable = false)
    @XmlElement
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH, unique = true, nullable = false)
    @XmlElement
    private String alias;
    @ManyToOne(optional = true)
    @JoinColumn(name = "worldGroup", nullable = true)
    private WorldGroup worldGroup;
    @OneToMany
    @JoinColumn(name = "world")
    private List<Surface> surfaces = new ArrayList<>();

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

    public List<Surface> getSurfaces() {
        return surfaces;
    }

    public void setSurfaces(List<Surface> surfaces) {
        this.surfaces = surfaces;
    }

    public WorldGroup getWorldGroup() {
        return worldGroup;
    }

    public void setWorldGroup(WorldGroup worldGroup) {
        this.worldGroup = worldGroup;
    }

    @Transient
    public String getSlug() {
        return StringUtils.hasText(getAlias()) ? getAlias() : Integer.toString(getId());
    }
}
