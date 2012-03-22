package gazap.site.web.mvc.wrime.model;

public class Bean2 {
    private int integer;
    private int string;

    public int getInteger() {
        return integer;
    }

    public int getString() {
        return string;
    }

    public void call(int value) {
    }

    public Bean2 call(String value) {
        return this;
    }
}
