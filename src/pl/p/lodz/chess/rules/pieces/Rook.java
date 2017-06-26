package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.Position;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Taberu on 2017-05-01.
 */
public class Rook extends Piece {
    public Rook(Board parentBoard){
        super(parentBoard, PieceInfo.PieceType.rook);
    }

    public Rook(Board parentBoard, Position position, boolean isUpper){
        this(parentBoard);
        setCurrentPosition(position);
        setUpperSet(isUpper);
    }

    @Override
    public String toString(){
        return "R";
    }

    public ArrayList<Position> getAllPossibleMoves(){
        ArrayList<Position> positions = new ArrayList<>();
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.left, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down, 8));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.right, 8));
        return positions;
    }
}
