package gazap.site.web.controllers.map;

import gazap.site.model.viewer.ContributionV;
import gazap.site.web.model.ApiAnswer;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@JsonAutoDetect(JsonMethod.NONE)
public class MapGodControllerLocalChangesApiAnswer extends ApiAnswer {
    private List<ContributionV> list;

    @JsonProperty
    public List<ContributionV> getList() {
        return list;
    }

    public void setList(List<ContributionV> list) {
        this.list = list;
        setSuccess(list != null);
    }
}
