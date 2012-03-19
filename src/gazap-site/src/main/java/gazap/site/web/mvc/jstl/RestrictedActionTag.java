package gazap.site.web.mvc.jstl;

import gazap.domain.entity.UserProfile;
import gazap.site.services.UserAccess;
import gazap.site.services.UserActionGuard;
import org.springframework.util.MethodInvoker;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;
import java.util.HashMap;
import java.util.Map;

public class RestrictedActionTag extends ConditionalTagSupport {
    private Integer visitorNeeded;
    private String action;
    private Object against;
    private UserAccess accessService;

    static final Map<String, ConditionMatcher> actionMatchers;

    static {
        actionMatchers = new HashMap<String, ConditionMatcher>() {{
            put("map.create", new GuardInvoker("createMap"));
            put("map.edit", new GuardInvoker("editMap"));
        }};
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAgainst(Object against) {
        this.against = against;
    }

    public void setVisitorNeeded(Integer visitorNeeded) {
        this.visitorNeeded = visitorNeeded;
    }

    private UserAccess getAccessService() {
        if (accessService == null) {
            accessService = WebApplicationContextUtils.getWebApplicationContext(pageContext.getServletContext()).getBean(UserAccess.class);
        }
        return accessService;
    }

    @Override
    public void release() {
        super.release();
        action = null;
        against = null;
        accessService = null;
        visitorNeeded = null;
    }

    @Override
    protected boolean condition() throws JspTagException {
        return checkVisitor() && getActionMatcher(action).invoke(getAccessService(), against);
    }

    private boolean checkVisitor() {
        if (visitorNeeded == null) {
            return true;
        }
        UserProfile visitor = getAccessService().getCurrentProfile();
        return visitor != null && visitorNeeded.equals(visitor.getId());
    }

    private ConditionMatcher getActionMatcher(String action) {
        ConditionMatcher matcher = actionMatchers.get(action);
        if (matcher == null) {
            throw new IllegalStateException("no matcher for action '" + action + "'");
        }
        return matcher;
    }

    interface ConditionMatcher {
        boolean invoke(UserAccess userAccess, Object target);
    }

    static class GuardInvoker implements ConditionMatcher {
        private final String method;

        public GuardInvoker(String method) {
            this.method = method;
        }

        @Override
        public boolean invoke(UserAccess userAccess, Object target) {
            MethodInvoker invoker = new MethodInvoker();
            invoker.setTargetClass(UserActionGuard.class);
            invoker.setTargetMethod(method);
            invoker.setTargetObject(userAccess.can());
            if (target != null) {
                invoker.setArguments(new Object[]{target});
            }
            try {
                invoker.prepare();
                return (Boolean) invoker.invoke();
            } catch (Exception e) {
                throw new IllegalStateException("cannot call method " + invoker, e);
            }
        }
    }
}
