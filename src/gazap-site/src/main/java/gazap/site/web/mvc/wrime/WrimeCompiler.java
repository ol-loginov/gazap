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
        builder.append("W_");
        for (char ch : name.toCharArray()) {
            if (Character.isJavaIdentifierPart(ch)) {
                builder.append(ch);
            } else {
                builder.append("$").append((int) ch);
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

    private void addImport(Class clazz) {
        importNames.add(clazz.getName());
    }

    @Override
    public void startResource(ScriptResource resource) throws WrimeException {
        addImport(java.io.Writer.class);
        addImport(java.lang.String.class);
        addImport(java.lang.Object.class);
        addImport(java.lang.Exception.class);
        addImport(java.util.Map.class);
        className = toIdentifier(resource.getPath());
    }

    @Override
    public void finishResource() throws WrimeException {
        classDone = true;
    }

    @Override
    public void text(String text) {
        if (text != null && text.length() > 0) {
            renderContentBody.l(String.format("write(\"%s\");", StringEscapeUtils.escapeJava(text)));
        }
    }

    @Override
    public void exprStart() {
    }

    @Override
    public void exprFinish() {
    }

    @Override
    public void exprListOpen() {
    }

    @Override
    public void exprListClose() {
    }

    @Override
    public void exprName(String name) {
    }

    @Override
    public void exprLiteral(String literal) {
    }

    @Override
    public void exprComma() {
    }

    @Override
    public void exprColon() {
    }

    @Override
    public void exprDot() {
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
}
