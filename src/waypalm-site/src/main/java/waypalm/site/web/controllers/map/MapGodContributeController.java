package waypalm.site.web.controllers.map;

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
import waypalm.domain.entity.*;
import waypalm.site.exceptions.ObjectIllegalStateException;
import waypalm.site.exceptions.ObjectNotFoundException;
import waypalm.site.model.ServiceError;
import waypalm.site.model.ServiceErrorException;
import waypalm.site.model.TileImage;
import waypalm.site.model.viewer.ContributionV;
import waypalm.site.services.ContributionService;
import waypalm.site.services.FileService;
import waypalm.site.web.controllers.ResponseBuilder;
import waypalm.site.web.model.ApiAnswer;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class MapGodContributeController extends MapGodControllerBase {
    private static final String ACTION_URL = "/s/{surface}/god";

    @Autowired
    private ContributionService contributionService;
    @Autowired
    private FileService fileService;

    @RequestMapping(value = ACTION_URL, method = RequestMethod.GET)
    public ModelAndView getEditor(Locale locale, @PathVariable("surface") int surfaceId) throws ObjectNotFoundException, ObjectIllegalStateException {
        if (!auth.isAuthorized()) {
            throw new AccessDeniedException("you should be authorized to enter god mode");
        }

        Surface mapInstance = loadSurfaceById(surfaceId, locale);

        if (SurfaceGeometry.PLANE == mapInstance.getGeometry().getGeometry()) {
            return responseBuilder(locale).view("map/plain-geometry-edit")
                    .addObject("surface", viewer.surfaceTitle(mapInstance))
                    .addObject("geometry", mapInstance.getGeometry());
        } else {
            throw new ObjectIllegalStateException(format.getMessage(locale, "illegalState.Map.geometryUnknown"));
        }
    }

    @RequestMapping(value = ACTION_URL + "/contribution/{contribution}/tile", method = RequestMethod.GET)
    @ResponseBody
    public UrlResource getContributionTile(Locale locale, @PathVariable("surface") int surfaceId, @PathVariable("contribution") int contributionId) throws ObjectIllegalStateException, ObjectNotFoundException {
        Surface surface = loadSurfaceById(surfaceId, locale);
        ContributionTile contribution = worldRepository.getContributionTile(contributionId);
        URL url = fileService.getTileURL(surface, contribution.getFile());
        return new UrlResource(url);
    }

    @RequestMapping(value = ACTION_URL + "/contribution/{contribution}/reject.ajax", method = RequestMethod.POST)
    public ModelAndView rejectChanges(Locale locale, @PathVariable("surface") int surfaceId, @PathVariable("contribution") int contributionId) throws ObjectIllegalStateException, ObjectNotFoundException {
        Surface surface = loadSurfaceById(surfaceId, locale);
        Profile visitor = auth.getCurrentProfile();
        ApiAnswer answer = new ApiAnswer();
        try {
            contributionService.reject(visitor, surface, contributionId);
            answer.setSuccess(true);
        } catch (ServiceErrorException e) {
            answer.setSuccess(false);
        }
        return responseBuilder(locale).json(answer);
    }

    @RequestMapping(value = ACTION_URL + "/contribution/changes.ajax", method = RequestMethod.GET)
    public ModelAndView getLocalChanges(Locale locale, @PathVariable("surface") int surfaceId, @RequestParam(value = "after", defaultValue = "0") long after) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        List<ContributionV> list = new ArrayList<>();
        return responseBuilder(locale).json(list);
    }

    @RequestMapping(value = ACTION_URL + "/contribution/add_tile.ajax", method = RequestMethod.POST)
    public ModelAndView uploadTile(Locale locale, @PathVariable("surface") int surfaceId, @Valid MapGodControllerAddTileForm form, BindingResult formBinding) throws ObjectIllegalStateException, ObjectNotFoundException, IOException {
        UploadTileApiAnswer answer = new UploadTileApiAnswer();
        ResponseBuilder response = responseBuilder(locale);

        if (formBinding.hasErrors()) {
            response.validationErrors(answer, formBinding);
            return response.json(answer);
        }

        Surface surface = loadSurfaceById(surfaceId, locale);

        TileImage info = new TileImage();
        info.setFileSize(form.getFile().getSize());
        info.setFileContentType(form.getFile().getContentType());
        info.setFileInputStream(form.getFile().getInputStream());
        info.setTileScale(form.getScale());
        info.setTileSize(form.getSize());
        info.setTileX(form.getX());
        info.setTileY(form.getY());

        try {
            ContributionTile tile = contributionService.addMapTile(auth.getCurrentProfile(), surface, info);
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
