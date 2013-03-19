package waypalm.site.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import waypalm.common.services.FormatService;
import waypalm.common.util.GravatarHelper;
import waypalm.domain.dao.UserRepository;
import waypalm.domain.dao.WorldRepository;
import waypalm.domain.entity.Profile;
import waypalm.site.model.view.UserName;
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
    public UserName createUserName(Profile profile) {
        UserName name = new UserName();
        name.setId(profile.getId());
        name.setName(profile.getDisplayName());
        name.setGravatar(GravatarHelper.hashOrDefault(profile.getEmail()));
        name.setRoute("/u/" + profile.getId());
        name.setSummary(userRepository.getProfileSummary(profile));
        name.setMe(profile.isSame(auth.loadCurrentProfile()));
        return name;
    }
}
