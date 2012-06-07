package gazap.site.web.controllers.map;

import gazap.domain.dao.MapDao;
import gazap.domain.entity.Geometry;
import gazap.domain.entity.GeometryPlain;
import gazap.domain.entity.GeometryPlainTile;
import gazap.domain.entity.Map;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.services.FileService;
import gazap.site.web.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.net.URL;

@Controller
public class MapTileProviderController extends BaseController {
    @Autowired
    private MapDao mapDao;
    @Autowired
    private FileService fileService;

    private Map loadMapById(String id) throws ObjectNotFoundException {
        Map mapInstance;
        try {
            mapInstance = mapDao.getMap(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            mapInstance = mapDao.findMapByAlias(id);
        }
        if (mapInstance == null) {
            throw new ObjectNotFoundException(Map.class, id);
        }
        return mapInstance;
    }

    @RequestMapping(value = "/tiles/p")
    public ModelAndView getTile() {
        return responseBuilder(null).forward("/static/img/pFFF-0.png");
    }

    @RequestMapping(value = "/tiles/m{map}/c{scale}s{size}x{x}y{y}")
    @ResponseBody
    public UrlResource getTile(
            @PathVariable("map") String mapId,
            @PathVariable("scale") int scale,
            @PathVariable("size") int size,
            @PathVariable("x") int x,
            @PathVariable("y") int y
    ) {
        Map map;
        try {
            map = loadMapById(mapId);
        } catch (ObjectNotFoundException e) {
            return null;
        }

        if (map.getGeometry() == null || !Geometry.Plain.CLASS.equals(map.getGeometry().getGeometryClass())) {
            return null;
        }

        GeometryPlainTile tile = mapDao.loadGeometryPlainTile((GeometryPlain) map.getGeometry(), scale, size, x, y);
        if (tile != null) {
            URL url = fileService.getTileURL(map, tile.getFile());
            if (url != null) {
                return new UrlResource(url);
            }
        }
        return null;
    }
}
