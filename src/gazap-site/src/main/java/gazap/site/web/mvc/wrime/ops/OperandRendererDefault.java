package gazap.site.web.mvc.wrime.ops;

import gazap.site.web.mvc.wrime.EscapeUtils;
import gazap.site.web.mvc.wrime.WrimeException;

import java.io.IOException;
import java.io.Writer;

public abstract class OperandRendererDefault implements OperandRenderer, FunctorRenderer {
    private FunctorRenderer functorRenderer;

    protected OperandRendererDefault() {
        functorRenderer = this;
    }

    @Override
    public void setCustomRenderer(FunctorRenderer renderer) {
        this.functorRenderer = renderer;
    }

    protected void render(Operand operand, Writer writer) throws IOException, WrimeException {
        if (operand instanceof Variable) {
            render0((Variable) operand, writer);
        } else if (operand instanceof Invoker) {
            render0((Invoker) operand, writer);
        } else if (operand instanceof Getter) {
            render0((Getter) operand, writer);
        } else if (operand instanceof Chain) {
            render0((Chain) operand, writer);
        } else if (operand instanceof Raw) {
            render0((Raw) operand, writer);
        } else if (operand instanceof Literal) {
            render0((Literal) operand, writer);
        } else if (operand instanceof Functor) {
            functorRenderer.render((Functor) operand, writer);
        } else {
            throw new WrimeException("internal error - operand " + operand.getClass() + " not implemented", null);
        }
    }

    @Override
    public void render(Functor operand, Writer writer) throws IOException {
        writer.append(operand.getName());
    }

    private void render0(Chain operand, Writer writer) throws WrimeException, IOException {
        for (Operand child : operand.getOperands()) {
            render(child, writer);
        }
    }

    private void render0(Literal operand, Writer writer) throws IOException {
        writer.append('"').append(EscapeUtils.escapeJavaString(operand.getText())).append('"');
    }

    private void render0(Raw operand, Writer writer) throws IOException {
        writer.append(operand.getText());
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
