package gazap.site.web.mvc.wrime.tags;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;

public interface CompleteCallback {
    void complete(PathReceiver child, ExpressionContextKeeper scope, boolean last) throws WrimeException;
}
