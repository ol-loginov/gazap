package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.WrimeScanner;
import gazap.site.web.mvc.wrime.ops.Operand;

/**
 * Accepts and validate syntax like "var_name : field.method().foo"
 */
public class AssignReceiver extends PathReceiver {
    enum Status {
        WAIT_VAR,
        WAIT_COLON,
        WAIT_CALL,
        COMPLETE
    }

    private Status status = Status.WAIT_VAR;

    private String alias;
    private Operand source;

    private ReceiverCallback aliasValidator;
    private CompleteCallback completeCallback;

    public String getAlias() {
        return alias;
    }

    public Operand getSource() {
        return source;
    }

    public AssignReceiver setAliasValidator(ReceiverCallback aliasValidator) {
        this.aliasValidator = aliasValidator;
        return this;
    }

    public AssignReceiver setCompleteCallback(CompleteCallback callback) {
        this.completeCallback = callback;
        return this;
    }

    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
        switch (status) {
            case WAIT_VAR:
                status = Status.WAIT_COLON;
                alias = name;
                validateAlias();
                break;
            default:
                errorUnexpected(name);
        }
    }

    private void validateAlias() {
        if (aliasValidator == null) {
            return;
        }
        aliasValidator.on(this);
    }

    @Override
    public void pushDelimiter(ExpressionContextKeeper scope, String delimiter) throws WrimeException {
        switch (status) {
            case WAIT_COLON:
                if (":".equals(delimiter)) {
                    status = Status.WAIT_CALL;
                    path.push(new CallReceiver(createCloser()));
                } else if (WrimeScanner.SPLIT_LIST_SYMBOL.equals(delimiter)) {
                    markComplete(scope);
                } else {
                    errorUnexpected(delimiter);
                }
                break;
            default:
                errorUnexpected(delimiter);
        }
    }

    private CompleteCallback createCloser() {
        return new CompleteCallback() {
            @Override
            public void complete(PathReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException {
                path.remove(child);
                if (!last) {
                    error("only one expression allowed here");
                }

                source = ((CallReceiver) child).getOperand();
                markComplete(scope);
            }
        };
    }

    private void markComplete(ExpressionContextKeeper scope) throws WrimeException {
        status = Status.COMPLETE;
        if (completeCallback != null) {
            completeCallback.complete(AssignReceiver.this, scope, true);
        }
    }
}
