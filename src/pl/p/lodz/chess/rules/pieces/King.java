package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.gui.PieceInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.OutsideTheBoard;
import pl.p.lodz.chess.rules.board.Position;
import pl.p.lodz.chess.rules.board.algorithms.CheckMate;
import pl.p.lodz.chess.rules.board.player.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Taberu on 2017-05-01.
 */
public class King extends Piece {
    public King(Board parentBoard){
        super(parentBoard, PieceInfo.PieceType.king);
    }

    public King(Board parentBoard, Position position, boolean isUpper){
        this(parentBoard);
        setCurrentPosition(position);
        setUpperSet(isUpper);
    }

    @Override
    public String toString(){
        return "K";
    }

    public ArrayList<Position> getAllPossibleMoves(){
        ArrayList<Position> positions = new ArrayList<>();
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.left, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.right, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up_left, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.up_right, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down_left, 1));
        positions.addAll(getPossibleMovesInDirection(Position.Directions.down_right, 1));
        return positions;
    }

    private void removeKingsThreatenMoves(PossibleMoves pm){
        for (Iterator<Position> itr = pm.getPositions().iterator(); itr.hasNext(); ) {
            if (CheckMate.isPositionThreaten(getParentPlayer(), itr.next())) {
                itr.remove();
            }
        }
    }

    @Override
    public void removeIllegalMoves(PossibleMoves allPossibleMoves){
        super.removeIllegalMoves(allPossibleMoves);
        removeKingsThreatenMoves(allPossibleMoves);
    }
}
