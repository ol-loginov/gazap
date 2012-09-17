package waypalm.domain.entity;

import waypalm.domain.entity.base.IntegerIdentityCUD;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "World")
@DynamicUpdate
public class World extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 64;
    public static final int ALIAS_LENGTH = 64;

    @Column(name = "title", length = TITLE_LENGTH, unique = true, nullable = false)
    private String title;
    @Column(name = "alias", length = ALIAS_LENGTH, unique = true, nullable = true)
    private String alias;

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
}
