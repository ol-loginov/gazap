package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.TypeName;

public class Literal extends Operand {
    private final String text;

    public Literal(String text) {
        this.text = text;
        setResult(new TypeName(String.class));
    }

    public String getText() {
        return text;
    }
}
