package waypalm.site.web.views.errors;

import java.util.ArrayList;
import java.util.List;

public class HttpError {
    private String bugEmail;
    private String bugSubject;
    private String bugBody;
    private String http;
    private List<String> suggestions = new ArrayList<String>();

    public String getBugEmail() {
        return bugEmail;
    }

    public void setBugEmail(String bugEmail) {
        this.bugEmail = bugEmail;
    }

    public String getBugSubject() {
        return bugSubject;
    }

    public void setBugSubject(String bugSubject) {
        this.bugSubject = bugSubject;
    }

    public String getBugBody() {
        return bugBody;
    }

    public void setBugBody(String bugBody) {
        this.bugBody = bugBody;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }
}
