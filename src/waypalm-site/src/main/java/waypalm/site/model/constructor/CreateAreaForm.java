package waypalm.site.model.constructor;

import org.hibernate.validator.constraints.NotBlank;
import waypalm.domain.entity.AreaKind;

import javax.validation.constraints.NotNull;

public class CreateAreaForm {
    @NotBlank(message = "{validation.CreateAreaForm.title.isEmpty}")
    private String title;
    @NotNull(message = "{validation.CreateAreaForm.kind.isEmpty}")
    private AreaKind kind;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AreaKind getKind() {
        return kind;
    }

    public void setKind(AreaKind kind) {
        this.kind = kind;
    }
}
