package gazap.domain.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GeometryPlain")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@DiscriminatorValue(Geometry.Plain.CLASS)
public class GeometryPlain extends Geometry {
    @Column(name = "west")
    private int west;
    @Column(name = "north")
    private int north;
    @Column(name = "east")
    private int east;
    @Column(name = "south")
    private int south;
    @Column(name = "tileSize")
    private int tileSize;
    @Column(name = "scaleMin")
    private int scaleMin;
    @Column(name = "scaleMax")
    private int scaleMax;

    public GeometryPlain() {
        super(Plain.CLASS);
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
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
