package waypalm.site.model.world;

import waypalm.domain.entity.World;
import waypalm.site.validation.WorldTitle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@WorldTitle
public class WorldCreateForm {
    @NotNull
    @Size(min = 1, max = World.TITLE_LENGTH)
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
