package pl.p.lodz.chess.rules.board;

import java.io.Serializable;

/**
 * Created by Taberu on 2017-05-30.
 */
public class Rules implements Serializable {
    private boolean bInvertTheBoard = false;
    private boolean bShowPossibleMoveShades = true;
    private int TimerLimit = 10;

    public boolean isInvertTheBoard() {
        return bInvertTheBoard;
    }

    public void setInvertTheBoard(boolean bInvertTheBoard) {
        this.bInvertTheBoard = bInvertTheBoard;
    }

    public void setbPlayAsBlack(boolean bInvertTheBoard) {
        this.bInvertTheBoard = bInvertTheBoard;
    }

    public boolean isShowPossibleMoveShades() {
        return bShowPossibleMoveShades;
    }

    public void setShowPossibleMoveShades(boolean bShowPossibleMoveShades) {
        this.bShowPossibleMoveShades = bShowPossibleMoveShades;
    }

    public int getTimerLimit() {
        return TimerLimit;
    }

    public void setTimerLimit(int timerLimit) {
        TimerLimit = timerLimit;
    }

}
