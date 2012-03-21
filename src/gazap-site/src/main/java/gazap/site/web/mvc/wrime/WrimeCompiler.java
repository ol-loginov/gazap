package gazap.site.web.mvc.wrime;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WrimeCompiler implements WrimeScanner.Receiver {
    private static final String EOL = System.getProperty("line.separator");
    private static final String SCOPE_IDENT = "  ";

    private final WrimeEngine engine;

    private final Body clearBody;
    private final Body assignFieldsBody;
    private final Body renderContentBody;

    private ExpressionBuilder expression;

    private boolean classDone;
    private String className;

    private List<String> importNames = new ArrayList<String>();

    public WrimeCompiler(WrimeEngine engine) {
        this.engine = engine;
        clearBody = new Body();
        assignFieldsBody = new Body();
        renderContentBody = new Body();
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

                .l(String.format("public %s(Writer writer) {", className))
                .scope().l("super(writer);").leave()
                .l("}")

                .l(String.format("protected void clear() {"))
                .scope().a(clearBody).l("super.clear();").leave()
                .l("}")

                .l(String.format("protected void assignFields(Map<String, Object> model) {"))
                .scope().l("super.assignFields(model);").a(assignFieldsBody).leave()
                .l("}")

                .l(String.format("protected void renderContent() throws Exception {"))
                .scope().a(renderContentBody).leave()
                .l("}")

                .leave()
                .a("}");
        return body.toString();
    }

    private void ensureNotReady() throws WrimeException {
        if (classDone) {
            throw new WrimeException("Class is ready", null);
        }
    }

    private void ensureInsideExpression(boolean shouldHaveExpression) throws WrimeException {
        if (expression != null ^ shouldHaveExpression) {
            throw new WrimeException("Unexpected expression statement", null);
        }
    }

    private void completeExpression() throws WrimeException {
        if (expression.complete()) {
            throw new WrimeException("Unexpected expression end", null);
        }
    }

    private void insideExpression() {
        expression = new ExpressionBuilder(new ExpressionContextKeeperImpl());
    }

    private void addImport(Class clazz) {
        addImport(clazz.getName());
    }

    private void addImport(String clazz) {
        importNames.add(clazz);
    }

    @Override
    public void startResource(ScriptResource resource) throws WrimeException {
        ensureNotReady();
        addImport(java.io.Writer.class);
        addImport("java.lang.*");
        addImport("java.util.*");
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
        expression.beginList();
    }

    @Override
    public void exprListClose() throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression.closeList();
    }

    @Override
    public void exprName(String name) throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression = expression.pushToken(name);
    }

    @Override
    public void exprLiteral(String literal) throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression.pushLiteral(literal);
    }

    @Override
    public void exprComma() throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression.nextListItem();
    }

    @Override
    public void exprColon() throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression.pushColon();
    }

    @Override
    public void exprDot() throws WrimeException {
        ensureNotReady();
        ensureInsideExpression(true);
        expression.pushDot();
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
    }

    private class ExpressionContextKeeperImpl implements ExpressionContextKeeper {
    }
}
