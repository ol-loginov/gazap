package gazap.site.web.controllers.map;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class MapGodControllerAddTileForm {
    @NotNull
    private MultipartFile file;
    @NotNull
    private Integer x;
    @NotNull
    private Integer y;
    @NotNull
    private Integer size;
    @NotNull
    private Integer scale;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }
}
