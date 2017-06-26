package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.Position;

import java.util.ArrayList;

/**
 * Created by Taberu on 2017-05-01.
 */
public class Knight extends Piece {
    public Knight(Board parentBoard){
        super(parentBoard, PieceInfo.PieceType.knight);
    }

    public Knight(Board parentBoard, Position position, boolean isUpper){
        this(parentBoard);
        setCurrentPosition(position);
        setUpperSet(isUpper);
    }

    @Override
    public String toString(){
        return "N";
    }

    public ArrayList<Position> getAllPossibleMoves(){
        ArrayList<Position> positions = new ArrayList<>();
        getKnightPossibleMove(positions, Position.Directions.up, Position.Directions.left);
        getKnightPossibleMove(positions, Position.Directions.up, Position.Directions.right);
        getKnightPossibleMove(positions, Position.Directions.left, Position.Directions.up);
        getKnightPossibleMove(positions, Position.Directions.left, Position.Directions.down);
        getKnightPossibleMove(positions, Position.Directions.down, Position.Directions.left);
        getKnightPossibleMove(positions, Position.Directions.down, Position.Directions.right);
        getKnightPossibleMove(positions, Position.Directions.right, Position.Directions.up);
        getKnightPossibleMove(positions, Position.Directions.right, Position.Directions.down);
        return positions;
    }

    private void getKnightPossibleMove(ArrayList<Position> arrayToSaveInto, Position.Directions firstMove, Position.Directions secondMove){
        try{
            Position pos = new Position(getCurrentPosition(), firstMove, 2);
            pos.GO(secondMove, 1);
            arrayToSaveInto.add(pos);
        }catch(OutsideTheBoard e){}
    }

}
