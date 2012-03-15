package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Game")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Game extends IntegerIdentityCUD {
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
