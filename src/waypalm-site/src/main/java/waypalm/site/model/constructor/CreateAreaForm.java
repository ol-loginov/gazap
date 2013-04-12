package waypalm.site.model.constructor;

import org.hibernate.validator.constraints.NotBlank;
import waypalm.domain.entity.AreaKind;
import waypalm.domain.entity.PlaneDirection;

import javax.validation.constraints.NotNull;

public class CreateAreaForm {
    @NotBlank
    private String title;
    @NotNull
    private AreaKind kind;

    private PlainSetup geometryPlain;

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

    public PlainSetup getGeometryPlain() {
        return geometryPlain;
    }

    public void setGeometryPlain(PlainSetup geometryPlain) {
        this.geometryPlain = geometryPlain;
    }

    public static class PlainSetup {
        private PlaneDirection axisX;
        private PlaneDirection axisY;

        public PlaneDirection getAxisX() {
            return axisX;
        }

        public void setAxisX(PlaneDirection axisX) {
            this.axisX = axisX;
        }

        public PlaneDirection getAxisY() {
            return axisY;
        }

        public void setAxisY(PlaneDirection axisY) {
            this.axisY = axisY;
        }
    }
}
