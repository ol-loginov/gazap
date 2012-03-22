package gazap.site.web.mvc.wrime;

import gazap.site.web.mvc.wrime.ops.Operand;
import gazap.site.web.mvc.wrime.ops.OperandRendererDefault;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Pattern;

public class WrimeCompiler {
    private static final String EOL = System.getProperty("line.separator");
    private static final String SCOPE_IDENT = "  ";

    private final WrimeEngine engine;

    private final Body renderContentBody;

    private ExpressionTreeBuilder expressionTreeBuilder;
    private ExpressionContextImpl expressionContext;

    private boolean classDone;
    private String className;

    private List<String> importNames = new ArrayList<String>();
    private Map<String, TypeDef> parameterNames = new HashMap<String, TypeDef>();

    public WrimeCompiler(WrimeEngine engine) {
        this.engine = engine;
        renderContentBody = new Body();
        expressionContext = new ExpressionContextImpl(this, engine.getRootLoader());
    }

    private void error(String name) throws WrimeException {
        error(name, null);
    }

    private void error(String name, Throwable cause) throws WrimeException {
        throw new WrimeException("Compiler exception: " + name, cause);
    }

    private static boolean isIdentifier(String name) {
        boolean firstPassed = false;
        boolean valid = true;
        for (char ch : name.toCharArray()) {
            if (!firstPassed) {
                valid = Character.isJavaIdentifierStart(ch);
                firstPassed = true;
            } else {
                valid &= Character.isJavaIdentifierPart(ch);
            }
        }
        return firstPassed && valid;
    }

    private static String toIdentifier(String name) throws WrimeException {
        StringBuilder builder = new StringBuilder();
        builder.append("W$");
        for (char ch : name.toCharArray()) {
            if (Character.isJavaIdentifierPart(ch)) {
                builder.append(ch);
            } else if ('/' == ch || '\'' == ch) {
                builder.append("$");
            } else if ('.' == ch) {
                builder.append("_");
            } else {
                builder.append((int) ch);
            }
        }
        return builder.toString();
    }

    public String getClassCode() {
        Body body = new Body();
        for (String name : importNames) {
            body.a(String.format("import %s;", name)).nl();
        }

        body.nl().l(String.format("public class %s extends %s {", className, WrimeWriter.class.getName()))
                .scope()

                .a(new ModelParameterListDeclarator())

                .l(String.format("public %s(Writer writer) {", className))
                .scope().l("super(writer);").leave()
                .l("}").nl()

                .l(String.format("protected void clear() {"))
                .scope().a(new ModelParameterListCleaner()).l("super.clear();").leave()
                .l("}").nl()

                .l(String.format("protected void assignFields(Map<String, Object> model) {"))
                .scope().l("super.assignFields(model);").a(new ModelParameterListInitializer()).leave()
                .l("}").nl()

                .l(String.format("protected void renderContent() throws Exception {"))
                .scope().a(renderContentBody).leave()
                .l("}")

                .leave()
                .a("}");
        return body.toString();
    }

    private void ensureNotReady() throws WrimeException {
        if (classDone) {
            error("class is ready");
        }
    }

    private void ensureInsideExpression(boolean shouldHaveExpression) throws WrimeException {
        if (expressionTreeBuilder != null ^ shouldHaveExpression) {
            error("unexpected expression statement");
        }
    }

    private void completeExpression() throws WrimeException {
        expressionTreeBuilder.getContext().markComplete(expressionContext);
        expressionTreeBuilder = null;
    }

    private void insideExpression() {
        expressionTreeBuilder = new ExpressionTreeBuilder(new DirectCallRenderer());
    }

    public void addImport(String clazz) {
        importNames.add(clazz);
    }

    public Collection<String> getImports() {
        return importNames;
    }

    public WrimeScanner.Receiver createReceiver() {
        return new ScannerReceiver();
    }

    public void addModelParameter(String parameterName, String parameterTypeDef, Class parameterClass) throws WrimeException {
        if (!isIdentifier(parameterName)) {
            error("not a Java identifier " + parameterName);
        }
        if (parameterNames.containsKey(parameterName)) {
            error("duplicate for model parameter " + parameterName);
        }
        TypeDef def = new TypeDef();
        def.setClazz(parameterClass);
        def.setAlias(parameterTypeDef);
        parameterNames.put(parameterName, def);
        expressionContext.addVar(parameterName, parameterClass);
    }

    public TypeDef getModelParameter(String name) {
        return parameterNames.get(name);
    }

    private static class Body {
        private final StringBuilder body;
        private final String prefix;

        public Body() {
            this(new StringBuilder(), "");
        }

        public Body(StringBuilder body, String prefix) {
            this.body = body;
            this.prefix = prefix;
        }

        public Body a(String text) {
            body.append(prefix).append(text);
            return this;
        }

        public Body l(String text) {
            return a(text).nl();
        }

        public Body a(Body other) {
            Scanner scanner = new Scanner(new StringReader(other.toString()));
            scanner.useDelimiter(Pattern.compile(Pattern.quote(EOL)));
            while (scanner.hasNext()) {
                a(scanner.next());
            }
            if (other.body.length() > 0) {
                nl();
            }
            return this;
        }

