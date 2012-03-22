package gazap.site.web.mvc.wrime;

import java.util.HashMap;
import java.util.Map;

public class ExpressionContext {
    private final ClassLoader classLoader;
    private final ExpressionContext parentContext;

    private Map<String, TypeDef> localVariables = new HashMap<String, TypeDef>();

    public ExpressionContext(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.parentContext = null;
    }

    public ExpressionContext(ExpressionContext parentContext, ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.parentContext = parentContext;
    }

    public ClassLoader getClassLoader() {
        return this.classLoader;
    }

    public void addVar(String name, Class classDef) throws WrimeException {
        if (localVariables.containsKey(name)) {
            throw new WrimeException("Variable named " + name + "  is already in scope", null);
        }
        TypeDef def = new TypeDef();
        def.setClazz(classDef);
        localVariables.put(name, def);
    }

    public TypeDef getVar(String name) {
        TypeDef classDef = localVariables.get(name);
        if (classDef == null && parentContext != null) {
            return parentContext.getVar(name);
        }
        return null;
    }
}
