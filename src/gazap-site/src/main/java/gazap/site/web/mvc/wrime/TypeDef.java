package gazap.site.web.mvc.wrime;

public class TypeDef {
    private Class clazz;
    private String alias;

    public TypeDef() {
    }

    public TypeDef(Class clazz) {
        this.clazz = clazz;
    }

    public boolean isVoid() {
        return Void.TYPE.equals(clazz) || void.class.equals(clazz);
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
