package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.WrimeException;

import java.io.IOException;
import java.io.Writer;

public abstract class OperandRendererDefault implements OperandRenderer {
    protected void render(Operand operand, Writer writer) throws IOException, WrimeException {
        if (operand instanceof Variable) {
            render0((Variable) operand, writer);
        } else if (operand instanceof Invoker) {
            render0((Invoker) operand, writer);
        } else if (operand instanceof Getter) {
            render0((Getter) operand, writer);
        } else {
            throw new WrimeException("internal error - operand " + operand.getClass() + " not implemented", null);
        }
    }

    private void render0(Getter operand, Writer writer) throws WrimeException, IOException {
        render(operand.getInvocable(), writer);
        writer.append(".").append(operand.getPropMethod().getName()).append("()");
    }

    private void render0(Invoker operand, Writer writer) throws WrimeException, IOException {
        render(operand.getInvocable(), writer);
        writer.append(".").append(operand.getMethod().getName()).append("(");
        boolean firstParameter = true;
        for (Operand parameter : operand.getParameters()) {
            if (!firstParameter) {
                writer.append(",");
            }
            firstParameter = false;
            render(parameter, writer);
        }
        writer.append(")");
    }

    private void render0(Variable operand, Writer writer) throws IOException {
        writer.write(operand.getVar());
    }
}
