package waypalm.site.model;

import waypalm.common.web.model.ObjectErrors;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class AnswerStatus {
    @XmlElement
    private boolean success = false;
    @XmlElement
    private String message;
    @XmlElement
    private ObjectErrors errors;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ObjectErrors getErrors() {
        return errors;
    }

    public void setErrors(ObjectErrors errors) {
        this.errors = errors;
    }
}
