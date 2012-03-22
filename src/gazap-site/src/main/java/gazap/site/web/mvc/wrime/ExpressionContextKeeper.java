package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.ops.Operand;

public interface ExpressionContextKeeper {
    ExpressionContext current();

    Class findClass(String className);

    void addImport(String className);

    void addModelParameter(String parameterTypeDef, String parameterName, Class parameterClass) throws WrimeException;

    Operand findInvoker(TypeDef typeDef, String name);
}
