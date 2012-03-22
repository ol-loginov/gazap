package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.WrimeException;

public interface OperandRenderer {
    void render(Operand operand) throws WrimeException;
}
