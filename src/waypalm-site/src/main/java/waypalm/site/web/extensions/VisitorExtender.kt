package waypalm.site.web.extensions

import waypalm.common.web.extensions.ModelExtension
import javax.inject.Inject
import waypalm.site.services.ModelViewer
import waypalm.site.services.UserAccess
import waypalm.site.services.UserService
import org.springframework.web.context.request.WebRequest
import org.springframework.ui.ModelMap
import waypalm.site.model.view.UserName

ModelExtension("eVisitor")
public class VisitorExtender [Inject](
        val modelViewer: ModelViewer,
        val userService: UserService,
        val auth: UserAccess
)
: Extender<VisitorExtender.Content>()
{
    public  class Content {
        var user: UserName? = null

        fun isLogged(): Boolean {
            return user != null
        }
    }

    protected override fun populate(request: WebRequest, extension: VisitorExtender.Content?, model: ModelMap): VisitorExtender.Content {
        val user = auth.loadCurrentProfile()
        var result = instantiateIfNull(extension, javaClass<VisitorExtender.Content>())
        if (user != null) {
            result.user = modelViewer.createUserName(user)
        }
        return result ;
    }
}