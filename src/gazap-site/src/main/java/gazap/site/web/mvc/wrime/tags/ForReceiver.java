package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.*;
import gazap.site.web.mvc.wrime.ops.Chain;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.Raw;
import gazap.site.web.mvc.wrime.tree.CallReceiver;
import gazap.site.web.mvc.wrime.tree.PathReceiver;

public class ForReceiver extends PathReceiver {
    private enum Status {
        NO_DECISION,
        WAIT_VAR,
        WAIT_ITERATOR,
        COMPLETE
    }

    private Status status = Status.NO_DECISION;

    private String varName = "";
    private TypeDef varType;

    private Operand iterator;

    @Override
    public void beginList(ExpressionContextKeeper scope) throws WrimeException {
        switch (status) {
            case NO_DECISION:
                status = Status.WAIT_VAR;
                break;
            default:
                errorUnexpected("(");
        }
    }

    @Override
    public void complete(ExpressionContextKeeper scope) throws WrimeException {
        Chain chain;
        switch (status) {
            case COMPLETE:
                TypeWrap iterableType = TypeWrap.create(iterator.getResult().getType());
                TypeWrap iteratorType = null;
                if (iterableType.isAssignableTo(Iterable.class)) {
                    iteratorType = TypeWrap.create(iterableType.getTypeParameterOf(Iterable.class, 0));
                } else if (iterableType.isArray()) {
                    iteratorType = TypeWrap.create(iterableType.getComponentType());
                } else {
                    error("call is not iterable");
                }

                chain = new Chain();
                chain.getOperands().add(new Raw(String.format("for(%s %s : ", iteratorType.getJavaSourceName(), varName)));
                chain.getOperands().add(iterator);
                chain.getOperands().add(new Raw(") {"));
                path.render(chain);

                ExpressionContext context = scope.openScope();
                context.addAttribute(ForFactory.LOOP_SCOPE);
                context.addVar(varName, new TypeDef(iteratorType.getType()));

                break;
            case NO_DECISION:
                scope.closeScope();

                chain = new Chain();
                chain.getOperands().add(new Raw("}"));
                path.render(chain);

                break;
            default:
                error("${loop(...)} is incomplete");
        }
    }


    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
        switch (status) {
            case WAIT_VAR:
                varName += name;
                break;
            default:
                errorUnexpected(name);
        }
    }

    @Override
    public void pushDelimiter(ExpressionContextKeeper scope, String delimiter) throws WrimeException {
        switch (status) {
            case WAIT_VAR:
                if (":".equals(delimiter)) {
                    status = Status.WAIT_ITERATOR;
                    path.push(new CallReceiver(createCloser()));
                    return;
                }
            default:
                errorUnexpected(delimiter);
        }
    }

    private CallReceiver.CloseCallback createCloser() {
        return new CallReceiver.CloseCallback() {
            @Override
            public void complete(CallReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException {
                path.remove(child);
                if (!last) {
                    error("only one expression allowed for iterator");
                }
                iterator = child.getOperand();
                status = Status.COMPLETE;
            }
        };
    }
}
