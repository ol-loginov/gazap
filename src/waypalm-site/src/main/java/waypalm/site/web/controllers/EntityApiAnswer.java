package waypalm.site.web.controllers;

import waypalm.site.web.model.ApiAnswer;

public class EntityApiAnswer<T> extends ApiAnswer {
    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
