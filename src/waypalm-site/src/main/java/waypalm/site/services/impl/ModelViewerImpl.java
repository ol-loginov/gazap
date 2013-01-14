package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waypalm.common.services.FormatService;
import waypalm.common.util.GravatarHelper;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Profile;
import waypalm.site.model.viewer.UserTitle;
import waypalm.site.services.ModelViewer;
import waypalm.site.services.UserAccess;

@Service
public class ModelViewerImpl implements ModelViewer {
    @Autowired
    protected FormatService formatService;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected WorldRepository worldRepository;
    @Autowired
    protected UserAccess auth;

    @Override
    @Transactional
    public UserTitle profileTitle(Profile profile) {
        UserTitle title = new UserTitle();
        title.setId(profile.getId());
        title.setName(profile.getDisplayName());
        title.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        title.setRoute("/u/" + profile.getId());
        title.setSummary(userRepository.getProfileSummary(profile));
        title.setMe(profile.isSame(auth.loadCurrentProfile()));
        return title;
    }
}
