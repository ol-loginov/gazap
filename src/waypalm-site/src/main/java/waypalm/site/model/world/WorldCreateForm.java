package waypalm.site.model.world;

import org.hibernate.validator.constraints.URL;
import waypalm.domain.entity.World;
import waypalm.site.validation.WorldTitle;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@WorldTitle
public class WorldCreateForm {
    @NotNull
    @Size(min = 1, max = World.TITLE_LENGTH)
    private String title;
    @Size(max = World.MEMO_LENGTH)
    private String memo;
    @Size(max = World.PUBLISHER_TITLE_LENGTH)
    private String publisherTitle;
    @Size(max = World.PUBLISHER_URL_LENGTH)
    @URL(regexp = "^http(s)?://.*", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String publisherUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPublisherTitle() {
        return publisherTitle;
    }

    public void setPublisherTitle(String publisherTitle) {
        this.publisherTitle = publisherTitle;
    }

    public String getPublisherUrl() {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl) {
        this.publisherUrl = publisherUrl;
    }
}
