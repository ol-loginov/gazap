package waypalm.site.web.controllers.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Geometry;
import waypalm.domain.entity.GeometryPlain;
import waypalm.domain.entity.GeometryPlainTile;
import waypalm.domain.entity.Surface;
import waypalm.site.exceptions.ObjectIllegalStateException;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.services.FileService;

import java.net.URL;

@Controller
public class MapTileProviderController extends MapGodControllerBase {
    public static final AbstractResource NOP_PNG = new MapTileProviderControllerNopImage();

    @Autowired
    private WorldRepository worldRepository;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/tiles/p")
    public ModelAndView getTile() {
        return responseBuilder(null).forward("/static/img/pFFF-0.png");
    }

    @RequestMapping(value = "/tiles/s{surface}/c{scale}s{size}x{x}y{y}")
    @ResponseBody
    public AbstractResource getTile(
            @PathVariable("surface") int surfaceId,
            @PathVariable("scale") int scale,
            @PathVariable("size") int size,
            @PathVariable("x") int x,
            @PathVariable("y") int y
    ) {
        Surface map;
        try {
            map = loadSurfaceById(surfaceId, null);
        } catch (ObjectNotFoundException e) {
            return NOP_PNG;
        } catch (ObjectIllegalStateException e) {
            return NOP_PNG;
        }

        if (map.getGeometry() == null || !Geometry.Plain.CLASS.equals(map.getGeometry().getGeometryClass())) {
            return NOP_PNG;
        }

        GeometryPlainTile tile = worldRepository.loadGeometryPlainTile((GeometryPlain) map.getGeometry(), scale, x, y);
        if (tile != null) {
            URL url = fileService.getTileURL(map, tile.getFile());
            if (url != null) {
                return new UrlResource(url);
            }
        }
        return NOP_PNG;
    }
}
