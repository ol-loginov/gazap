package gazap.site.web.controllers.world;

import gazap.domain.entity.World;
import gazap.site.validation.UniqueWorldProfileTitle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class WorldCreateForm {
    @NotNull
    @Size(min = 1, max = World.TITLE_LENGTH)
    @UniqueWorldProfileTitle
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
