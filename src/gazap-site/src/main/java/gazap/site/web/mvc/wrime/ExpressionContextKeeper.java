package gazap.site.web.mvc.wrime;

public interface ExpressionContextKeeper {
    ExpressionContext current();

    Class findClass(String className);

    void addImport(String className);

    void addModelParameter(String parameterTypeDef, String parameterName, Class parameterClass) throws WrimeException;

    ExpressionContext openScope();

    ExpressionContext closeScope();

    TypeDef findFunctor(String name);
}
