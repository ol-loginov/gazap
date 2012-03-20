package gazap.site.web.mvc.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.UUID;

public class ParamPut extends TagSupport {
    public static final String NAME_PREFIX = "gazap.site.web.mvc.jstl.VAR.";

    protected String name;
    protected Object value;
    protected String valueName;


    @Override
    public void release() {
        super.release();
        value = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Object value) {
        this.value = value;
        this.valueName = UUID.randomUUID().toString();
    }

    @Override
    public int doEndTag() throws JspException {
        if (name != null && !name.equals("")) {
            pageContext.setAttribute(NAME_PREFIX + name, valueName, PageContext.PAGE_SCOPE);
            pageContext.setAttribute(NAME_PREFIX + valueName, value, PageContext.PAGE_SCOPE);
        }
        return EVAL_PAGE;
    }
}
