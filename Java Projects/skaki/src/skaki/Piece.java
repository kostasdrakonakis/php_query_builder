package skaki;

import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Piece extends JLabel{
    private String color;
    private String pieceName;
    private Point position;
    private String iconImage;

    public Piece(String color, String pieceName, Point position) {
        this.color = color;
        this.pieceName = pieceName;
        this.iconImage = "Normal";
        this.position = position;
        
        
        String file = "images/" + color + "_" + pieceName + "_" + this.iconImage + ".png";
        ImageIcon image = new ImageIcon(file);
        this.setIcon(image);
        
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
        
        String file = "images/" + color + "_" + pieceName + "_" + this.iconImage + ".png";
        ImageIcon image = new ImageIcon(file);
        this.setIcon(image);
    }
    
    public String getPieceName(){
        return pieceName;
    }
    
    public void setPieceName(String pieceName){
        this.pieceName = pieceName;
    }
    
    public abstract ArrayList<Point> getPossibleMovements(Board b);
}
