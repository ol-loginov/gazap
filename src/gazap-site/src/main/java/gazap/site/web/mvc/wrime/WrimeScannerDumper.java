package gazap.site.web.mvc.wrime;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.Writer;

public class WrimeScannerDumper implements WrimeScanner.Receiver {
    private final Writer writer;
    private final String resourceBasePath;

    public WrimeScannerDumper(Writer writer, String resourceBasePath) {
        this.writer = writer;
        this.resourceBasePath = resourceBasePath;
    }

    public WrimeScannerDumper(Writer writer) {
        this(writer, null);
    }

    private String baselineResourcePath(String resourcePath) {
        if (resourceBasePath != null && resourcePath.startsWith(resourceBasePath)) {
            return resourcePath.substring(resourceBasePath.length());
        }
        return resourcePath;
    }

    private String getResourcePath(Resource resource) {
        try {
            return baselineResourcePath(resource.getURL().toString());
        } catch (IOException e) {
            return "<resource is not locatable>";
        }
    }

    @Override
    public void startResource(Resource resource) {
        appendQuietly("[enter resource " + getResourcePath(resource) + "]");
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
