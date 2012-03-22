package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.tree.PathContext;

public class LoopFactory implements TagFactory {
    @Override
    public boolean supports(String name) {
        return "loop".equals(name);
    }

    @Override
    public LoopReceiver createReceiver(PathContext context, String name) {
        return new LoopReceiver();
    }
}
