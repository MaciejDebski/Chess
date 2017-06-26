package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.player.Player;
import pl.p.lodz.chess.rules.board.algebraicnotation.AN;

/**
 * Created by Taberu on 2017-06-01.
 */
public class ANInfo {
    private String an;
    private PlayerInfo playerInfo;

    public String getAN() {
        return an;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public ANInfo(AN an, Player.Color player){
        this.an = an.toString();
        this.playerInfo = new PlayerInfo(player);
    }

    @Override
    public String toString(){
        return an;
    }
}
