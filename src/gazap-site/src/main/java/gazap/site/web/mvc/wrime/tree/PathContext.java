package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.OperandRenderer;

import java.util.Stack;

public class PathContext {
    private Stack<PathReceiver> receiverStack = new Stack<PathReceiver>();
    private Stack<OperandRenderer> rendererStack = new Stack<OperandRenderer>();

    public PathContext(OperandRenderer renderer) {
        receiverStack.push(new RootReceiver());
        rendererStack.push(renderer);
    }

    public void push(PathReceiver receiver) {
        receiverStack.push(receiver);
    }

    public void remove(PathReceiver receiver) {
        while (receiverStack.peek() != receiver) {
            receiverStack.pop();
        }
        receiverStack.pop();
    }

    public PathReceiver current() {
        return receiverStack.peek();
    }

    public void push(OperandRenderer renderer) {
        rendererStack.push(renderer);
    }

    /**
     * Complete expression analysis. No more expression tokens are expected
     *
     * @param scope current scope
     * @throws WrimeException for any error
     */
    public void finish(ExpressionContextKeeper scope) throws WrimeException {
    }

    public void markComplete(ExpressionContextKeeper scope) throws WrimeException {
        while (!receiverStack.empty()) {
            receiverStack.pop().complete(this, scope);
        }
    }

    public void render(Operand operand) throws WrimeException {
        OperandRenderer renderer = rendererStack.pop();
        if (renderer == null) {
            throw new WrimeException("internal exception - no call renderer", null);
        }
        renderer.render(operand);
    }
}
