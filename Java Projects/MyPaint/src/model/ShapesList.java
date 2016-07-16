package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ShapesList {
    @XmlElement(name="shapeItem")
    protected List<Shape> pList;
    @XmlElement(name="nameOfList")
    private String pName;

    public ShapesList() {
    }
    
    
    
    public ShapesList(String name) {
        this.pList = new ArrayList();
        this.pName = name;
    }
    
    public boolean addShape(Shape p) {
        return pList.add(p);
    }
    
    public void setShapeList(List<Shape> pList) {
        this.pList = pList;
    }
    
    public List<Shape> getShapeList() {
        return this.pList;
    }
    
    public String getName() {
        return this.pName;
    }
    
    public void setName(String pName) {
        this.pName = pName;
    }
    
}
