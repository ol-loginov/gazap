package gazap.site.web.mvc.jstl;

import org.apache.taglibs.standard.tag.rt.fmt.MessageTag;

import javax.servlet.jsp.JspException;

public class FormatterTag extends MessageTag {
    protected Object arg1;
    protected boolean arg1Set;
    protected Object arg2;
    protected boolean arg2Set;
    protected Object arg3;
    protected boolean arg3Set;

    public void setArg1(Object arg1) {
        this.arg1 = arg1;
        this.arg1Set = true;
    }

    public void setArg2(Object arg2) {
        this.arg2 = arg2;
        this.arg2Set = true;
    }

    public void setArg3(Object arg3) {
        this.arg3 = arg3;
        this.arg3Set = true;
    }

    @Override
    public int doEndTag() throws JspException {
        addParam(arg1, arg1Set);
        addParam(arg2, arg2Set);
        addParam(arg3, arg3Set);
        return super.doEndTag();
    }

    private void addParam(Object arg, boolean condition) {
        if (condition) {
            addParam(arg);
        }
    }
}
