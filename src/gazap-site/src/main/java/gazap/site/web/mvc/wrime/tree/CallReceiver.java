package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.TypeDef;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Getter;
import gazap.site.web.mvc.wrime.ops.Invoker;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.Variable;

public class CallReceiver extends PathReceiver {
    private final CallReceiver parent;
    private Operand operand;

    private boolean expectInvoker = false;

    public CallReceiver() {
        this(null);
    }

    public CallReceiver(CallReceiver parent) {
        this.parent = parent;
    }

    @Override
    public void complete(PathContext context, ExpressionContextKeeper scope) throws WrimeException {
        context.render(operand);
    }

    @Override
    public void pushDelimiter(PathContext context, ExpressionContextKeeper scope, String delimiter) throws WrimeException {
        if (",".equals(delimiter)) {
            if (parent != null) {
                returnToParent(context, false);
                return;
            }
        } else if (".".equals(delimiter)) {
            if (operand == null) {
                error("no invocable at the point");
            }
            expectInvoker = true;
            return;
        }
        errorUnexpected(delimiter);
    }

    @Override
    public void beginList(PathContext path, ExpressionContextKeeper scope) throws WrimeException {
        if (operand instanceof Invoker) {
            path.push(new CallReceiver(this));
        } else {
            error("expected function point only");
        }
    }

    @Override
    public void closeList(PathContext path, ExpressionContextKeeper scope) throws WrimeException {
        if (parent != null) {
            returnToParent(path, true);
        } else {
            error("unexpected list closure");
        }
    }

    private void returnToParent(PathContext path, boolean last) throws WrimeException {
        path.remove(this);
        if (operand != null) {
            parent.addOperand(path, operand, last);
        }
    }

    private void addOperand(PathContext path, Operand argument, boolean last) throws WrimeException {
        if (operand instanceof Invoker) {
            ((Invoker) operand).getParameters().add(argument);
            if (!last) {
                path.push(new CallReceiver(this));
            }
        } else {
            error("previous operand is not invocable");
        }
    }

    @Override
    public void pushToken(PathContext context, ExpressionContextKeeper scope, String name) throws WrimeException {
        if (operand == null) {
            TypeDef def = scope.current().getVar(name);
            if (def == null) {
                error("unknown variable or method name '" + name + "'");
            }
            Variable getter = new Variable();
            getter.setVar(name);
            getter.setResult(def);
            operand = getter;
        } else if (expectInvoker) {
            Operand invoker = scope.findInvoker(operand.getResult(), name);
            if (invoker instanceof Getter) {
                ((Getter) invoker).setInvocable(operand);
            } else if (invoker instanceof Invoker) {
                ((Invoker) invoker).setInvocable(operand);
            } else {
                error("unknown invoker");
            }
            operand = invoker;
        } else {
            error("unexpected token");
        }
    }
}
