package pl.p.lodz.chess.gui;

/**
 * Created by Taberu on 2017-06-01.
 */
public interface MoveEvent {
    void fireEvent(MoveInfo moveInfo);
}
