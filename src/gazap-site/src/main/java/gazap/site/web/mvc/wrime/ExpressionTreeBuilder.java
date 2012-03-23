package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.ops.OperandRenderer;
import gazap.site.web.mvc.wrime.tags.PathContext;
import gazap.site.web.mvc.wrime.tags.PathReceiver;

public class ExpressionTreeBuilder {
    private PathContext context;

    public ExpressionTreeBuilder(OperandRenderer renderer) {
        this.context = new PathContext(renderer);
    }

    public boolean isComplete() {
        return false;
    }

    public void complete() {
    }

    public PathReceiver receiver() {
        return getContext().current();
    }

    public PathContext getContext() {
        return context;
    }
}
