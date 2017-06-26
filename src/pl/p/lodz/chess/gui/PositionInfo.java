package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.Position;

/**
 * Created by Taberu on 2017-06-01.
 */
public class PositionInfo {
    private int X, Y;

    public PositionInfo(Position position){
        this(position.getX(), position.getY());
    }

    public PositionInfo(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
