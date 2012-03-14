package gazap.site.web.controllers.user;

import com.iserv2.commons.mvc.views.ViewName;
import com.iserv2.commons.mvc.views.ViewNames;
import gazap.domain.entity.UserProfile;
import gazap.site.services.UserService;
import gazap.site.web.controllers.BaseController;
import gazap.site.web.views.GazapPage;
import gazap.site.web.views.errors.UserProfileNotFound;
import gazap.site.web.views.user.UserProfilePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserProfileController extends BaseController {
    @Autowired
    protected UserService userService;

    @RequestMapping("/user/{accountId}")
    @ViewNames({
            @ViewName(response = UserProfilePage.class, name = "user/profile"),
            @ViewName(response = UserProfileNotFound.class, name = "errors/user-profile-not-found")
    })
    public GazapPage showProfilePage(@PathVariable String accountId) {
        UserProfile account = userService.findUserByAliasOrId(accountId);
        if (account == null) {
            return new UserProfileNotFound();
        }

        UserProfilePage page = new UserProfilePage();
        page.setUser(viewer.userDetails(account));
        return new UserProfilePage();
    }
}
