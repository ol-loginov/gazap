package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.WrimeException;

import java.io.IOException;
import java.io.Writer;

public interface FunctorRenderer {
    void render(Functor operand, Writer writer) throws WrimeException, IOException;
}