        public Body nl() {
            body.append(EOL);
            return this;
        }

        public String toString() {
            return body.toString();
        }

        public Body scope() {
            return new Body(body, prefix + SCOPE_IDENT);
        }

        public Body leave() {
            return new Body(body, prefix.substring(SCOPE_IDENT.length()));
        }

        public Body a(BodyCallback callback) {
            callback.in(this);
            return this;
        }
    }

    private interface BodyCallback {
        void in(Body body);
    }

    private class ModelParameterListDeclarator implements BodyCallback {
        @Override
        public void in(Body body) {
            if (parameterNames.size() == 0) {
                return;
            }
            for (Map.Entry<String, TypeDef> var : parameterNames.entrySet()) {
                body.a(String.format("private %s %s;", var.getValue().getAlias(), var.getKey())).nl();
            }
            body.nl();
        }
    }

    private class ModelParameterListCleaner implements BodyCallback {
        @Override
        public void in(Body body) {
            if (parameterNames.size() == 0) {
                return;
            }
            for (Map.Entry<String, TypeDef> var : parameterNames.entrySet()) {
                body.a(String.format("this.%s=%s;", var.getKey(), Defaults.getDefaultValueString(var.getValue().getClazz())))
                        .nl();
            }
        }
    }

    private class ModelParameterListInitializer implements BodyCallback {
        @Override
        public void in(Body body) {
            if (parameterNames.size() == 0) {
                return;
            }
            for (Map.Entry<String, TypeDef> var : parameterNames.entrySet()) {
                body.a(String.format("this.%s=(%s)model.get(\"%s\");", var.getKey(), var.getValue().getAlias(), StringEscapeUtils.escapeJava(var.getKey())))
                        .nl();
            }
        }
    }

    public static class Defaults {
        // These gets initialized to their default values
        private static boolean DEFAULT_BOOLEAN;
        private static byte DEFAULT_BYTE;
        private static short DEFAULT_SHORT;
        private static int DEFAULT_INT;
        private static long DEFAULT_LONG;
        private static float DEFAULT_FLOAT;
        private static double DEFAULT_DOUBLE;

        public static Object getDefaultValueString(Class clazz) {
            if (clazz.equals(boolean.class)) {
                return DEFAULT_BOOLEAN;
            } else if (clazz.equals(byte.class)) {
                return DEFAULT_BYTE;
            } else if (clazz.equals(short.class)) {
                return DEFAULT_SHORT;
            } else if (clazz.equals(int.class)) {
                return DEFAULT_INT;
            } else if (clazz.equals(long.class)) {
                return DEFAULT_LONG;
            } else if (clazz.equals(float.class)) {
                return DEFAULT_FLOAT;
            } else if (clazz.equals(double.class)) {
                return DEFAULT_DOUBLE;
            } else {
                return "null";
            }
        }
    }

    private class ScannerReceiver implements WrimeScanner.Receiver {
        @Override
        public void startResource(ScriptResource resource) throws WrimeException {
            ensureNotReady();
            expressionContext.addImport(java.io.Writer.class);
            expressionContext.addImport("java.lang.*");
            expressionContext.addImport("java.util.*");
            className = toIdentifier(resource.getPath());
        }

        @Override
        public void finishResource() throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(false);
            classDone = true;
        }

        @Override
        public void text(String text) throws WrimeException {
            ensureNotReady();
            if (text != null && text.length() > 0) {
                renderContentBody.l(String.format("write(\"%s\");", StringEscapeUtils.escapeJava(text)));
            }
        }

        @Override
        public void exprStart() throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(false);
            insideExpression();
        }

        @Override
        public void exprFinish() throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            completeExpression();
        }

        @Override
        public void exprListOpen() throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            expressionTreeBuilder.receiver().beginList(expressionTreeBuilder.getContext(), expressionContext);
        }

        @Override
        public void exprListClose() throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            expressionTreeBuilder.receiver().closeList(expressionTreeBuilder.getContext(), expressionContext);
        }

        @Override
        public void exprName(String name) throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            expressionTreeBuilder.receiver().pushToken(expressionTreeBuilder.getContext(), expressionContext, name);
        }

        @Override
        public void exprLiteral(String literal) throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            expressionTreeBuilder.receiver().pushLiteral(expressionTreeBuilder.getContext(), expressionContext, literal);
        }

        @Override
        public void exprDelimiter(String value) throws WrimeException {
            ensureNotReady();
            ensureInsideExpression(true);
            expressionTreeBuilder.receiver().pushDelimiter(expressionTreeBuilder.getContext(), expressionContext, value);
        }
    }

    private class DirectCallRenderer extends OperandRendererDefault {
        @Override
        public void render(Operand operand) throws WrimeException {
            if (operand == null) {
                return;
            }

            StringWriter writer = new StringWriter();
            try {
                super.render(operand, writer);
            } catch (IOException e) {
                error("writer error", e);
            }
            if (isWritable(operand.getResult())) {
                renderContentBody.a("write(").a(writer.toString()).a(");").nl();
            }
        }

        private boolean isWritable(TypeDef def) {
            return def != null && def.getClazz() != null && !def.getClazz().equals(Void.TYPE);
        }
    }
}
