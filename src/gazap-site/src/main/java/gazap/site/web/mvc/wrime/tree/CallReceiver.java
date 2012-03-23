package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.TypeDef;
import gazap.site.web.mvc.wrime.TypeUtil;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.*;

public class CallReceiver extends PathReceiver {
    public static interface CloseCallback {
        void complete(CallReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException;
    }

    private enum Expect {
        NONE,
        INVOKER
    }

    private final CloseCallback closer;
    private Operand operand;
    private Expect expect = Expect.NONE;

    public CallReceiver() {
        this(null);
    }

    public CallReceiver(CloseCallback closer) {
        this.closer = closer;
    }

    public Operand getOperand() {
        return operand;
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
                addOperand(child.getOperand());
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
        Invoker confirmation = TypeUtil.findInvoker(invoker.getInvocable().getResult(), invoker.getMethodName(), parameterTypes);
        if (confirmation == null) {
            error("cannot find suitable method with name '" + invoker.getMethodName() + "'");
        }
        invoker.setMethod(confirmation.getMethod());
        invoker.setResult(confirmation.getResult());
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
        } else if (".".equals(delimiter) || ":".equals(delimiter)) {
            if (operand == null || operand.getResult().isVoid()) {
                error("no invocable at the point");
            }
            expect = Expect.INVOKER;
            return;
        }
        errorUnexpected(delimiter);
    }

    @Override
    public void pushLiteral(ExpressionContextKeeper scope, String literal) throws WrimeException {
        if (operand == null) {
            operand = new Literal(literal);
        } else {
            error("literal is not expected in this point");
        }
    }

    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
        if (operand == null) {
            if (scope.current().getVar(name) != null) {
                Variable getter = new Variable();
                getter.setVar(name);
                getter.setResult(scope.current().getVar(name));
                operand = getter;
            } else if (scope.findFunctor(name) != null) {
                Functor functor = new Functor();
                functor.setName(name);
                functor.setResult(scope.findFunctor(name));
                operand = functor;
            } else {
                if (path.depth() > 2) {
                    error("unknown variable or functor '" + name + "'");
                } else {
                    error("unknown tag, variable or functor '" + name + "'");
                }
            }
        } else if (expect == Expect.INVOKER) {
            Operand invoker = TypeUtil.findAnyInvokerOrGetter(operand.getResult(), name);
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
