package gazap.site.web.mvc.wrime.tags;

public class LoopFactory implements TagFactory {
    @Override
    public boolean supports(String name) {
        return "loop".equals(name);
    }

    @Override
    public LoopReceiver createReceiver(String name) {
        return new LoopReceiver();
    }
}
