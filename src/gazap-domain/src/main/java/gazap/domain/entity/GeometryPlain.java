package gazap.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GeometryPlain")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class GeometryPlain extends Geometry {
    @Column(name = "west")
    private float west;
    @Column(name = "north")
    private float north;
    @Column(name = "east")
    private float east;
    @Column(name = "south")
    private float south;

    public float getWest() {
        return west;
    }

    public void setWest(float west) {
        this.west = west;
    }

    public float getNorth() {
        return north;
    }

    public void setNorth(float north) {
        this.north = north;
    }

    public float getEast() {
        return east;
    }

    public void setEast(float east) {
        this.east = east;
    }

    public float getSouth() {
        return south;
    }

    public void setSouth(float south) {
        this.south = south;
    }
}