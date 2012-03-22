package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.tree.PathReceiver;

public interface TagFactory {
    boolean supports(String name);

    PathReceiver createReceiver(String name);
}
