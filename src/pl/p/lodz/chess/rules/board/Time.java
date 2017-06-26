package pl.p.lodz.chess.rules.board;

import javafx.application.Platform;
import pl.p.lodz.chess.GameState;

import java.util.Timer;
import java.util.TimerTask;

import static pl.p.lodz.engine.settings.ProfileSettings.getProfileSettings;

/**
 * Created by Taberu on 2017-05-30.
 */
public class Time extends TimerTask {
    private static final int TaskPeriodMiliseconds = 20;

    private long SecondsElapsed = 0;
    private float accumulatedMiliseconds = 0;
    private Timer timer = new Timer(true);
    private boolean bPaused = true;
    private boolean bCanceled = false;

    public Time(){
        start();
    }

    public long getSecondsElapsed() {
        return SecondsElapsed;
    }

    @Override
    public void run() {
        if(bPaused){
            return;
        }

        if((accumulatedMiliseconds += TaskPeriodMiliseconds) < 1000){
            return;
        }

        accumulatedMiliseconds -= 1000;

        if(++SecondsElapsed >= getProfileSettings().getRules().getTimerLimit() * 60 ){
            Platform.runLater(
                    () -> {
                        stop();
                        GameState.getInstance().endSession(
                                GameState.getInstance().getCurrentGame().getOpponent()
                        );
                    }
            );
        }

        Platform.runLater(
                () -> GameState.getInstance().getCurrentGame().getCurrentPlayer().UpdateGameTimer(this)
        );
    }

    public long getSeconds(){
        return SecondsElapsed%60;
    }

    public long getMinutes(){
        return SecondsElapsed/60;
    }

    @Override
    public String toString() {
        int gameTimeLimit = getProfileSettings().getRules().getTimerLimit() * 60;
        if (gameTimeLimit == 0) {
            return SecondsDisplayFormat(SecondsElapsed);
        }
        long timeLeftSeconds = gameTimeLimit - SecondsElapsed;
        return SecondsDisplayFormat(timeLeftSeconds);
    }

    private String SecondsDisplayFormat(long time){
        long minutes = time / 60;
        long seconds = time % 60;
        char zero = '\0';
        if(seconds < 10){
            zero = '0';
        }
        return Long.toString(minutes) + ":" + zero + Long.toString(seconds);
    }

    public void pause(){
        bPaused = true;
    }

    public void unpause(){
        bPaused = false;
    }

    public void stop(){
        if(!bCanceled){
            bCanceled = true;
            timer.cancel();
        }
    }

    private void start(){
        timer.scheduleAtFixedRate(this, 0, TaskPeriodMiliseconds);
    }

}
