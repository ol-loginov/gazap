package gazap.panel.services.impl;

import gazap.domain.dao.UserProfileDao;
import gazap.panel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected UserProfileDao profileDao;
}

