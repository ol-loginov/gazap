package waypalm.site.web.controllers;

import org.codehaus.jackson.annotate.JsonProperty;
import waypalm.site.web.model.ApiAnswer;

public class EntityApiAnswer<T> extends ApiAnswer {
    @JsonProperty
    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
