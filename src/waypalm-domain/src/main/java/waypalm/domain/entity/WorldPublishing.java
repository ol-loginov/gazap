package waypalm.domain.entity;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.DomainEntity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@Entity
@Table(name = "WorldPublishing")
@DynamicUpdate
public class WorldPublishing implements DomainEntity {
    public static final int MEMO_LENGTH = 512;
    public static final int PUBLISHER_URL_LENGTH = 128;
    public static final int PUBLISHER_TITLE_LENGTH = 128;

    @SuppressWarnings("UnusedDeclaration")
    @EmbeddedId
    private ID id = new ID();

    @ManyToOne(optional = false)
    @JoinColumn(name = "world", nullable = false, updatable = false)
    @MapsId("world")
    private World world;
    @Column(name = "memo", length = MEMO_LENGTH, nullable = false)
    @XmlElement
    private String memo;
    @Column(name = "publisherUrl", length = PUBLISHER_URL_LENGTH, nullable = false)
    @XmlElement
    private String publisherUrl;
    @Column(name = "publisherTitle", length = PUBLISHER_TITLE_LENGTH, nullable = false)
    @XmlElement
    private String publisherTitle;

    protected WorldPublishing() {
    }

    public WorldPublishing(World world) {
        setWorld(world);
    }

    public World getWorld() {
        return world;
    }

    protected void setWorld(World world) {
        this.world = world;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }

    public String getPublisherTitle() {
        return publisherTitle;
    }

    public void setPublisherTitle(String publisherTitle) {
        this.publisherTitle = publisherTitle;
    }

    @Embeddable
    public static class ID implements Serializable {
        private Integer world;

        protected ID() {
        }

        public ID(Integer world) {
            this.world = world;
        }

        public Integer getWorld() {
            return world;
        }

        public void setWorld(Integer world) {
            this.world = world;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ID && equals((ID) obj);
        }

        public boolean equals(ID pk) {
            return pk != null
                    && world != null && world.equals(pk.world);
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(world)
                    .toHashCode();
        }
    }
}
