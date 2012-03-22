package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.TypeDef;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Getter;
import gazap.site.web.mvc.wrime.ops.Invoker;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.Variable;

public class CallReceiver extends PathReceiver {
    public static interface CloseCallback {
        void complete(CallReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException;
    }

    private final CloseCallback closer;
    private Operand operand;

    private boolean expectInvoker = false;

    public CallReceiver() {
        this(null);
    }

    public CallReceiver(CloseCallback closer) {
        this.closer = closer;
    }

    @Override
    public String getHumanName() {
        return "Expression analyser";
    }

    @Override
    public void complete(ExpressionContextKeeper scope) throws WrimeException {
        path.render(operand);
    }

    @Override
    public void beginList(ExpressionContextKeeper scope) throws WrimeException {
        if (operand instanceof Invoker) {
            path.push(new CallReceiver(createCloser()));
        } else {
            error("expected at function point only");
        }
    }

    private CloseCallback createCloser() {
        return new CloseCallback() {
            @Override
            public void complete(CallReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException {
                path.remove(child);
                addOperand(child.operand);
                if (!last) {
                    path.push(new CallReceiver(this));
                } else {
                    resolveInvoker(scope);
                }
            }
        };
    }

    @Override
    public void closeList(ExpressionContextKeeper scope) throws WrimeException {
        if (closer != null) {
            closer.complete(this, scope, true);
        } else {
            error("unexpected list closure");
        }
    }

    private void resolveInvoker(ExpressionContextKeeper scope) throws WrimeException {
        if (!(operand instanceof Invoker)) {
            error("operand supposed to be at function call only");
        }

        Invoker invoker = (Invoker) operand;
        TypeDef[] parameterTypes = new TypeDef[invoker.getParameters().size()];
        for (int i = 0; i < parameterTypes.length; ++i) {
            parameterTypes[i] = invoker.getParameters().get(i).getResult();
        }
        Invoker confirmation = scope.findInvoker(invoker.getInvocable().getResult(), invoker.getMethodName(), parameterTypes);
        if (confirmation == null) {
            error("cannot find suitable method with name '" + invoker.getMethodName() + "'");
        }
        invoker.setMethod(confirmation.getMethod());
        invoker.setResult(new TypeDef(confirmation.getMethod().getReturnType()));
    }

    private void addOperand(Operand argument) throws WrimeException {
        if (argument == null) {
            return;
        }
        if (operand instanceof Invoker) {
            ((Invoker) operand).getParameters().add(argument);
        } else {
            error("previous operand is not invocable");
        }
    }

    @Override
    public void pushDelimiter(ExpressionContextKeeper scope, String delimiter) throws WrimeException {
        if (",".equals(delimiter)) {
            if (closer != null) {
                closer.complete(this, scope, false);
                return;
            }
        } else if (".".equals(delimiter)) {
            if (operand == null || operand.getResult().isVoid()) {
                error("no invocable at the point");
            }
            expectInvoker = true;
            return;
        }
        errorUnexpected(delimiter);
    }

    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
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
            Operand invoker = scope.findAnyInvokerOrGetter(operand.getResult(), name);
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
