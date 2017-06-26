package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.Position;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Taberu on 2017-05-01.
 */
public class Bishop extends Piece {
    public Bishop(Board parentBoard){
        super(parentBoard, PieceInfo.PieceType.bishop);
    }

    public Bishop(Board parentBoard, Position position, boolean isUpper){
        this(parentBoard);
        setCurrentPosition(position);
        setUpperSet(isUpper);
    }

    @Override
    public String toString(){
        return "B";
    }

    public ArrayList<Position> getAllPossibleMoves(){
        ArrayList<Position> positions = new ArrayList<>();
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up_left, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up_right, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down_left, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down_right, 8));
        return positions;
    }
}
