package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityC;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FileImage")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FileImage extends IntegerIdentityC {
    @Column(name = "server", nullable = false)
    private String server;
    @Column(name = "path", nullable = false)
    private String path;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "width", nullable = false)
    private int width;
    @Column(name = "height", nullable = false)
    private int height;

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
