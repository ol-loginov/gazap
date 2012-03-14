package gazap.site.web.controllers.game;

import gazap.domain.entity.GameProfile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GameCreateForm {
    @NotNull
    @Size(min = 1, max = GameProfile.NAME_LENGTH)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
