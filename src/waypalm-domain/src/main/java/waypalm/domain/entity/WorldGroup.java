package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;
import waypalm.domain.entity.base.IntegerIdentityCUD;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WorldGroup")
@DynamicUpdate
public class WorldGroup extends IntegerIdentityCUD {
    public static final int TITLE_LENGTH = 128;

    @Column(name = "title", length = TITLE_LENGTH, nullable = false)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
