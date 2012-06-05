package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentity;

import javax.persistence.*;

@Entity
@Table(name = "GeometryPlainTile")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
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
    @ManyToOne
    @JoinColumn(name = "file", nullable = false)
    private FileImage file;

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

    public FileImage getFile() {
        return file;
    }

    public void setFile(FileImage file) {
        this.file = file;
    }
}

