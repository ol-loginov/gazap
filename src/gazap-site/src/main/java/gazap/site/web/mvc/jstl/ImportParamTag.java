package gazap.site.web.mvc.jstl;

import org.apache.taglibs.standard.tag.common.core.ParamParent;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;
import java.util.UUID;

public class ImportParamTag extends org.apache.taglibs.standard.tag.rt.core.ParamTag {
    public static final String PARAM_PREFIX = "gazap.site.web.mvc.jstl.ImportParamTag.";

    private Object value;
    private String var;

    @Override
    public void release() {
        super.release();
        var = null;
        value = null;
    }

    public void setValue(Object value) throws JspTagException {
        this.value = value;
        super.setValue(PARAM_PREFIX + UUID.randomUUID().toString());
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public int doEndTag() throws JspException {
        Tag t = findAncestorWithClass(this, ParamParent.class);
        return t != null
                ? super.doEndTag()
                : restoreVar();
    }

    private int restoreVar() {
        return EVAL_PAGE;
    }
}
