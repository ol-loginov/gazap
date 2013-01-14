package waypalm.site.model.constructor;

import org.hibernate.validator.constraints.NotBlank;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.SurfaceOrientation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreatePlaneForm {
    @NotBlank
    @Size(max = Surface.TITLE_LENGTH)
    private String title;
    @NotNull
    private SurfaceOrientation orientation;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SurfaceOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(SurfaceOrientation orientation) {
        this.orientation = orientation;
    }
}
