package gazap.site.web.mvc.support;

import gazap.site.services.FormatService;

@SuppressWarnings("UnusedDeclaration")
public class XsltViewResources {
    private final FormatService f;

    public XsltViewResources(FormatService formatService) {
        this.f = formatService;
    }

    public String t(String key) {
        return translate(key);
    }

    public String t(String key, String arg1) {
        return translate(key, arg1);
    }

    public String t(String key, String arg1, String arg2) {
        return translate(key, arg1, arg2);
    }

    public String t(String key, String arg1, String arg2, String arg3) {
        return translate(key, arg1, arg2, arg3);
    }

    private String translate(String code, Object... args) {
        return f.getMessage(code, args);
    }

    public String p(String code, String number) {
        if (number == null) {
            return null;
        }
        return f.pluralize(Long.parseLong(number), code);
    }
}
