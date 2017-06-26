package pl.p.lodz.chess.rules.board;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.algebraicnotation.AN;
import pl.p.lodz.chess.rules.pieces.Piece;
import pl.p.lodz.chess.rules.pieces.PossibleMoves;

/**
 * Created by Taberu on 2017-04-30.
 */
public class Position {
    private int X;
    private int Y;

    public enum Directions{
        up,
        left,
        down,
        right,
        up_left,
        up_right,
        down_left,
        down_right
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public Position(int X, int Y) throws OutsideTheBoard {
        if(!Board.IsBoardIndex(X, Y) ) {
            throw new OutsideTheBoard(X, Y);
        }
        this.X = X;
        this.Y = Y;
    }

    public Position(int X, int Y, Directions direction, int distance) throws OutsideTheBoard {
        try{
            setRelativePosition(X, Y, direction, distance);
        }
        catch(OutsideTheBoard e){
            throw e;
        }
    }

    public Position(Position position, Directions direction, int distance) throws OutsideTheBoard {
        setRelativePosition(position.X, position.Y, direction, distance);
        if(!Board.IsBoardIndex(X, Y) ) {
            throw new OutsideTheBoard(X, Y);
        }
    }

    public void SetPositionX(int X) throws OutsideTheBoard {
        try{
            SetPosition(X, this.Y);
        }
        catch(OutsideTheBoard e){
            throw e;
        }
    }

    public void SetPositionY(int Y) throws OutsideTheBoard {
        try{
            SetPosition(this.X, Y);
        }
        catch(OutsideTheBoard e){
            throw e;
        }
    }

    public void SetPosition(int X, int Y) throws OutsideTheBoard {
        if(!Board.IsBoardIndex(X, Y) ) {
            throw new OutsideTheBoard(X, Y);
        }
        this.X = X;
        this.Y = Y;
    }

    public void SetPosition(Position position){
        this.X = position.X;
        this.Y = position.Y;
    }

    public void GO(Directions dir, int distance) throws OutsideTheBoard {
        try{
            setRelativePosition(X, Y, dir, distance);
        }
        catch(OutsideTheBoard e){
            throw e;
        }
    }

    public void setRelativePosition(Position position, Directions direction, int distance) throws OutsideTheBoard {
        try{
            setRelativePosition(position.X, position.Y, direction, distance);
        }
        catch(OutsideTheBoard e){
            throw e;
        }
    }

    public void setRelativePosition(int X, int Y, Directions direction, int distance) throws OutsideTheBoard {
        int x = -15, y = -15;
        switch(direction){
            case up:{
                x = X;
                y = Y - distance;
                break;
            }
            case left:{
                x = X - distance;
                y = Y;
                break;
            }
            case down: {
                x = X;
                y = Y + distance;
                break;
            }
            case right: {
                x = X + distance;
                y = Y;
                break;
            }
            case up_left: {
                x = X - distance;
                y = Y - distance;
                break;
            }
            case up_right: {
                x = X + distance;
                y = Y - distance;
                break;
            }
            case down_left: {
                x = X - distance;
                y = Y + distance;
                break;
            }
            case down_right: {
                x = X + distance;
                y = Y + distance;
                break;
            }
        }

        if(!Board.IsBoardIndex(x, y) ) {
            throw new OutsideTheBoard(x, y);
        }

        this.X = x;
        this.Y = y;
    }

    public AN toAN(){
        return new AN( (char)( X + 'a'), 8 - Y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position otherPos = (Position) obj;

        return X == otherPos.X && Y == otherPos.Y;
    }

    public static PossibleMoves possibleMovesAtPosition(Position pos, PieceInfo.PieceType pieceType, boolean isUpper) {
        Piece piece = Piece.newPieceUsingPieceType(pieceType, GameState.getInstance().getCurrentGame(), pos, isUpper);
        if(piece == null){
            return new PossibleMoves();
        }
        return piece.getPossibleMoves();
    }

}
