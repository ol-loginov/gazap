package gazap.site.web.controllers.game;

import gazap.domain.entity.GameProfile;
import gazap.site.validation.UniqueGameProfileTitle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class GameCreateForm {
    @NotNull
    @Size(min = 1, max = GameProfile.TITLE_LENGTH)
    @UniqueGameProfileTitle
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}