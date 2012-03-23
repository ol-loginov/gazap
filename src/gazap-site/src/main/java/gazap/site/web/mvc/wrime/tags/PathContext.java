package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.OperandRenderer;

import java.util.Stack;

public class PathContext {
    private Stack<PathReceiver> receiverStack = new Stack<PathReceiver>();
    private Stack<OperandRenderer> rendererStack = new Stack<OperandRenderer>();

    private String file;
    private int line;
    private int column;

    public PathContext(OperandRenderer renderer, PathReceiver root) {
        push(root);
        push(renderer);
    }

    public void setPosition(String file, int line, int column) {
        this.file = file;
        this.line = line;
        this.column = column;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int depth() {
        return receiverStack.size();
    }

    public void push(PathReceiver receiver) {
        receiver.setPath(this);
        receiverStack.push(receiver);
        receiver.setup();
    }

    public void remove(PathReceiver receiver) {
        while (receiverStack.peek() != receiver) {
            receiverStack.pop().setPath(null);
        }
        receiverStack.pop().setPath(null);
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
            PathReceiver receiver = receiverStack.pop();
            receiver.complete(scope);
            receiver.setPath(null);
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
