package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.tags.*;

import java.util.ArrayList;
import java.util.List;

public class RootReceiver extends PathReceiver {
    private List<TagFactory> tagFactories;

    public RootReceiver() {
        tagFactories = new ArrayList<TagFactory>() {{
            add(new ForFactory());
            add(new ForBreakFactory());
            add(new ForContinueFactory());
            add(new ParamFactory());
            add(new ImportFactory());
        }};
    }

    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
        for (TagFactory factory : tagFactories) {
            if (factory.supports(name)) {
                PathReceiver receiver = factory.createReceiver(name);
                path.push(receiver);
                return;
            }
        }

        CallReceiver receiver = new CallReceiver();
        path.push(receiver);
        receiver.pushToken(scope, name);
    }

    @Override
    public void complete(ExpressionContextKeeper scope) throws WrimeException {
        //super-puper
    }
}
