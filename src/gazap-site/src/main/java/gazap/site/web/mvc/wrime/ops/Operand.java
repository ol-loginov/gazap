package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.TypeDef;

public abstract class Operand {
    private TypeDef result;
    private boolean statement = true;

    public boolean isStatement() {
        return statement;
    }

    public TypeDef getResult() {
        return result;
    }

    public void setResult(TypeDef result) {
        this.result = result;
    }
}
