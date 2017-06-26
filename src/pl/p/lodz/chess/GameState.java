package pl.p.lodz.chess;

import pl.p.lodz.chess.gui.Display;
import pl.p.lodz.chess.gui.PlayerInfo;
import pl.p.lodz.chess.rules.board.Board;
import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.engine.profiles.Profile;
import pl.p.lodz.engine.serialization.SerializationLoadError;
import pl.p.lodz.engine.ui.UIState;
import pl.p.lodz.view.chess.BoardController;

/**
 * Created by Taberu on 2017-03-19.
 */
public class GameState {
    private static GameState ourInstance = new GameState();
    public static GameState getInstance() {
        return ourInstance;
    }
    private Board CurrentGame;

    public void StartTheMainMenu() throws SerializationLoadError {
        if(Profile.GetCurrentProfile() == null){
            throw new SerializationLoadError("No profile is loaded to play!");
        }

        UIState.ChangeUI(UIState.UITypes.main_menu);
    }

    public void StartTheGame(){
        UIState.ChangeUI(UIState.UITypes.chess_board);
        try{
            CurrentGame = Profile.GetCurrentProfile().getCurrentGameProgerss();
        }
        catch(NoCurrentGameProgress e){
            CurrentGame = new Board();
            CurrentGame.StartNewGame();
        }
    }

    public void endSession(Player winner){
        CurrentGame.getWhites().getGameTimer().stop();
        CurrentGame.getBlacks().getGameTimer().stop();
        Display.fireMatchFinished(new PlayerInfo(winner));
    }
    
    public void rematch(){
    	Display.fireAllPossibleMovesHided();
    }

    public Board getCurrentGame() {
        return CurrentGame;
    }

    private GameState() {}
}
