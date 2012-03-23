package gazap.site.web.mvc.wrime;

public class EscapeUtils {
    public static String escapeJavaString(String text) {
        return text.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
