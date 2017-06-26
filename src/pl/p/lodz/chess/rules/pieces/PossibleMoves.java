package pl.p.lodz.chess.rules.pieces;

import pl.p.lodz.chess.rules.board.Position;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Taberu on 2017-05-01.
 */
public class PossibleMoves {
    private ArrayList<Position> Positions = new ArrayList<>();

    public PossibleMoves(Position... positions){
        this.Positions.addAll(Arrays.asList(positions));
    }
    public PossibleMoves(ArrayList<Position> positions){
        this.Positions = positions;
    }

    public ArrayList<Position> getPositions() {
        return Positions;
    }

    public void removeMove(Position position){
        Positions.remove(position);
    }
}
