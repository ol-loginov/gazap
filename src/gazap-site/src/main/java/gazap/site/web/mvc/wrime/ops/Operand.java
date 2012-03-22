package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.TypeDef;

public abstract class Operand {
    private TypeDef result;

    public TypeDef getResult() {
        return result;
    }

    public void setResult(TypeDef result) {
        this.result = result;
    }
}
