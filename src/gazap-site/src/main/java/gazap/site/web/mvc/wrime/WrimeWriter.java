package gazap.site.web.mvc.wrime;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public abstract class WrimeWriter {
    private final Writer writer;

    protected WrimeWriter(Writer writer) {
        this.writer = writer;
    }

    public void render(Map<String, Object> model) {
        clear();
        assignFields(model);
        renderContent();
    }

    protected abstract void renderContent();

    protected abstract void clear();

    protected abstract void assignFields(Map<String, Object> model);

    protected void write(String text) throws IOException {
        if (text == null || text.length() == 0) {
            return;
        }
        writer.write(text);
    }
}
