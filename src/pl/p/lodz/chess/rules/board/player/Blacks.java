package pl.p.lodz.chess.rules.board.player;

import pl.p.lodz.chess.GameState;
import pl.p.lodz.chess.gui.ANInfo;
import pl.p.lodz.chess.gui.Display;
import pl.p.lodz.chess.gui.PlayerInfo;
import pl.p.lodz.chess.gui.TimeInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.Time;
import pl.p.lodz.chess.rules.board.algebraicnotation.AN;
import pl.p.lodz.chess.rules.pieces.PieceSet;

import static pl.p.lodz.engine.settings.ProfileSettings.getProfileSettings;

/**
 * Created by Taberu on 2017-05-30.
 */
public class Blacks extends Player {
    public Blacks(){
        super(Color.black);
    }

    public void generateStartingSet(Board parentBoard){
        boolean isUpper = !getProfileSettings().getRules().isInvertTheBoard();
        getSet().setActivePieces(PieceSet.getStartingSet(parentBoard, isUpper));
        getSet().ColorSet(Color.black);
    }

    public void UpdateGameTimer(Time time) {
        Display.fireTimeChanged(new TimeInfo(Color.black, time) );
    }
    public Player getOpponent(){
        return GameState.getInstance().getCurrentGame().getWhites();
    }
}
