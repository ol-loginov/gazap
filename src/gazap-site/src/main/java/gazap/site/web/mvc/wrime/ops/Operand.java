package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.TypeName;

public abstract class Operand {
    private TypeName result;
    private boolean statement = true;

    public boolean isStatement() {
        return statement;
    }

    public TypeName getResult() {
        return result;
    }

    public void setResult(TypeName result) {
        this.result = result;
    }
}
