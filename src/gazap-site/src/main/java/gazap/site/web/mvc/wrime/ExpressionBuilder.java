package gazap.site.web.mvc.wrime;

public class ExpressionBuilder {
    private ExpressionContextKeeper contextKeeper;

    private boolean loopModeEnabled;

    public ExpressionBuilder(ExpressionContextKeeper contextKeeper) {
        this.contextKeeper = contextKeeper;
    }

    public boolean complete() {
        return false;
    }

    public void beginList() {
    }

    public void closeList() {
    }

    public ExpressionBuilder pushToken(String name) {
        if (loopModeEnabled && "loop".equals(name)) {
            return new ExpressionBuilderForLoop(contextKeeper);
        }
        loopModeEnabled = false;
        return this;
    }

    public void pushLiteral(String literal) {
    }

    public void nextListItem() {
    }

    public void pushDot() {
    }
}
