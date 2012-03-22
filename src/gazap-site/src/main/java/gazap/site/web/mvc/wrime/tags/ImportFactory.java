package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.tree.PathReceiver;

public class ImportFactory implements TagFactory {
    @Override
    public boolean supports(String name) {
        return "import".equals(name);
    }

    @Override
    public PathReceiver createReceiver(String name) {
        return new ImportReceiver();
    }
}
