package waypalm.site.web.extensions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.WebRequest;
import waypalm.common.web.extensions.ModelExtension;
import waypalm.domain.entity.Surface;
import waypalm.domain.entity.World;
import waypalm.site.services.UserAccess;
import waypalm.site.services.UserActionGuard;

@ModelExtension("eCan")
public class CanExtender extends Extender<CanExtender.Content> {
    @Autowired
    protected UserAccess userAccess;

    @Override
    protected CanExtender.Content populate(WebRequest request, CanExtender.Content extension, Object content) {
        extension = instantiateIfNull(extension, CanExtender.Content.class);
        extension.guard = userAccess.can(getLoggedUser());
        return extension;
    }

    public static class Content implements UserActionGuard {
        private UserActionGuard guard;

        @Override
        public boolean controlSurface(Surface surface) {
            return guard.controlSurface(surface);
        }

        public boolean controlWorld(World world) {
            return guard.controlWorld(world);
        }
    }
}
