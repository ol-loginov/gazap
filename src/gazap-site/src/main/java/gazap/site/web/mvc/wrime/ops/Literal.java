package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.TypeDef;

public class Literal extends Operand {
    private final String text;

    public Literal(String text) {
        this.text = text;
        setResult(new TypeDef(String.class));
    }

    public String getText() {
        return text;
    }
}
