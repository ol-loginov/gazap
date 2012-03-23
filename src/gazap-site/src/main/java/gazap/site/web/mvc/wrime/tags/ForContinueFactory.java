package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Raw;
import gazap.site.web.mvc.wrime.tree.PathReceiver;

public class ForContinueFactory implements TagFactory {
    @Override
    public boolean supports(String name) {
        return "continue".equals(name);
    }

    @Override
    public PathReceiver createReceiver(String name) {
        return new PathReceiver() {
            @Override
            public void complete(ExpressionContextKeeper scope) throws WrimeException {
                if (!scope.current().hasAttribute(ForFactory.LOOP_SCOPE)) {
                    error("You may use 'continue' only inside");
                }
                path.render(new Raw("continue;"));
            }
        };
    }
}
