package skaki;

import java.awt.Point;
import java.util.ArrayList;

public class King extends Piece{

    public King(String color, Point position) {
        super(color, "King", position);
    }

    @Override
    public ArrayList<Point> getPossibleMovements(Board b) {
        
        ArrayList<Point> moves = new ArrayList<Point>();
        if(this.getColor().equals("Black")){
            if(this.getPosition().x - 1 >= 0 && this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().x + 1 < 8 && this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().x + 1 < 8 && this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y - 1));
                }
            }
            
            if(this.getPosition().x - 1 >= 0 && this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y -+ 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y - 1));
                }
            }
            
            if(this.getPosition().x - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y));
                }
            }
            
            if(this.getPosition().x + 1 < 8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y));
                }
            }
            
            if(this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y - 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y - 1));
                }
            }
        }else{
            if(this.getPosition().x - 1 >= 0 && this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().x + 1 < 8 && this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().x + 1 < 8 && this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y - 1));
                }
            }
            
            if(this.getPosition().x - 1 >= 0 && this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y -+ 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y - 1));
                }
            }
            
            if(this.getPosition().x - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y)) == null){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y));
                }
            }
            
            if(this.getPosition().x + 1 < 8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y)) == null){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y));
                }
            }
            
            if(this.getPosition().y + 1 <8){
                if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y + 1)) == null){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y + 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y + 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y + 1));
                }
            }
            
            if(this.getPosition().y - 1 >= 0){
                if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y - 1)) == null){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y - 1));
                }
                else if(b.findPieceAtPosition(new Point(this.getPosition().x, this.getPosition().y - 1)).getColor().equals(this.getColor()) == false){
                    moves.add(new Point(this.getPosition().x, this.getPosition().y - 1));
                }
            }
        }
        return moves;
    }
    
}
