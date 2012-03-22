package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;

public abstract class PathReceiver {
    public void errorUnexpected(String token) throws WrimeException {
        throw new WrimeException(getClass().getSimpleName() + " reports about unexpected token '" + token + "'", null);
    }

    public void error(String text) throws WrimeException {
        throw new WrimeException(getClass().getSimpleName() + " reports an error: " + text, null);
    }

    public void beginList(PathContext path, ExpressionContextKeeper scope) throws WrimeException {
        error("unexpected list");
    }

    public void closeList(PathContext path, ExpressionContextKeeper scope) throws WrimeException {
        error("unexpected list closure");
    }

    public void pushToken(PathContext path, ExpressionContextKeeper scope, String name) throws WrimeException {
        error("unexpected token");
    }

    public void pushLiteral(PathContext path, ExpressionContextKeeper scope, String literal) throws WrimeException {
        error("unexpected literal");
    }

    public void nextListItem(PathContext path, ExpressionContextKeeper scope) throws WrimeException {
        error("unexpected list sequence");
    }

    public void pushDelimiter(PathContext path, ExpressionContextKeeper scope, String delimiter) throws WrimeException {
        error("unexpected delimiter '" + delimiter + "'");
    }

    /**
     * Should be invoked only one
     *
     * @param context current context
     * @param scope   current variables scope
     * @throws WrimeException in any error
     */
    public void complete(PathContext context, ExpressionContextKeeper scope) throws WrimeException {
        error("unexpected expression end");
    }
}
