package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.board.Time;

/**
 * Created by Taberu on 2017-06-01.
 */
public class TimeInfo {
    private long seconds;
    private long minutes;
    private String time;

    private PlayerInfo playerInfo;

    public TimeInfo(Player.Color color, Time time){
        this.playerInfo = new PlayerInfo(color);
        this.seconds = time.getSecondsElapsed() % 60;
        this.minutes = time.getSecondsElapsed() / 60;
        this.time = time.toString();
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getMinutes() {
        return minutes;
    }

    public String getTime() {
        return time;
    }
}
