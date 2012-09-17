package gazap.site.web.controllers.map;

import gazap.domain.dao.WorldRepository;
import gazap.domain.entity.Geometry;
import gazap.domain.entity.GeometryPlain;
import gazap.domain.entity.GeometryPlainTile;
import gazap.domain.entity.Surface;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
