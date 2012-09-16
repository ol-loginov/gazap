package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentity;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "GeometryPlainTile")
@DynamicUpdate
public class GeometryPlainTile extends IntegerIdentity {
    @ManyToOne
    @JoinColumn(name = "geometry", nullable = false)
    private GeometryPlain geometry;
    @Column(name = "x")
    private int x;
    @Column(name = "y")
    private int y;
    @Column(name = "scale")
    private int scale;
    @Column(name = "size")
    private int size;
    @Column(name = "file", nullable = false)
    private String file;

    public GeometryPlain getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPlain geometry) {
        this.geometry = geometry;
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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}

