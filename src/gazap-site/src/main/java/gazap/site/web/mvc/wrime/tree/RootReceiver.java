package gazap.site.web.mvc.wrime.tree;

import gazap.site.web.mvc.wrime.ExpressionContextKeeper;
import gazap.site.web.mvc.wrime.WrimeException;
import gazap.site.web.mvc.wrime.tags.ImportFactory;
import gazap.site.web.mvc.wrime.tags.LoopFactory;
import gazap.site.web.mvc.wrime.tags.ParamFactory;
import gazap.site.web.mvc.wrime.tags.TagFactory;

import java.util.ArrayList;
import java.util.List;

public class RootReceiver extends PathReceiver {
    private List<TagFactory> tagFactories;

    public RootReceiver() {
        tagFactories = new ArrayList<TagFactory>() {{
            add(new LoopFactory());
            add(new ParamFactory());
            add(new ImportFactory());
        }};
    }

    @Override
    public void pushToken(ExpressionContextKeeper scope, String name) throws WrimeException {
        PathReceiver receiver = null;
        for (TagFactory factory : tagFactories) {
            if (factory.supports(name)) {
                receiver = factory.createReceiver(name);
                break;
            }
        }
        if (receiver == null) {
            receiver = new CallReceiver();
            receiver.pushToken(scope, name);
        }
        path.push(receiver);
    }

    @Override
    public void complete(ExpressionContextKeeper scope) throws WrimeException {
        //super-puper
    }
}
