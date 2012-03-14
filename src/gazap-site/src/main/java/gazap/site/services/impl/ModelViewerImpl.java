package gazap.site.services.impl;

import gazap.common.util.GravatarHelper;
import gazap.domain.dao.UserProfileDao;
import gazap.domain.entity.UserProfile;
import gazap.site.model.viewer.UserDetails;
import gazap.site.services.FormatService;
import gazap.site.services.ModelViewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelViewerImpl implements ModelViewer {
    @Autowired
    protected FormatService formatService;
    @Autowired
    protected UserProfileDao userProfileDao;

    @Override
    public UserDetails userDetails(UserProfile profile) {
        UserDetails userDetails = new UserDetails();
        userDetails.setId(profile.getId());
        userDetails.setAlias(profile.getAlias());
        userDetails.setName(profile.getDisplayName());
        userDetails.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        userDetails.setRoute("/user/" + (profile.getAlias() == null ? Integer.toString(profile.getId()) : profile.getAlias()));
        userDetails.setSummary(userProfileDao.loadSummary(profile));
        return userDetails;
    }
}
