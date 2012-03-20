package gazap.site.web.mvc.jstl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ParamGet extends TagSupport {
    private String name;
    private String var;

    public void setVar(String var) {
        this.var = var;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doEndTag() throws JspException {
        String valueName = (String) pageContext.getAttribute(ParamPut.NAME_PREFIX + name);
        Object valueObject = pageContext.getAttribute(ParamPut.NAME_PREFIX + valueName);

        pageContext.removeAttribute(valueName);
        pageContext.removeAttribute(name);

        pageContext.setAttribute(var, valueObject);
        return EVAL_PAGE;
    }
}
