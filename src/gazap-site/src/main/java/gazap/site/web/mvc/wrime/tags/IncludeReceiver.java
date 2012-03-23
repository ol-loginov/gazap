package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.ops.Chain;
import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.Raw;

import java.util.ArrayList;
import java.util.List;

public class IncludeReceiver extends PathReceiver {
    enum Status {
        WAIT_START,
        WAIT_PATH,
        WAIT_PARAMETER,
        COMPLETE
    }

    static class ModelParameter {
        String name;
        Operand value;
    }

    private Status status = Status.WAIT_START;

    private Operand templatePath;
    private List<ModelParameter> templateModel = new ArrayList<ModelParameter>();

    @Override
    public void error(String message) throws WrimeException {
        super.error("incomplete ${include ...} expression, " + message);
    }

    @Override
    public void beginList(ExpressionContextKeeper scope) throws WrimeException {
        switch (status) {
            case WAIT_START:
                path.push(new CallReceiver(createPathCloser()));
                break;
            default:
                errorUnexpected("(");
        }
    }

    private CallReceiver.CloseCallback createPathCloser() {
        return new CallReceiver.CloseCallback() {
            @Override
            public void complete(CallReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException {
                path.remove(child);
                status = last ? Status.COMPLETE : Status.WAIT_PARAMETER;
                templatePath = child.getOperand();
            }
        };
    }

    @Override
    public String getHumanName() {
        return "Template includer";
    }

    @Override
    public void complete(ExpressionContextKeeper scope) throws WrimeException {
        switch (status) {
            case COMPLETE:
                Chain chain = new Chain();

                String model;
                if (templateModel.size() > 0) {
                    model = String.format("$includeAt_%d_%d", path.getLine(), path.getColumn());
                    chain.getOperands().add(new Raw(String.format("ModelMap %s = new ModelMap();\n", model)));
                } else {
                    model = "null";
                }

                chain.getOperands().add(new Raw(String.format("this.getEngine().include(this,")));
                chain.getOperands().add(templatePath);
                chain.getOperands().add(new Raw(String.format(", %s);", model)));
                path.render(chain);
                break;
            default:
                error("waiting for more parameters");
        }
    }
}
