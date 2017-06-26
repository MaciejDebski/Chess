package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.player.Player;

/**
 * Created by Taberu on 2017-06-01.
 */
public class PlayerInfo {
    private Player.Color player;

    public PlayerInfo(Player player){
        this.player = player.getPlayerColor();
    }

    public PlayerInfo(Player.Color player){
        this.player = player;
    }

    public Player.Color getPlayer() {
        return player;
    }
}
