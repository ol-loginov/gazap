package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Entity
@Table(name = "World")
@DynamicUpdate
public class World extends IntegerIdentityCUD implements Serializable {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "hidden")
    @XmlElement
    private boolean hidden;
    @Column(name = "title", length = TITLE_LENGTH, unique = true, nullable = false)
    @XmlElement
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH, unique = true)
    @XmlElement
    private String alias;
    @ManyToOne(optional = true)
    @JoinColumn(name = "worldGroup", nullable = true)
    @XmlElement
    private WorldGroup worldGroup;
    @Column(name = "surfaceCount")
    @XmlElement
    private int surfaceCount;

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

    public WorldGroup getWorldGroup() {
        return worldGroup;
    }

    public void setWorldGroup(WorldGroup worldGroup) {
        this.worldGroup = worldGroup;
    }

    public int getSurfaceCount() {
        return surfaceCount;
    }

    public void setSurfaceCount(int surfaceCount) {
        this.surfaceCount = surfaceCount;
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
