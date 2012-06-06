package gazap.site.model.viewer;

import gazap.domain.entity.ContributionDecision;
import gazap.domain.entity.ContributionTileAction;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonAutoDetect(JsonMethod.NONE)
public class ContributionV {
    private int id;
    private String type;
    private int author;
    private String authorName;
    private ContributionDecision decision;

    private int x;
    private int y;
    private int scale;
    private int size;
    private ContributionTileAction action;

    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty
    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    @JsonProperty
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @JsonProperty
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty
    public ContributionDecision getDecision() {
        return decision;
    }

    public void setDecision(ContributionDecision decision) {
        this.decision = decision;
    }

    @JsonProperty
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @JsonProperty
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @JsonProperty
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @JsonProperty
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @JsonProperty
    public ContributionTileAction getAction() {
        return action;
    }

    public void setAction(ContributionTileAction action) {
        this.action = action;
    }
}
