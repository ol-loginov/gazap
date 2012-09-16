package gazap.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "ContributionTile")
@DynamicUpdate
@DiscriminatorValue(ContributionTile.CLASS)
public class ContributionTile extends Contribution {
    public static final String CLASS = "TILE";

    @Column(name = "x", updatable = false)
    private int x;
    @Column(name = "y", updatable = false)
    private int y;
    @Column(name = "scale", updatable = false)
    private int scale;
    @Column(name = "size", updatable = false)
    private int size;
    @Column(name = "action", updatable = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContributionTileAction action;
    @Column(name = "file", updatable = false)
    private String file;

    public ContributionTile() {
        super(CLASS);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ContributionTileAction getAction() {
        return action;
    }

    public void setAction(ContributionTileAction action) {
        this.action = action;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
