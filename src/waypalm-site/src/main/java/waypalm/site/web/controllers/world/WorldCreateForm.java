package waypalm.site.web.controllers.world;

import waypalm.domain.entity.World;
import waypalm.site.validation.UniqueWorldProfileTitle;

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
