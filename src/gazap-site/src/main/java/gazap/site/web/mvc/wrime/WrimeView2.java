package gazap.site.web.mvc.wrime;

import gazap.site.services.UserAccess;
import gazap.site.services.UserActionGuard;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;
import wrime.spring.webmvc.WrimeView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TreeMap;

public class WrimeView2 extends WrimeView {
    private final static String ACCESS_FUNCTOR = "can";
    private UserAccess userAccess;

    @Override
    protected void initApplicationContext(ApplicationContext context) {
        super.initApplicationContext(context);
        userAccess = getApplicationContext().getBean(UserAccess.class);
        if (userAccess == null) {
            throw new BeanInitializationException("Bean of type " + UserAccess.class + " is not found");
        }
    }

    @Override
    protected void registerWebRequestFunctors() {
        super.registerWebRequestFunctors();
        getWrimeEngine().registerFunctor(ACCESS_FUNCTOR, UserActionGuard.class, null);
    }

    @Override
    protected void setupWebRequestFunctors(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        super.setupWebRequestFunctors(map, request, response);
        getWrimeEngine().addFunctorToModel(map, ACCESS_FUNCTOR, userAccess.can());
    }
}
