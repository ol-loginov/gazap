package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.ops.Getter;
import gazap.site.web.mvc.wrime.ops.Invoker;
import gazap.site.web.mvc.wrime.ops.Operand;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
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
            return getClassLoader().loadClass(paramType);
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
    public Operand findInvoker(TypeDef typeDef, String name) {
        PropertyDescriptor propDescriptor;
        try {
            propDescriptor = BeanUtils.getPropertyDescriptor(typeDef.getClazz(), name);
        } catch (BeansException be) {
            propDescriptor = null;
        }

        if (propDescriptor != null) {
            return createGetter(name, propDescriptor);
        }

        // no property found. try method then

        Method method;
        try {
            method = BeanUtils.findMethodWithMinimalParameters(typeDef.getClazz(), name);
        } catch (IllegalArgumentException ie) {
            return createInvoker(name, null);
        }

        return method != null ? createInvoker(name, method) : null;
    }

    private Getter createGetter(String name, PropertyDescriptor descriptor) {
        Getter getter = new Getter();
        getter.setPropName(name);
        getter.setPropMethod(descriptor.getReadMethod());
        getter.setResult(new TypeDef(getter.getPropMethod().getReturnType()));
        return getter;
    }

    private Invoker createInvoker(String name, Method method) {
        Invoker invoker = new Invoker();
        invoker.setMethodName(name);
        invoker.setMethod(method);
        if (method != null) {
            invoker.setResult(new TypeDef(method.getReturnType()));
        }
        return invoker;
    }
}
