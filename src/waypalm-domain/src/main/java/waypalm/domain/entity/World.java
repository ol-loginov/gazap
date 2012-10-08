package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "World")
@DynamicUpdate
public class World extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;
    public static final int MEMO_LENGTH = 512;
    public static final int PUBLISHER_URL_LENGTH = 128;
    public static final int PUBLISHER_TITLE_LENGTH = 128;

    @Column(name = "title", length = TITLE_LENGTH, unique = true, nullable = false)
    @XmlElement
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH, unique = true, nullable = false)
    @XmlElement
    private String alias;
    @Column(name = "memo", length = MEMO_LENGTH, nullable = false)
    @XmlElement
    private String memo;
    @Column(name = "publisherUrl", length = PUBLISHER_URL_LENGTH, nullable = false)
    @XmlElement
    private String publisherUrl;
    @Column(name = "publisherTitle", length = PUBLISHER_URL_LENGTH, nullable = false)
    @XmlElement
    private String publisherTitle;

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

    @Transient
    public String getSlug() {
        return StringUtils.hasText(getAlias()) ? getAlias() : Integer.toString(getId());
    }
}
