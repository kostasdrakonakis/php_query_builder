package skaki;

import java.awt.Point;
import java.util.ArrayList;

public class Bishop extends Piece{

    public Bishop(String color, Point position) {
        super(color, "Bishop", position);
    }

    @Override
    public ArrayList<Point> getPossibleMovements(Board b) {
        ArrayList<Point> moves = new ArrayList<Point>();
        
        if(this.getColor().equals("Black")){
            for (int i = this.getPosition().x - 1, j = this.getPosition().y + 1;i>=0 && j<8;i--,j++) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x - 1, j = this.getPosition().y - 1;i >= 0 && j >= 0;i--,j--) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x + 1, j = this.getPosition().y - 1;i < 8 && j >= 0;i++,j--) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x + 1, j = this.getPosition().y + 1;i < 8 && j < 8;i++,j++) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
        }else{
            for (int i = this.getPosition().x - 1, j = this.getPosition().y + 1;i>=0 && j<8;i--,j++) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x - 1, j = this.getPosition().y - 1;i >= 0 && j >= 0;i--,j--) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x + 1, j = this.getPosition().y - 1;i < 8 && j >= 0;i++,j--) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
            
            for (int i = this.getPosition().x + 1, j = this.getPosition().y + 1;i < 8 && j < 8;i++,j++) {
                if(b.findPieceAtPosition(new Point(i,j)) == null){
                    moves.add(new Point(i,j));
                }else{
                    if(b.findPieceAtPosition(new Point(i,j)).getColor().equals(this.getColor()) == false){
                        moves.add(new Point(i,j));
                    }
                    break;
                }                
            }
        }
      
        return moves;
    }
    
}
