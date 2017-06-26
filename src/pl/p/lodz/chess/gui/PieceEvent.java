package pl.p.lodz.chess.gui;

import pl.p.lodz.chess.rules.pieces.Piece;

/**
 * Created by Taberu on 2017-06-01.
 */
public interface PieceEvent {
    void fireEvent(PieceInfo piece);
}
