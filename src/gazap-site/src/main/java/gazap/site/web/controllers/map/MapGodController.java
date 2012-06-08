package gazap.site.web.controllers.map;

import gazap.domain.dao.ContributionDao;
import gazap.domain.dao.MapDao;
import gazap.domain.entity.*;
import gazap.site.exceptions.ObjectIllegalStateException;
import gazap.site.exceptions.ObjectNotFoundException;
import gazap.site.model.ServiceError;
import gazap.site.model.ServiceErrorException;
import gazap.site.model.TileImage;
import gazap.site.model.viewer.ContributionV;
import gazap.site.services.ContributionService;
import gazap.site.services.FileService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.controllers.ResponseBuilder;
import gazap.site.web.model.ApiAnswer;
import gazap.site.web.views.map.MapEditPlainPage;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class MapGodController extends BaseController {
    private static final String ACTION_URL = "/map/{map}/god";

    @Autowired
    private MapDao mapDao;
    @Autowired
    private ContributionService contributionService;
    @Autowired
    private ContributionDao contributionDao;
    @Autowired
    private FileService fileService;

    private Map loadMapById(Locale locale, String id) throws ObjectNotFoundException, ObjectIllegalStateException {
        Map mapInstance;
        try {
            mapInstance = mapDao.getMap(Integer.parseInt(id));
        } catch (NumberFormatException nfe) {
            mapInstance = mapDao.findMapByAlias(id);
        }
        if (mapInstance == null) {
            throw new ObjectNotFoundException(Map.class, id);
        }
        if (mapInstance.getGeometry() == null) {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.noGeometrySpecified"));
        }
        return mapInstance;
    }

    @RequestMapping(value = ACTION_URL, method = RequestMethod.GET)
    public ModelAndView getEditor(Locale locale, @PathVariable("map") String map) throws ObjectNotFoundException, ObjectIllegalStateException {
        if (!auth.isAuthorized()) {
            throw new AccessDeniedException("you should be authorized to enter god mode");
        }

        Map mapInstance = loadMapById(locale, map);

        if (Geometry.Plain.CLASS.equals(mapInstance.getGeometry().getGeometryClass())) {
            MapEditPlainPage page = new MapEditPlainPage();
            page.setGeometry((GeometryPlain) mapInstance.getGeometry());
            page.setMap(viewer.mapTitle(mapInstance));
            return responseBuilder(locale).view("map/edit-plain-geometry", page);
        } else {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.geometryUnknown"));
        }
    }

    @RequestMapping(value = ACTION_URL + "/contribution/{contribution}/tile", method = RequestMethod.GET)
    @ResponseBody
    public UrlResource getContributionTile(Locale locale, @PathVariable("map") String mapId, @PathVariable("contribution") int contributionId) throws ObjectIllegalStateException, ObjectNotFoundException {
        Map map = loadMapById(locale, mapId);
        ContributionTile contribution = contributionDao.loadTile(contributionId);
        URL url = fileService.getTileURL(map, contribution.getFile());
        return new UrlResource(url);
    }

    @RequestMapping(value = ACTION_URL + "/contribution/{contribution}/reject.ajax", method = RequestMethod.POST)
    public ModelAndView rejectChanges(Locale locale, @PathVariable("map") String mapId, @PathVariable("contribution") int contributionId) throws ObjectIllegalStateException, ObjectNotFoundException {
        Map map = loadMapById(locale, mapId);
        UserProfile visitor = auth.getCurrentProfile();
        ApiAnswer answer = new ApiAnswer();
        try {
            contributionService.reject(visitor, map, contributionId);
            answer.setSuccess(true);
        } catch (ServiceErrorException e) {
        }
        return responseBuilder(locale).json(answer);
    }

    @RequestMapping(value = ACTION_URL + "/contribution/changes.ajax", method = RequestMethod.GET)
    public ModelAndView getLocalChanges(Locale locale, @PathVariable("map") String map, @RequestParam(value = "after", defaultValue = "0") long after) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        Map mapInstance = loadMapById(locale, map);
        UserProfile visitor = auth.getCurrentProfile();
        GetLocalChangesApiAnswer answer = new GetLocalChangesApiAnswer();

        List<ContributionV> list = new ArrayList<ContributionV>();
        for (Contribution c : contributionDao.findContributions(mapInstance, visitor, ContributionDecision.NONE, new Date(after))) {
            list.add(viewer.mapContribution(c));
        }
        answer.setList(list);
        return responseBuilder(locale).json(answer);
    }

    @JsonAutoDetect(JsonMethod.NONE)
    public static class GetLocalChangesApiAnswer extends ApiAnswer {
        private List<ContributionV> list;

        @JsonProperty
        public List<ContributionV> getList() {
            return list;
        }

        public void setList(List<ContributionV> list) {
            this.list = list;
            setSuccess(list != null);
        }
    }

    @RequestMapping(value = ACTION_URL + "/contribution/add_tile.ajax", method = RequestMethod.POST)
    public ModelAndView uploadTile(Locale locale, @PathVariable("map") String map, MapGodControllerAddTileForm form, BindingResult formBinding) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        UploadTileApiAnswer answer = new UploadTileApiAnswer();
        ResponseBuilder response = responseBuilder(locale);

        validate(form, formBinding);

        if (formBinding.hasErrors()) {
            response.validationErrors(answer, formBinding);
            return response.json(answer);
        }

        Map mapInstance = loadMapById(locale, map);

        TileImage info = new TileImage();
        info.setFileSize(form.getFile().getSize());
        info.setFileContentType(form.getFile().getContentType());
        info.setFileInputStream(form.getFile().getInputStream());
        info.setTileScale(form.getScale());
        info.setTileSize(form.getSize());
        info.setTileX(form.getX());
        info.setTileY(form.getY());

        try {
            ContributionTile tile = contributionService.addMapTile(auth.getCurrentProfile(), mapInstance, info);
            answer.setContribution(viewer.mapContribution(tile));
        } catch (ServiceErrorException see) {
            answer.setCode(see.getError().code());
        } catch (AccessDeniedException ade) {
            answer.setCode(ServiceError.ACCESS_DENIED.code());
        } finally {
            IOUtils.closeQuietly(info.getFileInputStream());
        }

        if (answer.getCode() != null) {
            answer.setMessage(format.getMessage(locale, answer.getCode()));
        }
        return response.json(answer);
    }

    @JsonAutoDetect(JsonMethod.NONE)
    public static class UploadTileApiAnswer extends ApiAnswer {
        private ContributionV contribution;

        @JsonProperty
        public ContributionV getContribution() {
            return contribution;
        }

        public void setContribution(ContributionV contribution) {
            this.contribution = contribution;
            setSuccess(this.contribution != null);
        }
    }
}
