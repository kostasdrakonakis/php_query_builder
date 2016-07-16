package model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement(name="shape")
@XmlType(propOrder={"shapeName", "pointX", "pointY", "pointZ", "pointW"})
public class Shape {
    
    private String shapeName;
    private int pointX, pointY, pointZ, pointW;

    public Shape() {
    }

    public Shape(String shapeName, int pointX, int pointY) {
        this.shapeName = shapeName;
        this.pointX = pointX;
        this.pointY = pointY;
    }

    public Shape(String shapeName, int pointX, int pointY, int pointZ, int pointW) {
        this.shapeName = shapeName;
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
        this.pointW = pointW;
    }
    
    public Shape(String shapeName, int pointX, int pointY, int pointZ) {
        this.shapeName = shapeName;
        this.pointX = pointX;
        this.pointY = pointY;
        this.pointZ = pointZ;
    }
    
    @Override
    public String toString() {
        return "The Shape is " +  this.shapeName + 
                " and the coordinates are("
                + Integer.toString(this.pointX) + ", " 
                + Integer.toString(this.pointY) + ", " 
                + Integer.toString(this.pointZ) + ", " 
                + Integer.toString(this.pointW) +  ")";
    }
    

    @XmlElement(required = true)
    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }

    @XmlElement(name="pointX", required = true)
    public int getPointX() {
        return pointX;
    }

    public void setPointX(int pointX) {
        this.pointX = pointX;
    }

    @XmlElement
    public int getPointY() {
        return pointY;
    }

    public void setPointY(int pointY) {
        this.pointY = pointY;
    }

    @XmlElement
    public int getPointZ() {
        return pointZ;
    }

    public void setPointZ(int pointZ) {
        this.pointZ = pointZ;
    }

    @XmlElement
    public int getPointW() {
        return pointW;
    }

    public void setPointW(int pointW) {
        this.pointW = pointW;
    }

    
    
    
    
    
}
