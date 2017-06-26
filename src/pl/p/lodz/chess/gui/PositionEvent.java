package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.Position;

/**
 * Created by Taberu on 2017-06-01.
 */
public interface PositionEvent {
    void fireEvent(PositionInfo position);
}
