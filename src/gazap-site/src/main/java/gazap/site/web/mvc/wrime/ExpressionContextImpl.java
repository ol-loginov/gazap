package gazap.site.web.mvc.wrime;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionContextImpl extends ExpressionContext implements ExpressionContextKeeper {
    private final Stack<ExpressionContext> contextStack;
    private final WrimeCompiler compiler;

    public ExpressionContextImpl(WrimeCompiler compiler, ClassLoader classLoader) {
        super(classLoader);
        this.compiler = compiler;
        this.contextStack = new Stack<ExpressionContext>() {{
            push(ExpressionContextImpl.this);
        }};
    }

    @Override
    public ExpressionContext openScope() {
        compiler.scopeAdded();
        ExpressionContext child = new ExpressionContext(current(), getClassLoader());
        contextStack.push(child);
        return child;
    }

    @Override
    public ExpressionContext closeScope() {
        compiler.scopeRemoved();
        return contextStack.pop();
    }

    @Override
    public ExpressionContext current() {
        return contextStack.peek();
    }

    @Override
    public Class findClass(String className) {
        Class instance = tryClass(className);
        if (instance != null) {
            return instance;
        }
        String classSelfName = getPublicClassName(className);
        for (String imports : compiler.getImports()) {
            if (imports.endsWith("." + classSelfName)) {
                return tryClass(imports);
            }
            if (imports.endsWith(".*")) {
                instance = tryClass(combinePackageAndClass(imports, classSelfName));
                if (instance == null) {
                    continue;
                }
                // this should work with situation:
                // imports has "java.*";
                // className is "lang.String";
                if (imports.equals(instance.getPackage().getName() + ".*")) {
                    return instance;
                }
            }
        }
        return null;
    }

    private String combinePackageAndClass(String imports, String classSelfName) {
        return StringUtils.trimTrailingCharacter(imports, '*') + classSelfName;
    }

    private String getPublicClassName(String name) {
        Matcher matcher = Pattern.compile("[^.]+").matcher(name);
        if (matcher.find()) {
            return matcher.group();
        }
        return name;
    }

    public Class tryClass(String paramType) {
        try {
            return ClassUtils.forName(paramType, getClassLoader());
        } catch (LinkageError e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public void addImport(Class<?> className) {
        compiler.addImport(className.getName());
    }

    public void addImport(String className) {
        compiler.addImport(className);
    }

    @Override
    public void addModelParameter(String parameterTypeDef, String parameterName, Class parameterClass) throws WrimeException {
        compiler.addModelParameter(parameterName, parameterTypeDef, parameterClass);
    }

    @Override
    public TypeDef getVar(String name) {
        TypeDef def = super.getVar(name);
        return def != null ? def : compiler.getModelParameter(name);
    }

    @Override
    public TypeDef findFunctor(String name) {
        return compiler.findFunctor(name);
    }
}
