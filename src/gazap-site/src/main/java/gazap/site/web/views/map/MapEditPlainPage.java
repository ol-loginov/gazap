package gazap.site.web.views.map;

import gazap.domain.entity.GeometryPlain;
import gazap.site.model.viewer.MapTitle;

public class MapEditPlainPage {
    private GeometryPlain geometry;
    private MapTitle map;

    public MapTitle getMap() {
        return map;
    }

    public void setMap(MapTitle map) {
        this.map = map;
    }

    public GeometryPlain getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryPlain geometry) {
        this.geometry = geometry;
    }
}
