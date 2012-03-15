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
        Map map = new Map();
        map.setTitle(form.getTitle());
        mapDao.create(map);

        mapDao.create(new UserMapRole(creator, map, UserMapRoles.CREATOR));

        Geometry geometry;
        if (Geometry.CLASS_GEOID.equals(form.getGeometryClass())) {

        } else if (Geometry.CLASS_PLAIN.equals(form.getGeometryClass())) {
        }

        return null;
    }
}
