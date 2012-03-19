package gazap.site.web.mvc.jstl;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;

public class RestrictedActionTei extends TagExtraInfo {
    @Override
    public boolean isValid(TagData data) {
        String action = (String) data.getAttribute("action");
        if (null == action) {
            return false;
        } else if (!RestrictedActionTag.actionMatchers.containsKey(action)) {
            return false;
        }
        return true;
    }
}
