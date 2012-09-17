package waypalm.domain.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GeometryPlain")
@DynamicUpdate
@DiscriminatorValue(Geometry.Plain.CLASS)
public class GeometryPlain extends Geometry {
    @Column
    private int westMax;
    @Column
    private int northMax;
    @Column
    private int eastMax;
    @Column
    private int southMax;
    @Column
    private int scaleMin;
    @Column
    private int scaleMax;

    public GeometryPlain() {
        super(Plain.CLASS);
    }

    public int getWestMax() {
        return westMax;
    }

    public void setWestMax(int westMax) {
        this.westMax = westMax;
    }

    public int getNorthMax() {
        return northMax;
    }

    public void setNorthMax(int northMax) {
        this.northMax = northMax;
    }

    public int getEastMax() {
        return eastMax;
    }

    public void setEastMax(int eastMax) {
        this.eastMax = eastMax;
    }

    public int getSouthMax() {
        return southMax;
    }

    public void setSouthMax(int southMax) {
        this.southMax = southMax;
    }

    public int getScaleMin() {
        return scaleMin;
    }

    public void setScaleMin(int scaleMin) {
        this.scaleMin = scaleMin;
    }

    public int getScaleMax() {
        return scaleMax;
    }

    public void setScaleMax(int scaleMax) {
        this.scaleMax = scaleMax;
    }
}
