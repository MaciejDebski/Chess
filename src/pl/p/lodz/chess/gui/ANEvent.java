package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.board.algebraicnotation.AN;

/**
 * Created by Taberu on 2017-06-01.
 */
public interface ANEvent {
    void fireEvent(ANInfo algebraicNotation);
}
