package skaki;

import java.awt.Point;
import java.util.ArrayList;

public class Pawn extends Piece {

    public Pawn(String color, Point position) {
        super(color, "Pawn", position);
    }

    @Override
    public ArrayList<Point> getPossibleMovements(Board b) {
        ArrayList<Point> moves = new ArrayList<Point>();
        
        if (this.getColor().equals("Black")) {
            if (this.getPosition().x + 1 < 8) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y)) == null) {
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y));

                    if (this.getPosition().x == 1 && b.findPieceAtPosition(new Point(this.getPosition().x + 2, this.getPosition().y)) == null) {
                        moves.add(new Point(this.getPosition().x + 2, this.getPosition().y));
                    }
                }

            }

            if (this.getPosition().x + 1 < 8 && this.getPosition().y + 1 < 8) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)) != null
                        && b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y + 1)).getColor().equals("White")) {
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y + 1));
                }
            }

            if (this.getPosition().x + 1 < 8 && this.getPosition().y - 1 >= 0) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)) != null
                        && b.findPieceAtPosition(new Point(this.getPosition().x + 1, this.getPosition().y - 1)).getColor().equals("White")) {
                    moves.add(new Point(this.getPosition().x + 1, this.getPosition().y - 1));
                }
            }
        } else {
            if (this.getPosition().x - 1 >= 0) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y)) == null) {
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y));
                    if (this.getPosition().x == 6 && b.findPieceAtPosition(new Point(this.getPosition().x - 2, this.getPosition().y)) == null) {
                        moves.add(new Point(this.getPosition().x - 2, this.getPosition().y));
                    }
                }

            }

            if (this.getPosition().x - 1 >= 0 && this.getPosition().y + 1 < 8) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)) != null
                        && b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y + 1)).getColor().equals("Black")) {
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y + 1));
                }
            }

            if (this.getPosition().x - 1 >= 0 && this.getPosition().y - 1 >= 0) {
                if (b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y - 1)) != null
                        && b.findPieceAtPosition(new Point(this.getPosition().x - 1, this.getPosition().y - 1)).getColor().equals("Black")) {
                    moves.add(new Point(this.getPosition().x - 1, this.getPosition().y - 1));
                }
            }
        }
        return moves;
    }

}
