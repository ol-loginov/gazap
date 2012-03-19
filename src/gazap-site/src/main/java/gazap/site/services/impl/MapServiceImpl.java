package gazap.site.services.impl;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.*;
import gazap.site.services.MapService;
import gazap.site.web.controllers.map.MapCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapServiceImpl implements MapService {
    @Autowired
    protected MapDao mapDao;

    @Override
    public Map createMap(UserProfile creator, MapCreateForm form) {
        Geometry geometry;
        if (Geometry.Geoid.CLASS.equals(form.getGeometryClass())) {
            GeometryGeoid geoid = new GeometryGeoid();
            geoid.setRadiusX(form.getGeoidRadiusX());
            geoid.setRadiusY(form.getGeoidRadiusY());
            geoid.setRadiusZ(form.getGeoidRadiusZ());
            geometry = geoid;
        } else if (Geometry.Plain.CLASS.equals(form.getGeometryClass())) {
            geometry = new GeometryPlain();
        } else {
            throw new IllegalArgumentException("geometry " + form.getGeometryClass() + " is not supported");
        }

        mapDao.create(geometry);

        Map map = new Map();
        map.setTitle(form.getTitle());
        map.setGeometry(geometry);

        mapDao.create(map);
        mapDao.create(new UserMapRole(creator, map, UserMapRoles.CREATOR));

        return map;
    }
}
