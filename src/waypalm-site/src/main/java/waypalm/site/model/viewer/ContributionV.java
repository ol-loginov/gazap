package waypalm.site.model.viewer;

import waypalm.domain.entity.ContributionDecision;
import waypalm.domain.entity.ContributionTileAction;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

@JsonAutoDetect(JsonMethod.NONE)
public class ContributionV {
    private int id;
    private Date createdAt;
    private String type;
    private ContributionDecision decision;

    private int x;
    private int y;
    private int scale;
    private int size;
    private ContributionTileAction action;

    private String file;

    private int author;
    private String authorName;
    private String authorGravatar;

    @JsonProperty
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

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

    @JsonProperty
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @JsonProperty
    public String getAuthorGravatar() {
        return authorGravatar;
    }

    public void setAuthorGravatar(String authorGravatar) {
        this.authorGravatar = authorGravatar;
    }
}
