package gazap.domain.entity;

import gazap.domain.entity.base.IntegerIdentityC;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "Contribution")
@DynamicUpdate
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.STRING)
public abstract class Contribution extends IntegerIdentityC {
    @Version
    @Column(name = "version")
    private int version;
    @Column(name = "class", updatable = false)
    private String contributionClass = null;
    @ManyToOne
    @JoinColumn(name = "map", nullable = true, updatable = false)
    private Surface surface;
    @ManyToOne
    @JoinColumn(name = "author", nullable = false, updatable = false)
    private UserProfile author;
    @Column(name = "decision", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContributionDecision decision = ContributionDecision.NONE;
    @Column(name = "approveLevel")
    private int approveLevel = 0;

    protected Contribution() {
    }

    protected Contribution(String contributionClass) {
        this.contributionClass = contributionClass;
    }

    public int getVersion() {
        return version;
    }

    protected void setVersion(int version) {
        this.version = version;
    }

    public String getContributionClass() {
        return contributionClass;
    }

    protected void setContributionClass(String contributionClass) {
        this.contributionClass = contributionClass;
    }

    public Surface getSurface() {
        return surface;
    }

    public void setSurface(Surface surface) {
        this.surface = surface;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public ContributionDecision getDecision() {
        return decision;
    }

    public void setDecision(ContributionDecision decision) {
        this.decision = decision;
    }

    public int getApproveLevel() {
        return approveLevel;
    }

    public void setApproveLevel(int approveLevel) {
        this.approveLevel = approveLevel;
    }
}
