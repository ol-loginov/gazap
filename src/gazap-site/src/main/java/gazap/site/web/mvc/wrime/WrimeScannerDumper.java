package gazap.site.web.mvc.wrime;

import java.io.IOException;
import java.io.Writer;

public class WrimeScannerDumper implements WrimeScanner.Receiver {
    private final Writer writer;

    public WrimeScannerDumper(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void startResource(ScriptResource resource) {
        appendQuietly("[enter resource " + resource.getPath() + "]");
    }

    private void appendQuietly(String text) {
        try {
            writer.append(text);
        } catch (IOException e) {
            // sorry for that
        }
    }

    @Override
    public void finishResource() {
        appendQuietly("[leave resource]");
    }

    @Override
    public void text(String text) {
        appendQuietly("[write] " + text);
    }

    @Override
    public void exprStart() {
        appendQuietly("[expr]");
    }

    @Override
    public void exprFinish() {
        appendQuietly("[/expr]");
    }

    @Override
    public void exprListOpen() {
        appendQuietly("[list]");
    }

    @Override
    public void exprListClose() {
        appendQuietly("[/list]");
    }

    @Override
    public void exprName(String name) {
        appendQuietly("[$" + name + "]");
    }

    @Override
    public void exprLiteral(String literal) {
        appendQuietly("['" + literal + "']");
    }

    @Override
    public void exprComma() {
        appendQuietly("[,]");
    }

    @Override
    public void exprDot() {
        appendQuietly("[.]");
    }

    @Override
    public void exprColon() {
        appendQuietly("[:]");
    }
}
