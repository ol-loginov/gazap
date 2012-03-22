package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.ops.Invoker;
import gazap.site.web.mvc.wrime.ops.Operand;

public interface ExpressionContextKeeper {
    ExpressionContext current();

    Class findClass(String className);

    void addImport(String className);

    void addModelParameter(String parameterTypeDef, String parameterName, Class parameterClass) throws WrimeException;

    Operand findAnyInvokerOrGetter(TypeDef invocable, String method);

    Invoker findInvoker(TypeDef invocable, String method, TypeDef... argumentTypes);
}
